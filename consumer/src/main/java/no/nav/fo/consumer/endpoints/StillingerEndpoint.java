package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.transformers.*;
import no.nav.fo.consumer.extractor.AntallStillingerExtractor;
import no.nav.fo.mia.domain.stillinger.KommuneStilling;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.fo.mia.domain.stillinger.Stilling;
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

import java.io.IOException;
import java.util.ArrayList;
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
    @Cacheable("yrkesomrader")
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
    @Cacheable("antallStillingerYrkesomrade")
    private int getAntallStillingerForYrkesomrade(String yrkesomradeid, List<String> fylker, List<String> kommuner) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        henteAntallStillingerQuery.addFilterQuery("YRKGR_LVL_1_ID:"+yrkesomradeid);
        henteAntallStillingerQuery.addFacetField("ANTALLSTILLINGER");
        addFylkerOgKommunerFilter(henteAntallStillingerQuery, fylker, kommuner);
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

    @Timed
    @Cacheable("antallStillingerYrkesgruppe")
    private int getAntallStillingerForYrkesgruppe(String yrkesgruppeid, List<String> fylker, List<String> kommuner) {
        SolrQuery henteAntallStillingerQuery = new SolrQuery("*:*");
        henteAntallStillingerQuery.addFilterQuery("YRKGR_LVL_2_ID:"+yrkesgruppeid);
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
    @Cacheable("stillinger")
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
