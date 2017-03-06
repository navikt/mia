package no.nav.fo.consumer.service;

import no.nav.fo.consumer.endpoints.SupportEndpoint;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

public class SupportMappingService {

    @Inject
    SupportEndpoint supportEndpoint;

    private Logger logger = LoggerFactory.getLogger(SupportMappingService.class);
    private Map<String, String> strukturkodeTilIdMapping, idTilStrukturkodeMapping;
    private Map<String, List<String>> strukturkodeTilYrkgrLvl2Mapping, yrkgrLvl2TilStrukturkodeMapping;
    private Map<String, List<String>> yrkgrLvl2TilYrkgrLvl1Mapping;

    @PostConstruct
    public void createMapping() {
        createStrukturkodeMappingForGeografi();
        createStrukturkodeMappingForYrkesgruppe();
        createYrkgrLvl1ForYrkgrLvl2Mapping();
    }

    public Map<String, String> getStrukturkodeTilIdMapping() {
        return strukturkodeTilIdMapping;
    }

    public Map<String, String> getIdTilStrukturkodeMapping() {
        return idTilStrukturkodeMapping;
    }

    public Map<String, List<String>> getStrukturkodeTilYrkgrLvl2Mapping() {
        return strukturkodeTilYrkgrLvl2Mapping;
    }

    public Map<String, List<String>> getYrkgrLvl2TilYrkgrLvl1Mapping() {
        return yrkgrLvl2TilYrkgrLvl1Mapping;
    }

    private void createStrukturkodeMappingForGeografi() {
        logger.info("Lager ny mapping for geografi");
        strukturkodeTilIdMapping = new HashMap<>();
        idTilStrukturkodeMapping = new HashMap<>();
        QueryResponse resp = supportEndpoint.getRelevanteOmraderFraSolr();

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
        logger.info("Lager ny mapping for yrkesgrupper");
        yrkgrLvl2TilStrukturkodeMapping = new HashMap<>();
        strukturkodeTilYrkgrLvl2Mapping = new HashMap<>();

        QueryResponse resp = supportEndpoint.getStillingstyperFraSolr();

        SolrDocumentList results = resp.getResults();

        results.forEach(document -> {
            List<String> yrkgrLvl2IdListe = addParentIdToList(document);
            String strukturkode = (String) document.getFieldValue("STRUKTURKODE");
            if (strukturkode != null) {
                addStrukturkodeToList(yrkgrLvl2IdListe, strukturkode);
            }
        });
    }

    private void addStrukturkodeToList(List<String> yrkgrLvl2IdListe, String strukturkode) {
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

    private List<String> addParentIdToList(SolrDocument document) {
        List<String> yrkgrLvl2IdListe = new ArrayList<>();

        Collection<Object> parents = document.getFieldValues("PARENT");
        if (parents != null) {
            yrkgrLvl2IdListe.addAll(parents.stream().map(Object::toString).collect(Collectors.toList()));
        }
        return yrkgrLvl2IdListe;
    }

    private void createYrkgrLvl1ForYrkgrLvl2Mapping() {
        logger.info("Lager yrkesgrupper lvl1 for lvl2 mapping");
        yrkgrLvl2TilYrkgrLvl1Mapping = new HashMap<>();

        yrkgrLvl2TilStrukturkodeMapping.keySet().forEach(lvl2 -> {
            QueryResponse response = supportEndpoint.getYrkgrLvl1IdFraSolr(lvl2);

            SolrDocumentList results = response.getResults();
            if (!results.isEmpty()) {
                Collection<Object> parents = results.get(0).getFieldValues("PARENT");

                List<String> yrkgrLvl1IdListe = new ArrayList<>();
                if (parents != null) {
                    yrkgrLvl1IdListe.addAll(parents.stream().map(Object::toString).collect(Collectors.toList()));
                }
                yrkgrLvl2TilYrkgrLvl1Mapping.put(lvl2, yrkgrLvl1IdListe);
            }
        });
    }
}
