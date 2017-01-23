package no.nav.fo.endpoints;

import no.nav.fo.consumer.endpoints.SupportEndpoint;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;

public class HentDataEndpoint {
    private static Logger logger = LoggerFactory.getLogger(HentDataEndpoint.class);

    @Inject
    SupportEndpoint supportEndpointUtils;

    public static void indekserSOLRIndex() {
        String dataDirNavn = System.getProperty("app.solr.data.dir");

        String arbeidsledighetFilnavn = "arbeidsledighet_solr.csv";
        File arbeidsledighetFil = new File(dataDirNavn, arbeidsledighetFilnavn);

        String ledigestillingerFilnavn = "ledigestillinger_solr.csv";
        File ledigeStillingerFil = new File(dataDirNavn, ledigestillingerFilnavn);

        String arbeidsledigecoreUri = System.getProperty("miasolr.solr.arbeidsledigecore");
        String ledigestillingercoreUri = System.getProperty("miasolr.solr.ledigestillingercore");

        SolrClient arbeidsledighetCoreSolrServer = new HttpSolrClient.Builder().withBaseSolrUrl(arbeidsledigecoreUri).build();
        SolrClient ledigeStillingerCoreSolrServer = new HttpSolrClient.Builder().withBaseSolrUrl(ledigestillingercoreUri).build();

        oppdaterSolrIndex(arbeidsledighetCoreSolrServer, arbeidsledighetFil);
        oppdaterSolrIndex(ledigeStillingerCoreSolrServer, ledigeStillingerFil);
    }

    private static void slettSolrIndex(SolrClient solrServer) throws IOException, SolrServerException {
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    private static void oppdaterSolrIndex(SolrClient solrServer, File fil) {
        try {
            slettSolrIndex(solrServer);
            ContentStreamUpdateRequest updateRequest = new ContentStreamUpdateRequest("/update/csv");
            updateRequest.addFile(fil, "application/csv");
            updateRequest.setAction(AbstractUpdateRequest.ACTION.COMMIT, true, true);
            solrServer.request(updateRequest);
            solrServer.commit();
        } catch(IOException | SolrServerException e) {
            logger.error("Kunne ikke oppdatere " + fil.getName() + " SOLR ", e.getMessage());
        }
    }
}
