package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.transformers.GeografiTransformer;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SupportEndpoint {

    @Inject
    SolrClient supportSolrClient;

    private Logger logger = LoggerFactory.getLogger(SupportEndpoint.class);

    public QueryResponse getYrkgrLvl1IdFraSolr(String yrkgrLvl2Id) {
        SolrQuery query = new SolrQuery("*:*");
        query.addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE");
        query.addFilterQuery("ID:" + yrkgrLvl2Id);
        query.setRows(10000);

        try {
            return supportSolrClient.query(query);
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillingstyper fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av stillingstyper fra solr", e.getCause());
        }
    }


    public QueryResponse getStillingstyperFraSolr() {
        SolrQuery query = new SolrQuery("*:*");
        query.addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE");
        query.setRows(10000);

        try {
            return supportSolrClient.query(query);
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillingstyper fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av stillingstyper fra solr", e.getCause());
        }
    }

    @Timed
    @Cacheable("fylkerOgKommuner")
    public List<Omrade> getFylkerOgKommuner() {
        QueryResponse resp = getFylkerOgKommunerFraSolr();
        return GeografiTransformer.transformResponseToFylkerOgKommuner(resp.getResults());
    }

    public QueryResponse getFylkerOgKommunerFraSolr() {
        SolrQuery query = new SolrQuery("NIVAA:[2 TO 3]");
        query.addFilterQuery("DOKUMENTTYPE:GEOGRAFI");
        query.setRows(1000);

        try {
            return supportSolrClient.query(query);
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av geografi fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av geografi fra solr", e.getCause());
        }
    }

    List<String> finnKommunerTilFylke(List<String> fylker) {
        SolrQuery kommunerTilFylkeQuery = new SolrQuery("*:*");
        kommunerTilFylkeQuery.addFilterQuery("DOKUMENTTYPE:GEOGRAFI");
        kommunerTilFylkeQuery.addFilterQuery("NIVAA:3");
        kommunerTilFylkeQuery.addFilterQuery(String.format("PARENT:(%s)", StringUtils.join(fylker, " OR ")));
        kommunerTilFylkeQuery.setRows(0);
        kommunerTilFylkeQuery.setFacetLimit(-1);
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

    QueryResponse getYrkesgrupperForYrkesomrade(String yrkesomradeid) {
        SolrQuery henteYrkesgrupperQuery = new SolrQuery("*:*");
        henteYrkesgrupperQuery.addFilterQuery("PARENT:" + yrkesomradeid);
        henteYrkesgrupperQuery.addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE");

        try {
            return supportSolrClient.query(henteYrkesgrupperQuery);

        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
            throw new ApplicationException("Feil ved henting av stillingstyper fra solr supportcore", e.getCause());
        }
    }
}
