package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.kodeverk.FylkerOgKommunerReader;
import no.nav.fo.consumer.transformers.BransjeForFylkeTransformer;
import no.nav.fo.consumer.transformers.StillingerForKommuneTransformer;
import no.nav.fo.consumer.transformers.StillingstypeForYrkesomradeTransformer;
import no.nav.fo.mia.domain.stillinger.Bransje;
import no.nav.fo.mia.domain.stillinger.KommuneStilling;
import no.nav.fo.mia.domain.stillinger.Stillingstype;
import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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
    public List<KommuneStilling> getAntallStillingerForAlleKommuner() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("KOMMUNE_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = mainSolrClient.query(solrQuery);
            return StillingerForKommuneTransformer.getStillingerForKommuner(resp.getFacetField("KOMMUNE_ID").getValues(), null, FylkerOgKommunerReader.getFylkerOgKommuner());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillinger fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av stillinger fra solr", e.getCause());
        }
    }

    @Timed
    public List<Bransje> getYrkesomraderForFylke(String fylkesnummer) {
        String query = String.format("FYLKE_ID:%s", fylkesnummer == null ? "*" : fylkesnummer);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("YRKGR_LVL_1");
        solrQuery.addFacetField("YRKGR_LVL_1_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = mainSolrClient.query(solrQuery);
            return BransjeForFylkeTransformer.getBransjeForFylke(resp.getFacetField("YRKGR_LVL_1"), resp.getFacetField("YRKGR_LVL_1_ID"));
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
        }
    }

    @Timed
    public List<Stillingstype> getYrkesgrupperForYrkesomrade(String yrkesomradeid) {
        String query = String.format("DOKUMENTTYPE:STILLINGSTYPE AND PARENT:%s", yrkesomradeid);
        SolrQuery solrQuery = new SolrQuery(query);
        if(yrkesomradeid.equals("*")) {
            solrQuery.setRows(10000);
        }

        try {
            QueryResponse resp = supportSolrClient.query(solrQuery);
            return StillingstypeForYrkesomradeTransformer.getStillingstyperForYrkesomrade(resp.getResults());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
            throw new ApplicationException("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
        }
    }
}
