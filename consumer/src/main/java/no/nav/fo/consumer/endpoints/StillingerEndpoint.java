package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.transformers.StillingerForKommuneTransformer;
import no.nav.fo.consumer.transformers.GeografiTransformer;
import no.nav.fo.consumer.extractor.AntallStillingerExtractor;
import no.nav.fo.consumer.transformers.BransjeForFylkeTransformer;
import no.nav.fo.mia.domain.stillinger.KommuneStilling;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.fo.consumer.transformers.StillingstypeForYrkesomradeTransformer;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StillingerEndpoint {

    private SolrClient mainSolrClient, supportSolrClient;
    private Logger logger = LoggerFactory.getLogger(StillingerEndpoint.class);

    public StillingerEndpoint() {
        String maincoreUri = String.format("%s/maincore", System.getProperty("stilling.solr.url"));
        String supportCoreUri = String.format("%s/supportcore", System.getProperty("stilling.solr.url"));
        mainSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(maincoreUri).build();
        supportSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(supportCoreUri).build();
    }

    @Timed
    @Cacheable("antallStillingerForAlleKommuner")
    public List<KommuneStilling> getAntallStillingerForAlleKommuner() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("KOMMUNE_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = mainSolrClient.query(solrQuery);
            return StillingerForKommuneTransformer.getStillingerForKommuner(resp.getFacetField("KOMMUNE_ID").getValues(), null, getFylkerOgKommuner());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillinger fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av stillinger fra solr", e.getCause());
        }
    }

    @Timed
    @Cacheable("yrkesomraderForFylke")
    public List<Bransje> getYrkesomraderForFylke(String fylkesnummer) {
        String query = String.format("FYLKE_ID:%s", fylkesnummer == null ? "*" : fylkesnummer);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("YRKGR_LVL_1");
        solrQuery.addFacetField("YRKGR_LVL_1_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = mainSolrClient.query(solrQuery);
            return BransjeForFylkeTransformer.getBransjeForFylke(resp.getFacetField("YRKGR_LVL_1"), resp.getFacetField("YRKGR_LVL_1_ID")).stream()
                    .map(yrkesomrade -> yrkesomrade.withAntallStillinger(getAntallStillingerForYrkesomrade(yrkesomrade.getId())))
                    .collect(Collectors.toList());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
        }
    }

    @Timed
    @Cacheable("antallStillingerYrkesomrade")
    private int getAntallStillingerForYrkesomrade(String yrkesomradeid) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        henteAntallStillingerQuery.addFilterQuery("YRKGR_LVL_1_ID:"+yrkesomradeid);
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
    public List<Bransje> getYrkesgrupperForYrkesomrade(String yrkesomradeid) {
        SolrQuery henteYrkesgrupperQuery = new SolrQuery("*:*");
        henteYrkesgrupperQuery.addFilterQuery("PARENT:"+yrkesomradeid);
        henteYrkesgrupperQuery.addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE");

        try {
            QueryResponse yrkesgruppeResponse = supportSolrClient.query(henteYrkesgrupperQuery);
            return StillingstypeForYrkesomradeTransformer.getStillingstyperForYrkesgrupper(yrkesgruppeResponse.getResults()).stream()
                    .map(stillingstype -> stillingstype.withAntallStillinger(getAntallStillingerForYrkesgruppe(stillingstype.getId())))
                    .collect(Collectors.toList());

        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
            throw new ApplicationException("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
        }
    }

    @Timed
    @Cacheable("antallStillingerYrkesgruppe")
    private int getAntallStillingerForYrkesgruppe(String yrkesgruppeid) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        henteAntallStillingerQuery.addFilterQuery("YRKGR_LVL_2_ID:"+yrkesgruppeid);
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
}
