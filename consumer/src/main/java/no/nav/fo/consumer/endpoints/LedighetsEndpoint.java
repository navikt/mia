package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.service.SupportMappingService;
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
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class LedighetsEndpoint {

    @Inject
    SupportMappingService supportMappingService;

    private SolrClient arbeidsledighetSolrClient, ledigestillingerSolrClient;
    private Logger logger = LoggerFactory.getLogger(StillingerEndpoint.class);

    public LedighetsEndpoint() {
        String ledigehetsCoreUri = System.getProperty("miasolr.solr.arbeidsledigecore.url");
        String ledigestillingerCoreUri = System.getProperty("miasolr.solr.ledigestillingercore.url");
        arbeidsledighetSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(ledigehetsCoreUri).build();
        ledigestillingerSolrClient = new HttpSolrClient.Builder().withBaseSolrUrl(ledigestillingerCoreUri).build();
    }

    public Map<String, Map<String, String>> getLedighetForSisteTrettenMaaneder(String yrkesomrade, List<String> yrkesgrupper, List<String> fylker, List<String> kommuner) {
        Map<String, String> idTilStrukturKode = supportMappingService.getIdTilStrukturkodeMapping();

        List<String> fylkesnr = fylker.stream().map(idTilStrukturKode::get).filter(Objects::nonNull).collect(toList());
        List<String> kommunenr = kommuner.stream().map(idTilStrukturKode::get).filter(Objects::nonNull).collect(toList());

        Timer timer = MetricsFactory.createTimer("LedighetsEndpoint.getArbeidsledighetForSisteTrettenMaaneder");
        timer.start();
        Map<String, String> arbeidsledighetForSisteTrettenMaaneder = getLedighetForSisteTrettenMaaneder(arbeidsledighetSolrClient, yrkesomrade, yrkesgrupper, fylkesnr, kommunenr, true);
        timer.stop();
        timer.report();

        timer = MetricsFactory.createTimer("LedighetsEndpoint.getLedigeStillingerForSisteTrettenMaaneder");
        timer.start();
        Map<String, String> ledigeStillingerForSisteTrettenMaaneder = getLedighetForSisteTrettenMaaneder(ledigestillingerSolrClient, yrkesomrade, yrkesgrupper, fylkesnr, kommunenr, false);
        timer.stop();
        timer.report();

        Map<String, Map<String, String>> resultat = new HashMap<>();
        resultat.put("arbeidsledighet", arbeidsledighetForSisteTrettenMaaneder);
        resultat.put("ledigestillinger", ledigeStillingerForSisteTrettenMaaneder);

        return resultat;
    }

    private Map<String, String> getLedighetForSisteTrettenMaaneder(SolrClient client, String yrkesomrade, List<String> yrkesgrupper, List<String> fylkesnr, List<String> kommunenr, boolean arbeidsledighet) {
        SolrQuery solrQuery = createSolrQueryForFiltreringsvalg(yrkesomrade, yrkesgrupper, fylkesnr, kommunenr);

        solrQuery.addFacetField("PERIODE");

        try {
            QueryResponse resp = client.query(solrQuery);
            Map<String, String> perioderMedAntall = new HashMap<>();
            resp.getFacetField("PERIODE").getValues()
                    .forEach(periode -> perioderMedAntall.put(periode.getName(),
                            arbeidsledighet ?
                                    erstattMindreEnn4MedStrek(periode.getCount()) :
                                    Long.toString(periode.getCount()))
                    );

            return perioderMedAntall;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    private String erstattMindreEnn4MedStrek(Long tallFraSolr) {
        return tallFraSolr < 4 ? "-" : Long.toString(tallFraSolr);
    }

    @Timed
    public Map<String, String> getLedighetForAlleFylker() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(0);

        LocalDateTime d = LocalDateTime.now().minusMonths(1);
        String sistePeriodeFilter = d.getYear() + "" + d.getMonthValue() + "";

        solrQuery.addFilterQuery("PERIODE:" + sistePeriodeFilter);
        solrQuery.addFacetField("FYLKESNR");

        try {
            QueryResponse resp = arbeidsledighetSolrClient.query(solrQuery);
            Map<String, String> ledighetPerFylke = new HashMap<>();
            resp.getFacetField("FYLKESNR").getValues()
                    .forEach(fylke -> ledighetPerFylke.put(fylke.getName(), erstattMindreEnn4MedStrek(fylke.getCount())));

            return ledighetPerFylke;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    @Timed
    Map<String, String> getLedighetForOmrader(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylker, List<String> kommuner) {
        Map<String, String> idTilStrukturKode = supportMappingService.getIdTilStrukturkodeMapping();
        Map<String, String> strukturkodeTilIdMapping = supportMappingService.getStrukturkodeTilIdMapping();
        List<String> fylkesnr = fylker.stream().map(idTilStrukturKode::get).filter(Objects::nonNull).collect(toList());
        List<String> kommunenr = new ArrayList<>();

        kommuner.forEach(id -> {
            String strukturkode = idTilStrukturKode.get(id);
            if (strukturkode != null && !strukturkode.equals("")) {
                kommunenr.add(strukturkode);
            }
        });

        LocalDateTime d = LocalDateTime.now().minusMonths(1);
        String sistePeriodeFilter = d.getYear() + "" + d.getMonthValue() + "";

        SolrQuery solrQuery = createSolrQueryForFiltreringsvalg(yrkesomradeid, yrkesgrupper, fylkesnr, kommunenr);

        solrQuery.addFilterQuery("PERIODE:" + sistePeriodeFilter);

        solrQuery.addFacetField("KOMMUNENR");

        try {
            QueryResponse resp = arbeidsledighetSolrClient.query(solrQuery);
            Map<String, String> ledighetPerFylke = new HashMap<>();
            resp.getFacetField("KOMMUNENR").getValues()
                    .forEach(kommune -> {
                        if ((int) kommune.getCount() > 0) {
                            String kommuneid = strukturkodeTilIdMapping.get(kommune.getName());
                            ledighetPerFylke.put(kommuneid, erstattMindreEnn4MedStrek(kommune.getCount()));
                        }
                    });

            return ledighetPerFylke;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    private SolrQuery createSolrQueryForFiltreringsvalg(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylkesnr, List<String> kommunenr) {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(0);
        if (yrkesgrupper.size() > 0) {
            solrQuery.addFilterQuery(String.format("YRKGR_LVL_2_ID:(%s)", StringUtils.join(yrkesgrupper, " OR ")));
        } else if (yrkesomradeid != null) {
            solrQuery.addFilterQuery("YRKGR_LVL_1_ID:" + yrkesomradeid);
        }

        solrQuery.addFilterQuery(createFylkeFilter(fylkesnr, kommunenr));
        return solrQuery;
    }

    private String createFylkeFilter(List<String> fylker, List<String> kommuner) {
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
