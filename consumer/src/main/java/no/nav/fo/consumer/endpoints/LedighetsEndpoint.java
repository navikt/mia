package no.nav.fo.consumer.endpoints;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class LedighetsEndpoint {

    @Inject
    SupportEndpoint supportEndpointUtils;

    private SolrClient ledighetsSolrClient;
    private Logger logger = LoggerFactory.getLogger(StillingerEndpoint.class);


    public LedighetsEndpoint() {
        String ledigehetsCoreUri = String.format("%s/arbeidsledigecore", System.getProperty("ledighet.solr.url"));
        ledighetsSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(ledigehetsCoreUri).build();
    }

    @Timed
    public Map<String, Integer> getLedighetForSisteTrettenMaaneder(List<String> yrkesgrupper, List<String> fylker, List<String> kommuner) {
        Map<String, String> idTilStrukturKode = supportEndpointUtils.getIdTilStrukturkodeMapping();

        List<String> fylkesnr = fylker.stream().map(idTilStrukturKode::get).collect(toList());
//        List<String> fylkeIder = fylker.stream().map(idTilStrukturKode::get).collect(toList());


        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(0);
        List<String> filter = new ArrayList<>();

//        if (yrkesgrupper != null && yrkesgrupper.size() > 0) {
//            filter.add(String.format("YRKESKODE:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
//        }

        if (fylkesnr != null && fylkesnr.size() > 0) {
            filter.add(String.format("FYLKESNR:(%s)", StringUtils.join(fylkesnr, " OR ")));
        }

        if (kommuner != null && kommuner.size() > 0) {
            filter.add(String.format("KOMMUNENR:(%s)", StringUtils.join(kommuner, " OR ")));
        }

        filter.forEach(solrQuery::addFilterQuery);

        solrQuery.addFacetField("PERIODE");

        try {
            QueryResponse resp = ledighetsSolrClient.query(solrQuery);
            Map<String, Integer> perioderMedAntall = new HashMap<>();
            resp.getFacetField("PERIODE").getValues()
                    .forEach(periode -> perioderMedAntall.put(periode.getName(), (int)periode.getCount()));

            return perioderMedAntall;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    @Timed
    public Map<String, Integer> getLedighetForAlleFylker() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(0);

        LocalDateTime d = LocalDateTime.now().minusMonths(1);
        String sistePeriodeFilter = d.getYear() + "" + d.getMonthValue() + "";

        solrQuery.addFilterQuery("PERIODE:" + sistePeriodeFilter);
        solrQuery.addFacetField("FYLKESNR");

        try {
            QueryResponse resp = ledighetsSolrClient.query(solrQuery);
            Map<String, Integer> ledighetPerFylke = new HashMap<>();
            resp.getFacetField("FYLKESNR").getValues()
                    .forEach(fylke -> ledighetPerFylke.put(fylke.getName(), (int)fylke.getCount()));

            return ledighetPerFylke;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    @Timed
    public Map<String, Integer> getLedighetForOmrader(List<String> fylker, List<String> kommuner) {
        Map<String, String> idTilStrukturKode = supportEndpointUtils.getIdTilStrukturkodeMapping();
        Map<String, String> strukturkodeTilIdMapping = supportEndpointUtils.getStrukturkodeTilIdMapping();
        List<String> fylkesnr = fylker.stream().map(idTilStrukturKode::get).collect(toList());
        List<String> kommunenr = kommuner.stream().map(idTilStrukturKode::get).collect(toList());

        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(0);

        LocalDateTime d = LocalDateTime.now().minusMonths(1);
        String sistePeriodeFilter = d.getYear() + "" + d.getMonthValue() + "";

        String filter = createFylkeFilter(fylkesnr, kommunenr);

        solrQuery.addFilterQuery(filter);
        solrQuery.addFilterQuery("PERIODE:" + sistePeriodeFilter);

        solrQuery.addFacetField("KOMMUNENR");

        try {
            QueryResponse resp = ledighetsSolrClient.query(solrQuery);
            Map<String, Integer> ledighetPerFylke = new HashMap<>();
            resp.getFacetField("KOMMUNENR").getValues()
                    .forEach(kommune -> {
                        if ((int) kommune.getCount() > 0) {
                            String kommuneid = strukturkodeTilIdMapping.get(kommune.getName());
                            ledighetPerFylke.put(kommuneid, (int)kommune.getCount());
                        }
                    });

            return ledighetPerFylke;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    private String createFylkeFilter(List<String> fylker, List<String> kommuner ) {
        String filter = "";

        if (fylker != null && fylker.size() > 0) {
            filter += String.format("FYLKESNR:(%s)", StringUtils.join(fylker, " OR "));
        }

        if (fylker != null && fylker.size() > 0 && kommuner != null && kommuner.size() > 0) {
            filter += " OR ";
        }

        if (kommuner != null && kommuner.size() > 0) {
            filter += String.format("KOMMUNENR:(%s)", StringUtils.join(kommuner, " OR "));
        }
        return filter;
    }
}
