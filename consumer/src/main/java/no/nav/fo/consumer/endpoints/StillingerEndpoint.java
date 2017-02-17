package no.nav.fo.consumer.endpoints;

import io.ino.solrs.JavaAsyncSolrClient;
import no.nav.fo.consumer.extractor.AntallStillingerExtractor;
import no.nav.fo.consumer.transformers.BransjeForFylkeTransformer;
import no.nav.fo.consumer.transformers.StillingTransformer;
import no.nav.fo.consumer.transformers.StillingerForOmradeTransformer;
import no.nav.fo.consumer.transformers.StillingstypeForYrkesomradeTransformer;
import no.nav.fo.mia.domain.Filtervalg;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import no.nav.fo.mia.domain.stillinger.Stilling;
import no.nav.metrics.MetricsFactory;
import no.nav.metrics.Timer;
import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.stream.Collectors.toList;

public class StillingerEndpoint {

    private SolrClient mainSolrClient;
    private JavaAsyncSolrClient mainSolrClientAsync;
    private Logger logger = LoggerFactory.getLogger(StillingerEndpoint.class);

    @Inject
    SupportEndpoint supportEndpoint;

    @Inject
    LedighetsEndpoint ledighetsEndpoint;

    public StillingerEndpoint() {
        String maincoreUri = String.format("%s/mainwithlocal", System.getProperty("stilling.solr.url"));
        mainSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(maincoreUri).build();
        mainSolrClientAsync = JavaAsyncSolrClient.create(maincoreUri);
    }

    @Timed
    @Cacheable(value = "yrkesomrader", keyGenerator = "cacheKeyGenerator")
    public List<Bransje> getYrkesomrader(String fylkesnummer, Filtervalg filtervalg) {
        String query = String.format("FYLKE_ID:%s", fylkesnummer == null ? "*" : fylkesnummer);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("YRKGR_LVL_1");
        solrQuery.addFacetField("YRKGR_LVL_1_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = mainSolrClient.query(solrQuery);
            return BransjeForFylkeTransformer.getBransjeForFylke(resp.getFacetField("YRKGR_LVL_1"), resp.getFacetField("YRKGR_LVL_1_ID")).stream()
                    .map(yrkesomrade -> yrkesomrade.withAntallStillinger(getAntallStillingerForYrkesomrade(yrkesomrade.getId(), filtervalg)))
                    .collect(toList());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
        }
    }

    private int getAntallStillingerForYrkesomrade(String yrkesomradeid, Filtervalg filtervalg) {
        String filter = "YRKGR_LVL_1_ID:" + yrkesomradeid;
        return getAntallStillingerForFiltrering(filtervalg, filter, "StillingerEndpoint.getAntallStillingerForYrkesomrade");
    }

    private int getAntallStillingerForYrkesgruppe(String yrkesgruppeid, Filtervalg filtervalg) {
        String filter = "YRKGR_LVL_2_ID:" + yrkesgruppeid;
        return getAntallStillingerForFiltrering(filtervalg, filter, "StillingerEndpoint.getAntallStillingerForYrkesgruppe");
    }

    private int getAntallStillingerForFiltrering(Filtervalg filtervalg, String filter, String timernavn) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        addOmradeFilter(henteAntallStillingerQuery, filtervalg);
        henteAntallStillingerQuery.addFilterQuery(filter);
        henteAntallStillingerQuery.addFacetField("ANTALLSTILLINGER");
        henteAntallStillingerQuery.setRows(0);

        Timer timer = MetricsFactory.createTimer(timernavn);
        timer.start();
        int antallStillinger = getAntallStillinger(henteAntallStillingerQuery);
        timer.stop();
        timer.report();
        return antallStillinger;
    }

    @Timed
    @Cacheable(value = "yrkesgrupperMedAntallStillinger", keyGenerator = "cacheKeyGenerator")
    public List<Bransje> getYrkesgrupperForYrkesomrade(String yrkesomradeid, Filtervalg filtervalg) {
        QueryResponse yrkesgruppeResponse = supportEndpoint.getYrkesgrupperForYrkesomrade(yrkesomradeid);
        return StillingstypeForYrkesomradeTransformer.getStillingstyperForYrkesgrupper(yrkesgruppeResponse.getResults()).stream()
                .map(stillingstype -> stillingstype.withAntallStillinger(getAntallStillingerForYrkesgruppe(stillingstype.getId(), filtervalg)))
                .collect(toList());
    }

    @Timed
    @Cacheable(value = "ledighetstallForKommuner", keyGenerator = "cacheKeyGenerator")
    public List<OmradeStilling> getLedighetstallForKommuner(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylker, List<String> kommuner, String periode) {
        if (!fylker.isEmpty()) {
            kommuner.addAll(supportEndpoint.finnKommunerTilFylker(fylker));
        }

        List<String> filter = getFilterForYrker(yrkesomradeid, yrkesgrupper);
        Map<String, Integer> ledigestillingerForOmrader = hentLedigeStillingerForOmrader(kommuner, filter, "KOMMUNE_ID");
        Map<String, Integer> ledighetForOmrader = ledighetsEndpoint.getLedighetForKommuner(yrkesomradeid, yrkesgrupper, fylker, kommuner, periode);
        return lagOmradestillinger(kommuner, ledigestillingerForOmrader, ledighetForOmrader);
    }

    @Timed
    @Cacheable(value = "ledighetstallForFylker", keyGenerator = "cacheKeyGenerator")
    public List<OmradeStilling> getLedighetstallForFylker(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylker, String periode) {
        List<String> filter = getFilterForYrker(yrkesomradeid, yrkesgrupper);

        Map<String, Integer> ledigestillingerForOmrader = hentLedigeStillingerForOmrader(fylker, filter, "FYLKE_ID");
        Map<String, Integer> ledighetForOmrader = ledighetsEndpoint.getLedighetForFylker(yrkesomradeid, yrkesgrupper, fylker, periode);

        return lagOmradestillinger(fylker, ledigestillingerForOmrader, ledighetForOmrader);
    }

    private List<String> getFilterForYrker(String yrkesomrade, List<String> yrkesgrupper) {
        List<String> filter = new ArrayList<>();
        if (yrkesomrade != null) {
            filter.add(String.format("YRKGR_LVL_1_ID:(%s)", yrkesomrade));
        }
        if (!yrkesgrupper.isEmpty()) {
            filter.add(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
        }
        return filter;
    }

    private List<OmradeStilling> lagOmradestillinger(List<String> omrader, Map<String, Integer> ledigeStillinger, Map<String, Integer> arbeidledighet) {
        return omrader.stream()
                .map(omrade -> new OmradeStilling(omrade, arbeidledighet.getOrDefault(omrade, 0), ledigeStillinger.getOrDefault(omrade, 0)))
                .collect(toList());
    }

    @Timed
    public int getAntallStillingerForValgtOmrade(Filtervalg filtervalg) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");

        addOmradeFilter(henteAntallStillingerQuery, filtervalg);
        henteAntallStillingerQuery.addFacetField("ANTALLSTILLINGER");
        henteAntallStillingerQuery.setRows(0);

        return getAntallStillinger(henteAntallStillingerQuery);
    }

    private int getAntallStillinger(SolrQuery henteAntallStillingerQuery) {
        try {
            QueryResponse antallStillingerResponse = mainSolrClient.query(henteAntallStillingerQuery);
            return AntallStillingerExtractor.getAntallStillinger(antallStillingerResponse);
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av antall stillinger fra solr maincore", e.getCause());
            throw new ApplicationException("Feil ved henting av antall stillinger fra solr maincore", e.getCause());
        }
    }

    @Timed
    @Cacheable(value = "stillingsannonser", keyGenerator = "cacheKeyGenerator")
    public List<Stilling> getStillinger(List<String> yrkesgrupper, Filtervalg filtervalg) {
        SolrQuery stillingerQuery = new SolrQuery("*:*");
        stillingerQuery.addFilterQuery(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
        addOmradeFilter(stillingerQuery, filtervalg);
        stillingerQuery.setRows(Integer.MAX_VALUE);

        try {
            return StillingTransformer.getStillinger(mainSolrClient.query(stillingerQuery).getResults());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillinger", e.getCause());
            throw new ApplicationException("Feil ved henting av stillinger", e.getCause());
        }
    }

    private void addOmradeFilter(SolrQuery query, Filtervalg filtervalg) {
        List<String> statements = new ArrayList<>();

        if (filtervalg.fylker != null && !filtervalg.fylker.isEmpty()) {
            statements.add(String.format("FYLKE_ID:(%s)", StringUtils.join(filtervalg.fylker, " OR ")));
        }
        if (filtervalg.kommuner != null && !filtervalg.kommuner.isEmpty()) {
            statements.add(String.format("KOMMUNE_ID:(%s)", StringUtils.join(filtervalg.kommuner, " OR ")));
        }

        if(filtervalg.eoseu) {
            statements.add("LANDGRUPPE:EOSEU");
        }

        if(filtervalg.restenavverden) {
            statements.add("LANDGRUPPE:\"resten av verden\"");
        }

        if (!statements.isEmpty()) {
            query.addFilterQuery(StringUtils.join(statements, " OR "));
        }
    }

    private Map<String, Integer> hentLedigeStillingerForOmrader(List<String> omrader, List<String> filter, String filterKeyNavn) {
        String query = "*:*";
        Map<String, Integer> responses = new HashMap<>();
        List<AsyncSolrQuery> asyncQueries = new ArrayList<>();
        Timer metricsTimer = MetricsFactory.createTimer(this.getClass().toString() + ".hentLedigeStillingerForOmrader");
        metricsTimer.start();

        for (String kommuneid : omrader) {
            SolrQuery solrQuery = new SolrQuery(query);
            if (filter != null) {
                filter.forEach(solrQuery::addFilterQuery);
            }
            solrQuery.setRows(0);
            solrQuery.addFacetField("ANTALLSTILLINGER");
            String filterquery = filterKeyNavn + ":" + kommuneid;
            solrQuery.addFilterQuery(filterquery);
            asyncQueries.add(new AsyncSolrQuery(kommuneid, mainSolrClientAsync.query(solrQuery)));
        }

        asyncQueries.forEach(asyncQuery -> responses.put(asyncQuery.getKommuneid(), StillingerForOmradeTransformer.getAntallStillingerFraQuery(asyncQuery.getResponse())));
        metricsTimer.stop();
        metricsTimer.report();
        return responses;
    }

    private class AsyncSolrQuery {
        CompletionStage<QueryResponse> query;
        String kommuneid;

        AsyncSolrQuery(String kommuneid, CompletionStage<QueryResponse> query) {
            this.query = query;
            this.kommuneid = kommuneid;
        }

        String getKommuneid() {
            return kommuneid;
        }

        public QueryResponse getResponse() {
            return this.query.toCompletableFuture().join();
        }
    }
}
