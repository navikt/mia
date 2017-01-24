package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.transformers.GeografiTransformer;
import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.metrics.aspects.Timed;
import no.nav.modig.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class SupportEndpoint {

    private SolrClient supportSolrClient;
    private Map<String, String> strukturkodeTilIdMapping, idTilStrukturkodeMapping;
    private Map<String, List<String>> strukturkodeTilYrkgrLvl2Mapping, yrkgrLvl2TilStrukturkodeMapping;
    private Logger logger = LoggerFactory.getLogger(SupportEndpoint.class);

    public SupportEndpoint() {
        String supportCoreUri = String.format("%s/supportcore", System.getProperty("stilling.solr.url"));
        supportSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(supportCoreUri).build();
        createStrukturkodeMappingForGeografi();
        createStrukturkodeMappingForYrkesgruppe();
    }

    Map<String, String> getStrukturkodeTilIdMapping() {
        return strukturkodeTilIdMapping;
    }

    Map<String, String> getIdTilStrukturkodeMapping() {
        return idTilStrukturkodeMapping;
    }

    public Map<String, List<String>> getStrukturkodeTilYrkgrLvl2Mapping() {
        return strukturkodeTilYrkgrLvl2Mapping;
    }

    public Map<String, List<String>> getYrkgrLvl2TilStrukturkodeMapping() {
        return yrkgrLvl2TilStrukturkodeMapping;
    }

    private void createStrukturkodeMappingForGeografi() {
        strukturkodeTilIdMapping = new HashMap<>();
        idTilStrukturkodeMapping = new HashMap<>();
        QueryResponse resp = getFylkerOgKommunerFraSolr();

        SolrDocumentList results = resp.getResults();

        results.forEach(document -> {
            String id = (String) document.getFieldValue("ID");
            String strukturkode = (String) document.getFieldValue("STRUKTURKODE");
            if (strukturkode != null) {
                strukturkode = strukturkode.replace("NO", "");

                if (strukturkode.contains(".")) {
                    strukturkode = strukturkode.split("\\.")[1];
                }
                idTilStrukturkodeMapping.put(id, strukturkode);
                strukturkodeTilIdMapping.put(strukturkode, id);
            }
        });
    }

    private void createStrukturkodeMappingForYrkesgruppe() {
        strukturkodeTilYrkgrLvl2Mapping = new HashMap<>();
        yrkgrLvl2TilStrukturkodeMapping = new HashMap<>();
        QueryResponse resp = getStillingstyperFraSolr();

        SolrDocumentList results = resp.getResults();

        results.forEach(document -> {
            List<String> yrkgrLvl2IdListe = new ArrayList<>();

            Collection<Object> parents = document.getFieldValues("PARENT");
            if(parents != null) {
                yrkgrLvl2IdListe.addAll(parents.stream().map(Object::toString).collect(Collectors.toList()));
            }
            String strukturkode = (String) document.getFieldValue("STRUKTURKODE");
            if (strukturkode != null) {
                strukturkodeTilYrkgrLvl2Mapping.put(strukturkode, yrkgrLvl2IdListe);

                for (String id : yrkgrLvl2IdListe) {
                    if (yrkgrLvl2TilStrukturkodeMapping.containsKey(id)) {
                        List<String> p = yrkgrLvl2TilStrukturkodeMapping.get(id);
                        p.add(strukturkode);
                    } else {
                        List<String> strukturkodeListe = new ArrayList<>();
                        strukturkodeListe.add(strukturkode);
                        yrkgrLvl2TilStrukturkodeMapping.put(id, strukturkodeListe);
                    }
                }
            }
        });
    }

    private QueryResponse getStillingstyperFraSolr() {
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

    private QueryResponse getFylkerOgKommunerFraSolr() {
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
