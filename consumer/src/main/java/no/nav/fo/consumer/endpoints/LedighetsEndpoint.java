package no.nav.fo.consumer.endpoints;

import no.nav.fo.consumer.service.SupportMappingService;
import no.nav.fo.mia.domain.Filtervalg;
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

import javax.inject.Inject;
import java.io.IOException;
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

    @Timed
    public Map<String, Integer> getArbeidsledighetForSisteTrettenMaaneder(Filtervalg filtervalg) {
        return getStatistikkSisteTrettenMaaneder(arbeidsledighetSolrClient, filtervalg);
    }

    @Timed
    public Map<String, Integer> getLedigestillingerForSisteTrettenMaaneder(Filtervalg filtervalg) {
        return getStatistikkSisteTrettenMaaneder(ledigestillingerSolrClient, filtervalg);
    }

    private Map<String, Integer> getStatistikkSisteTrettenMaaneder(SolrClient solrClient, Filtervalg filtervalg) {
        Map<String, String> idTilStrukturKode = supportMappingService.getIdTilStrukturkodeMapping();

        List<String> fylkesnr = filtervalg.fylker.stream().map(idTilStrukturKode::get).filter(Objects::nonNull).collect(toList());
        List<String> kommunenr = filtervalg.kommuner.stream().map(idTilStrukturKode::get).filter(Objects::nonNull).collect(toList());

        return hentStatistikkSisteTrettenMaanederFraSolr(solrClient, filtervalg.yrkesomrade, filtervalg.yrkesgrupper, fylkesnr, kommunenr);
    }

    private Map<String, Integer> hentStatistikkSisteTrettenMaanederFraSolr(SolrClient client, String yrkesomrade, List<String> yrkesgrupper, List<String> fylkesnr, List<String> kommunenr) {
        SolrQuery solrQuery = createSolrQueryForFiltreringsvalg(yrkesomrade, yrkesgrupper, fylkesnr, kommunenr);

        solrQuery.addFacetField("PERIODE");
        try {
            QueryResponse resp = client.query(solrQuery);
            Map<String, Integer> perioderMedAntall = new HashMap<>();
            resp.getFacetField("PERIODE").getValues()
                    .forEach(periode -> perioderMedAntall.put(periode.getName(), (int) periode.getCount()));

            return perioderMedAntall;
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av ledighet fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av ledighet fra solr", e.getCause());
        }
    }

    @Timed
    public String getSisteOpplastedeMaaned() {
        String query = "*:*";
        SolrQuery solrQuery = new SolrQuery(query);
        solrQuery.setRows(0);
        solrQuery.addFacetField("PERIODE");

        try {
            QueryResponse response = arbeidsledighetSolrClient.query(solrQuery);

            List<String> dates = response.getFacetField("PERIODE").getValues().stream()
                    .map(FacetField.Count::getName)
                    .sorted()
                    .collect(toList());

            return dates.get(dates.size() -1);
        } catch (SolrServerException | IOException e) {
            logger.error("Feil ved henting av perioder fra solr", e.getCause());
            throw new ApplicationException("Feil ved henting av perioder fra solr", e.getCause());
        }
    }

    @Timed
    public Map<String, Integer> getLedighetForOmrader(String yrkesomradeid, List<String> yrkesgrupper, List<String> fylker, List<String> kommuner, String periode) {
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

        SolrQuery solrQuery = createSolrQueryForFiltreringsvalg(yrkesomradeid, yrkesgrupper, fylkesnr, kommunenr);
        solrQuery.addFilterQuery("PERIODE:" + periode);
        solrQuery.addFacetField("KOMMUNENR");

        try {
            QueryResponse resp = arbeidsledighetSolrClient.query(solrQuery);
            Map<String, Integer> ledighetPerFylke = new HashMap<>();
            resp.getFacetField("KOMMUNENR").getValues().stream()
                    .filter(kommune -> (int) kommune.getCount() > 0)
                    .forEach(kommune -> ledighetPerFylke.put(strukturkodeTilIdMapping.get(kommune.getName()), (int)kommune.getCount()));

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
