package no.nav.fo.transformers;

import no.nav.fo.consumer.endpoints.SupportEndpoint;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class IndekserSolr {

    @Inject
    SupportEndpoint supportEndpointUtils;

    private Logger logger = LoggerFactory.getLogger(IndekserSolr.class);
    private Map<String, List<String>> strukturkodeTilYrkgrLvl2Mapping;
    private SolrClient arbeidsledighetCore, ledigeStillingerCore;

    public IndekserSolr() {
        String arbeidsledigecoreUri = System.getProperty("miasolr.solr.arbeidsledigecore.url");
        String ledigestillingercoreUri = System.getProperty("miasolr.solr.ledigestillingercore.url");

        arbeidsledighetCore = new HttpSolrClient.Builder().withBaseSolrUrl(arbeidsledigecoreUri).build();
        ledigeStillingerCore = new HttpSolrClient.Builder().withBaseSolrUrl(ledigestillingercoreUri).build();
    }

    public void lesOgSkrivArbeidsledige() {
        strukturkodeTilYrkgrLvl2Mapping = supportEndpointUtils.getStrukturkodeTilYrkgrLvl2Mapping();

        lesArbeidsledighetCSV();
    }

    public void lesOgSkrivLedigeStillinger() {
        strukturkodeTilYrkgrLvl2Mapping = supportEndpointUtils.getStrukturkodeTilYrkgrLvl2Mapping();

        lesLedigeStillingerCSV();
    }

    private void lesLedigeStillingerCSV() {
        slettSolrIndex(ledigeStillingerCore);

        InputStream inputStreamLedigestillinger = IndekserSolr.class.getResourceAsStream("/statistikk_ledigestillinger.csv");

        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "LEDIGE_STILLINGER", "YRKGR_LVL_2_ID"};
        skrivCSVTilSolrClient(ledigeStillingerCore, inputStreamLedigestillinger, header);
    }

    private void lesArbeidsledighetCSV() {
        slettSolrIndex(arbeidsledighetCore);
        InputStream inputStreamArbeidsledige = IndekserSolr.class.getResourceAsStream("/statistikk_arbeidsledige.csv");

        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "ARBEIDSLEDIGE", "YRKGR_LVL_2_ID"};
        skrivCSVTilSolrClient(arbeidsledighetCore, inputStreamArbeidsledige, header);
    }

    private void skrivCSVTilSolrClient(SolrClient solrClient, InputStream inputStreamLedigestillinger, String[] header) {
        BufferedReader bufferedReader = null;
        String line = "";
        String cvsSplitBy = ",";

        Collection<SolrInputDocument> documents = new ArrayList<>();

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStreamLedigestillinger));

            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                String formatertLine = line.replace("\"", "");
                String[] csvFelter = formatertLine.split(cvsSplitBy);

                int antallArbeidsledige = Integer.parseInt(csvFelter[4]);

                for (int i = 0; i < antallArbeidsledige; i++) {
                    SolrInputDocument document = new SolrInputDocument();
                    document.addField(header[0], csvFelter[0]);
                    document.addField(header[1], csvFelter[1]);
                    document.addField(header[2], csvFelter[2]);
                    document.addField(header[3], csvFelter[3]);
                    document.addField(header[4], csvFelter[4]);

                    List<String> yrkgrLvl2ider = strukturkodeTilYrkgrLvl2Mapping.get(csvFelter[3]);

                    if (yrkgrLvl2ider == null || yrkgrLvl2ider.size() == 0) {
                        document.addField(header[5], "-2");
                    } else {
                        document.addField(header[5], yrkgrLvl2ider);
                    }
                    documents.add(document);
                }
            }
            oppdaterSolrIndex(solrClient, documents);
        } catch (IOException e) {
            logger.error("Feil ved lesing/skriving av fil " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.error("Feil ved lukking av fil ", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void slettSolrIndex(SolrClient solrServer) {
        try {
            solrServer.deleteByQuery("*:*");
            solrServer.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    private void oppdaterSolrIndex(SolrClient solrServer, Collection<SolrInputDocument> dokumenter) {
        try {
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.setAction(UpdateRequest.ACTION.COMMIT, true, true);
            updateRequest.add(dokumenter);

            updateRequest.process(solrServer);
        } catch (IOException | SolrServerException e) {
            logger.error("Kunne ikke oppdatere " + solrServer.getClass() + " SOLR ", e.getCause());
        }
    }
}
