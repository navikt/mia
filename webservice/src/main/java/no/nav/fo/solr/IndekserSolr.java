package no.nav.fo.solr;

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
import java.util.ArrayList;
import java.util.Collection;

public class IndekserSolr {

    @Inject
    DokumentOppretter dokumentOppretter;

    private Logger logger = LoggerFactory.getLogger(IndekserSolr.class);
    private SolrClient arbeidsledighetCore, ledigeStillingerCore;

    public IndekserSolr() {
        String uri = System.getProperty("miasolr.solr.url") + "/internal/masternode";
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String result = br.readLine();

            String arbeidsledigecoreUri = result + "/arbeidsledigecore";
            String ledigestillingercoreUri = result + "/ledigestillingercore";

            arbeidsledighetCore = new HttpSolrClient.Builder().withBaseSolrUrl(arbeidsledigecoreUri).build();
            ledigeStillingerCore = new HttpSolrClient.Builder().withBaseSolrUrl(ledigestillingercoreUri).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lesLedigeStillingerCSV(InputStream inputStreamLedigestillinger) {
        slettSolrIndex(ledigeStillingerCore);

        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "LEDIGE_STILLINGER", "YRKGR_LVL_1_ID", "YRKGR_LVL_2_ID"};
        skrivCSVTilSolrClient(ledigeStillingerCore, inputStreamLedigestillinger, header);
    }

    public void lesArbeidsledighetCSV(InputStream inputStreamArbeidsledige) {
        slettSolrIndex(arbeidsledighetCore);

        String[] header = new String[]{"PERIODE", "FYLKESNR", "KOMMUNENR", "YRKESKODE", "ARBEIDSLEDIGE", "YRKGR_LVL_1_ID", "YRKGR_LVL_2_ID"};
        skrivCSVTilSolrClient(arbeidsledighetCore, inputStreamArbeidsledige, header);
    }

    private void skrivCSVTilSolrClient(SolrClient solrClient, InputStream inputStreamLedigestillinger, String[] header) {
        String line = "";
        String cvsSplitBy = ",";

        Collection<SolrInputDocument> documents = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStreamLedigestillinger))) {
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String formatertLine = line.replace("\"", "");
                String[] csvFelter = formatertLine.split(cvsSplitBy);
                int antallArbeidsledige = Integer.parseInt(csvFelter[4]);

                documents.addAll(dokumentOppretter.createDocuments(header, csvFelter, antallArbeidsledige));

                if (documents.size() > 10000) {
                    oppdaterSolrIndex(solrClient, documents);
                    documents = new ArrayList<>();
                }
            }
            oppdaterSolrIndex(solrClient, documents);
        } catch (IOException e) {
            logger.error("Feil ved lesing/skriving av fil " + e.getMessage());
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
