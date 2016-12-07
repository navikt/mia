package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.transformers.BransjeLvl1ForFylkeTransformer;
import no.nav.fo.consumer.transformers.GeografiTransformer;
import no.nav.fo.consumer.transformers.StillingerForKommuneTransformer;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.fo.mia.domain.stillinger.BransjeLvl1;
import no.nav.fo.mia.domain.stillinger.KommuneStilling;
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

public class StillingerEndpoint {

    private SolrClient solrClientMain;
    private SolrClient solrClientSupport;
    private Logger logger = LoggerFactory.getLogger(StillingerEndpoint.class);

    public StillingerEndpoint() {
        String mainCoreUri = String.format("%s/maincore", System.getProperty("stilling.solr.url"));
        String supportCoreUri = String.format("%s/supportcore", System.getProperty("stilling.solr.url"));
        solrClientMain = new HttpSolrClient.Builder().withBaseSolrUrl(mainCoreUri).build();
        solrClientSupport = new HttpSolrClient.Builder().withBaseSolrUrl(supportCoreUri).build();
    }

    @Timed
    public List<KommuneStilling> getAntallStillingerForAlleKommuner() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("KOMMUNE_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = solrClientMain.query(solrQuery);
            return StillingerForKommuneTransformer.getStillingerForKommuner(resp.getFacetField("KOMMUNE_ID").getValues(), null, getFylkerOgKommuner());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillinger fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av stillinger fra solr", e.getCause());
        }
    }

    @Timed
    public List<BransjeLvl1> getBransjerLvl1ForFylke(String fylkesnummer) {
        String query = String.format("FYLKE_ID:%s", fylkesnummer == null ? "*" : fylkesnummer);
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.addFacetField("YRKGR_LVL_1");
        solrQuery.addFacetField("YRKGR_LVL_1_ID");
        solrQuery.setRows(0);

        try {
            QueryResponse resp = solrClientMain.query(solrQuery);
            return BransjeLvl1ForFylkeTransformer.getBransjeLvlForFylke(resp.getFacetField("YRKGR_LVL_1"), resp.getFacetField("YRKGR_LVL_1_ID"));
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av bransjer(lvl1) fra solr", e.getCause());
        }
    }

    @Timed
    @Cacheable("fylkerogkommuner")
    public List<Omrade> getFylkerOgKommuner() {
        SolrQuery query = new SolrQuery("NIVAA:[2 TO 3] AND DOKUMENTTYPE:GEOGRAFI");
        query.setRows(500);

        try {
            QueryResponse resp = solrClientSupport.query(query);
            return GeografiTransformer.transformResponseToFylkerOgKommuner(resp.getResults());
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av geografi fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av geografi fra solr", e.getCause());
        }
    }
}
