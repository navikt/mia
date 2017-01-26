package no.nav.fo.transformers;

import no.nav.fo.consumer.endpoints.SupportEndpoint;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
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
import java.util.*;

public class IndekserSolr {

    @Inject
    SupportEndpoint supportEndpointUtils;

    private Logger logger = LoggerFactory.getLogger(IndekserSolr.class);
    private Map<String, List<String>> strukturkodeTilYrkgrLvl2Mapping, yrkgrLvl2TilYrkgrLvl1Mapping;
    private SolrClient arbeidsledighetCore, ledigeStillingerCore;

    public IndekserSolr() {
        String uri = System.getProperty("miasolr.solr.url") + "/internal/masternode";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uri);
        try {
            HttpResponse response = httpClient.execute(request);

            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String result = br.readLine();

            String arbeidsledigecoreUri = result + "/arbeidsledigecore";
            String ledigestillingercoreUri = result + "/ledigestillingercore";

            arbeidsledighetCore = new HttpSolrClient.Builder().withBaseSolrUrl(arbeidsledigecoreUri).build();
            ledigeStillingerCore = new HttpSolrClient.Builder().withBaseSolrUrl(ledigestillingercoreUri).build();
        } catch (IOException e) {
            logger.error("Feil ved henting av masternode fra solr", e.getCause());
        }
    }

    public void lesOgSkrivArbeidsledige(InputStream inputStream) {
        strukturkodeTilYrkgrLvl2Mapping = supportEndpointUtils.getStrukturkodeTilYrkgrLvl2Mapping();
        yrkgrLvl2TilYrkgrLvl1Mapping = supportEndpointUtils.getYrkgrLvl2TilYrkgrLvl1Mapping();

        lesArbeidsledighetCSV(inputStream);
    }

    public void lesOgSkrivLedigeStillinger(InputStream inputStream) {
        strukturkodeTilYrkgrLvl2Mapping = supportEndpointUtils.getStrukturkodeTilYrkgrLvl2Mapping();
        yrkgrLvl2TilYrkgrLvl1Mapping = supportEndpointUtils.getYrkgrLvl2TilYrkgrLvl1Mapping();

        lesLedigeStillingerCSV(inputStream);
    }

    private void lesLedigeStillingerCSV(InputStream inputStreamLedigestillinger) {
        slettSolrIndex(ledigeStillingerCore);

        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "LEDIGE_STILLINGER", "YRKGR_LVL_1_ID", "YRKGR_LVL_2_ID"};
        skrivCSVTilSolrClient(ledigeStillingerCore, inputStreamLedigestillinger, header);
    }

    private void lesArbeidsledighetCSV(InputStream inputStreamArbeidsledige) {
        slettSolrIndex(arbeidsledighetCore);

        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "ARBEIDSLEDIGE", "YRKGR_LVL_1_ID", "YRKGR_LVL_2_ID"};
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
            int teller = 0;
            while ((line = bufferedReader.readLine()) != null) {
                teller++;
                String formatertLine = line.replace("\"", "");
                String[] csvFelter = formatertLine.split(cvsSplitBy);
                int antallArbeidsledige = Integer.parseInt(csvFelter[4]);

                documents.addAll(createDocuments(header, csvFelter, antallArbeidsledige));

                if(teller % 1000 == 0) {
                    oppdaterSolrIndex(solrClient, documents);
                    documents = new ArrayList<>();
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

    private Collection<SolrInputDocument> createDocuments(String[] header, String[] csvFelter, int antallArbeidsledige) {
        Collection<SolrInputDocument> documents = new ArrayList<>();

        for (int i = 0; i < antallArbeidsledige; i++) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField(header[0], csvFelter[0]);
            document.addField(header[1], csvFelter[1]);
            document.addField(header[2], csvFelter[2]);
            document.addField(header[3], csvFelter[3]);
            document.addField(header[4], csvFelter[4]);

            List<String> yrkgrLvl2ider = strukturkodeTilYrkgrLvl2Mapping.get(csvFelter[3]);

            List<String> yrkgrLvl1ider = new ArrayList<>();
            if (yrkgrLvl2ider != null) {
                yrkgrLvl2ider.forEach(yrkgrlvl2id -> yrkgrLvl1ider.addAll(yrkgrLvl2TilYrkgrLvl1Mapping.get(yrkgrlvl2id)));
            }

            if (yrkgrLvl1ider.size() == 0) {
                document.addField(header[5], "-2");
            } else {
                document.addField(header[5], yrkgrLvl1ider);
            }

            if (yrkgrLvl2ider == null || yrkgrLvl2ider.size() == 0) {
                document.addField(header[6], "-2");
            } else {
                document.addField(header[6], yrkgrLvl2ider);
            }

            documents.add(document);
        }
        return documents;
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
