package no.nav.fo.consumer.endpoints;

import io.ino.solrs.JavaAsyncSolrClient;
import no.nav.fo.consumer.extractor.AntallStillingerExtractor;
import no.nav.fo.consumer.transformers.BransjeForFylkeTransformer;
import no.nav.fo.consumer.transformers.StillingTransformer;
import no.nav.fo.consumer.transformers.StillingerForOmradeTransformer;
import no.nav.fo.consumer.transformers.StillingstypeForYrkesomradeTransformer;
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
    SupportEndpoint supportEndpointUtils;

    @Inject
    LedighetsEndpoint ledighetsEndpoint;

    public StillingerEndpoint() {
        String maincoreUri = String.format("%smaincore", System.getProperty("stilling.solr.url"));
        mainSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(maincoreUri).build();
        mainSolrClientAsync = JavaAsyncSolrClient.create(maincoreUri);
    }

    @Timed
    public List<Bransje> getYrkesomrader(String fylkesnummer, List<String> fylker, List<String> kommuner) {
        String query = String.format("FYLKE_ID:%s", fylkesnummer == null ? "*" : fylkesnummer);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("YRKGR_LVL_1");
        solrQuery.addFacetField("YRKGR_LVL_1_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = mainSolrClient.query(solrQuery);
            return BransjeForFylkeTransformer.getBransjeForFylke(resp.getFacetField("YRKGR_LVL_1"), resp.getFacetField("YRKGR_LVL_1_ID")).stream()
                    .map(yrkesomrade -> yrkesomrade.withAntallStillinger(getAntallStillingerForYrkesomrade(yrkesomrade.getId(), fylker, kommuner)))
                    .collect(toList());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
        }
    }

    private int getAntallStillingerForYrkesomrade(String yrkesomradeid, List<String> fylker, List<String> kommuner) {
        String filter = "YRKGR_LVL_1_ID:" + yrkesomradeid;
        return getAntallStillingerForFiltrering(fylker, kommuner, filter, "StillingerEndpoint.getAntallStillingerForYrkesomrade");
    }

    private int getAntallStillingerForYrkesgruppe(String yrkesgruppeid, List<String> fylker, List<String> kommuner) {
        String filter = "YRKGR_LVL_2_ID:" + yrkesgruppeid;
        return getAntallStillingerForFiltrering(fylker, kommuner, filter, "StillingerEndpoint.getAntallStillingerForYrkesgruppe");
    }

    private int getAntallStillingerForFiltrering(List<String> fylker, List<String> kommuner, String filter, String timernavn) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        addFylkerOgKommunerFilter(henteAntallStillingerQuery, fylker, kommuner);
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
    public List<Bransje> getYrkesgrupperForYrkesomrade(String yrkesomradeid, List<String> fylker, List<String> kommuner) {
        QueryResponse yrkesgruppeResponse = supportEndpointUtils.getYrkesgrupperForYrkesomrade(yrkesomradeid);
        return StillingstypeForYrkesomradeTransformer.getStillingstyperForYrkesgrupper(yrkesgruppeResponse.getResults()).stream()
                .map(stillingstype -> stillingstype.withAntallStillinger(getAntallStillingerForYrkesgruppe(stillingstype.getId(), fylker, kommuner)))
                .collect(toList());
    }

    @Timed
    public List<OmradeStilling> getLedighetstallForOmrader(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylker, List<String> kommuner, String periode) {
        if (fylker.size() > 0) {
            kommuner.addAll(supportEndpointUtils.finnKommunerTilFylke(fylker));
        }

        List<String> filter = new ArrayList<>();
        if (yrkesomradeid != null) {
            filter.add(String.format("YRKGR_LVL_1_ID:(%s)", yrkesomradeid));
        }
        if (yrkesgrupper.size() > 0) {
            filter.add(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
        }

        Map<String, Integer> ledigestillingerForOmrader = hentLedigeStillingerForKommuner(kommuner, filter);
        Map<String, Integer> ledighetForOmrader = ledighetsEndpoint.getLedighetForOmrader(yrkesomradeid, yrkesgrupper, fylker, kommuner, periode);

        return lagOmradestillinger(kommuner, ledigestillingerForOmrader, ledighetForOmrader);
    }

    private List<OmradeStilling> lagOmradestillinger(List<String> kommuner, Map<String, Integer> ledigeStillinger, Map<String, Integer> arbeidledighet) {
        return kommuner.stream()
                .map(kommune -> new OmradeStilling(kommune, arbeidledighet.getOrDefault(kommune, 0), ledigeStillinger.getOrDefault(kommune, 0)))
                .collect(toList());
    }

    @Timed
    public int getAntallStillingerForValgtOmrade(List<String> fylker, List<String> kommuner) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");

        addFylkerOgKommunerFilter(henteAntallStillingerQuery, fylker, kommuner);
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
    public List<Stilling> getStillinger(List<String> yrkesgrupper, List<String> fylker, List<String> kommuner) {
        SolrQuery stillingerQuery = new SolrQuery("*:*");
        stillingerQuery.addFilterQuery(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
        addFylkerOgKommunerFilter(stillingerQuery, fylker, kommuner);
        stillingerQuery.setRows(Integer.MAX_VALUE);

        try {
            return StillingTransformer.getStillinger(mainSolrClient.query(stillingerQuery).getResults());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillinger", e.getCause());
            throw new ApplicationException("Feil ved henting av stillinger", e.getCause());
        }
    }

    private void addFylkerOgKommunerFilter(SolrQuery query, List<String> fylker, List<String> kommuner) {
        List<String> statements = new ArrayList<>();

        if (fylker != null && !fylker.isEmpty()) {
            statements.add(String.format("FYLKE_ID:(%s)", StringUtils.join(fylker, " OR ")));
        }
        if (kommuner != null && !kommuner.isEmpty()) {
            statements.add(String.format("KOMMUNE_ID:(%s)", StringUtils.join(kommuner, " OR ")));
        }

        if (!statements.isEmpty()) {
            query.addFilterQuery(StringUtils.join(statements, " OR "));
        }
    }

    private Map<String, Integer> hentLedigeStillingerForKommuner(List<String> kommuner, List<String> filter) {
        String query = "*:*";
        Map<String, Integer> responses = new HashMap<>();
        List<AsyncSolrQuery> asyncQueries = new ArrayList<>();
        Timer metricsTimer = MetricsFactory.createTimer(this.getClass().toString() + ".hentLedigeStillingerForKommuner");
        metricsTimer.start();

        for (String kommuneid : kommuner) {
            SolrQuery solrQuery = new SolrQuery(query);
            if (filter != null) {
                filter.forEach(solrQuery::addFilterQuery);
            }
            solrQuery.setRows(0);
            solrQuery.addFacetField("ANTALLSTILLINGER");
            String filterquery = "KOMMUNE_ID" + ":" + kommuneid;
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
