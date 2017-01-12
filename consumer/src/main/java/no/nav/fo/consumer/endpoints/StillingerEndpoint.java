package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.extractor.AntallStillingerExtractor;
import no.nav.fo.consumer.transformers.BransjeForFylkeTransformer;
import no.nav.fo.consumer.transformers.GeografiTransformer;
import no.nav.fo.consumer.transformers.StillingTransformer;
import no.nav.fo.consumer.transformers.StillingstypeForYrkesomradeTransformer;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import no.nav.fo.mia.domain.stillinger.Stilling;
import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static no.nav.fo.consumer.transformers.StillingerForOmradeTransformer.getOmradeStillingForKommuner;

public class StillingerEndpoint {

    private SolrClient mainSolrClient, supportSolrClient;
    private Logger logger = LoggerFactory.getLogger(StillingerEndpoint.class);

    public StillingerEndpoint() {
        String maincoreUri = String.format("%s/maincore", System.getProperty("stilling.solr.url"));
        String supportCoreUri = String.format("%s/supportcore", System.getProperty("stilling.solr.url"));
        mainSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(maincoreUri).build();
        supportSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(supportCoreUri).build();
    }

    private Map<String, QueryResponse> queryForKommuner(List<String> kommuner, List<String> filter) {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("ANTALLSTILLINGER");
        solrQuery.setRows(0);

        if (filter != null) {
            filter.forEach(solrQuery::addFilterQuery);
        }

        Map<String, QueryResponse> responses = new HashMap<>();

        for (String kommuneid : kommuner) {
            String filterquery = "KOMMUNE_ID" + ":" + kommuneid;
            solrQuery.addFilterQuery(filterquery);
            try {
                responses.put(kommuneid, mainSolrClient.query(solrQuery));
            } catch (SolrServerException | IOException e) {
                logger.error("Feil ved henting av stillinger fra solr", e.getCause());
                throw new ApplicationException("Feil ved henting av stillinger fra solr", e.getCause());
            }
            solrQuery.removeFilterQuery(filterquery);
        }
        return responses;
    }

    private List<OmradeStilling> getAntallStillingerForKommuner(Map<String, QueryResponse> liste) {
        return liste.keySet().stream()
                .map(id -> getOmradeStillingForKommuner(id, liste.get(id).getFacetField("ANTALLSTILLINGER").getValues()))
                .collect(Collectors.toList());
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
                    .collect(Collectors.toList());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
        }
    }

    @Timed
    private int getAntallStillingerForYrkesomrade(String yrkesomradeid, List<String> fylker, List<String> kommuner) {
        String filter = "YRKGR_LVL_1_ID:" + yrkesomradeid;
        return getAntallStillingerForFiltrering(fylker, kommuner, filter);
    }

    @Timed
    private int getAntallStillingerForYrkesgruppe(String yrkesgruppeid, List<String> fylker, List<String> kommuner) {
        String filter = "YRKGR_LVL_2_ID:" + yrkesgruppeid;
        return getAntallStillingerForFiltrering(fylker, kommuner, filter);
    }

    @Timed
    private int getAntallStillingerForFiltrering(List<String> fylker, List<String> kommuner, String filter) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        addFylkerOgKommunerFilter(henteAntallStillingerQuery, fylker, kommuner);
        henteAntallStillingerQuery.addFilterQuery(filter);
        henteAntallStillingerQuery.addFacetField("ANTALLSTILLINGER");
        henteAntallStillingerQuery.setRows(0);
        return getAntallStillinger(henteAntallStillingerQuery);
    }

    @Timed
    @Cacheable("fylkerOgKommuner")
    public List<Omrade> getFylkerOgKommuner() {
        SolrQuery query = new SolrQuery("NIVAA:[2 TO 3]");
        query.addFilterQuery("DOKUMENTTYPE:GEOGRAFI");
        query.setRows(1000);

        try {
            QueryResponse resp = supportSolrClient.query(query);
            return GeografiTransformer.transformResponseToFylkerOgKommuner(resp.getResults());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av geografi fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av geografi fra solr", e.getCause());
        }
    }

    @Timed
    @Cacheable("yrkesgrupperForYrkesomrade")
    public List<Bransje> getYrkesgrupperForYrkesomrade(String yrkesomradeid, List<String> fylker, List<String> kommuner) {
        SolrQuery henteYrkesgrupperQuery = new SolrQuery("*:*");
        henteYrkesgrupperQuery.addFilterQuery("PARENT:"+yrkesomradeid);
        henteYrkesgrupperQuery.addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE");

        try {
            QueryResponse yrkesgruppeResponse = supportSolrClient.query(henteYrkesgrupperQuery);
            return StillingstypeForYrkesomradeTransformer.getStillingstyperForYrkesgrupper(yrkesgruppeResponse.getResults()).stream()
                    .map(stillingstype -> stillingstype.withAntallStillinger(getAntallStillingerForYrkesgruppe(stillingstype.getId(), fylker, kommuner)))
                    .collect(Collectors.toList());

        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
            throw new ApplicationException("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
        }
    }

    private List<String> finnKommunerTilFylke(List<String> fylker) {
        SolrQuery kommunerTilFylkeQuery = new SolrQuery("*:*");
        kommunerTilFylkeQuery.addFilterQuery("DOKUMENTTYPE:GEOGRAFI");
        kommunerTilFylkeQuery.addFilterQuery("NIVAA:3");
        kommunerTilFylkeQuery.addFilterQuery(String.format("PARENT:(%s)", StringUtils.join(fylker, " OR ")));
        kommunerTilFylkeQuery.setRows(0);
        kommunerTilFylkeQuery.addFacetField("ID");

        try {
            QueryResponse kommunerResponse = supportSolrClient.query(kommunerTilFylkeQuery);

            List<FacetField.Count> kommuner = kommunerResponse.getFacetField("ID").getValues();

            return kommuner.stream()
                    .filter(kommune -> kommune.getCount() > 0)
                    .map(FacetField.Count::getName)
                    .collect(Collectors.toList());

        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av kommuner for fylker fra solr supportcore", e.getCause());
            throw new ApplicationException("Feil ved henting av kommuner for fylker fra solr supportcore", e.getCause());
        }
    }

    @Timed
    public List<OmradeStilling> getAntallStillingerForFiltrering(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylker, List<String> kommuner) {
        if (fylker.size() > 0) {
            kommuner.addAll(finnKommunerTilFylke(fylker));
        }

        List<String> filter = new ArrayList<>();
        if (yrkesomradeid != null) {
            filter.add(String.format("YRKGR_LVL_1_ID:(%s)", yrkesomradeid));
        }
        if (yrkesgrupper.size() > 0) {
            filter.add(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
        }

        Map<String, QueryResponse> responser = queryForKommuner(kommuner, filter);

        return getAntallStillingerForKommuner(responser);
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

        if(fylker != null && !fylker.isEmpty()) {
            statements.add(String.format("FYLKE_ID:(%s)", StringUtils.join(fylker, " OR ")));
        }
        if(kommuner != null && !kommuner.isEmpty()) {
            statements.add(String.format("KOMMUNE_ID:(%s)", StringUtils.join(kommuner, " OR ")));
        }

        if(!statements.isEmpty()) {
            query.addFilterQuery(StringUtils.join(statements, " OR "));
        }
    }
}
