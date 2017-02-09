package no.nav.fo.solr;

import no.nav.fo.consumer.service.SupportMappingService;
import org.apache.solr.common.SolrInputDocument;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DokumentOppretter {
    private static final int PERIODE = 0, FYLKESNR = 1, KOMMUNENR = 2, YRKESKODE = 3, ANTALL = 4, YRKLVL1ID = 5, YRKLVL2ID = 6;

    @Inject
    SupportMappingService supportMappingService;

    public Collection<SolrInputDocument> createDocuments(String[] header, String[] csvFelter, int antallArbeidsledige) {
        Map<String, List<String>> strukturkodeTilYrkgrLvl2Mapping = supportMappingService.getStrukturkodeTilYrkgrLvl2Mapping();
        Map<String, List<String>> yrkgrLvl2TilYrkgrLvl1Mapping = supportMappingService.getYrkgrLvl2TilYrkgrLvl1Mapping();
        Collection<SolrInputDocument> documents = new ArrayList<>();

        for (int i = 0; i < antallArbeidsledige; i++) {
            SolrInputDocument document = lagSolrDocumentMedCSVData(header, csvFelter);
            List<String> yrkgrLvl2ider = strukturkodeTilYrkgrLvl2Mapping.get(csvFelter[3]);

            List<String> yrkgrLvl1ider = new ArrayList<>();
            if (yrkgrLvl2ider != null) {
                yrkgrLvl2ider.forEach(yrkgrlvl2id -> yrkgrLvl1ider.addAll(yrkgrLvl2TilYrkgrLvl1Mapping.get(yrkgrlvl2id)));
            }

            document.addField(header[YRKLVL1ID], harYrkgrIder(yrkgrLvl1ider) ? yrkgrLvl1ider : "-2");
            document.addField(header[YRKLVL2ID], harYrkgrIder(yrkgrLvl2ider) ? yrkgrLvl2ider : "-2");

            documents.add(document);
        }
        return documents;
    }

    private boolean harYrkgrIder(List<String> yrkgrIder) {
        return yrkgrIder != null && !yrkgrIder.isEmpty();
    }

    private SolrInputDocument lagSolrDocumentMedCSVData(String[] header, String[] csvFelter) {
        SolrInputDocument document = new SolrInputDocument();
        document.addField(header[PERIODE], csvFelter[PERIODE]);
        document.addField(header[FYLKESNR], csvFelter[FYLKESNR]);
        document.addField(header[KOMMUNENR], csvFelter[KOMMUNENR]);
        document.addField(header[YRKESKODE], csvFelter[YRKESKODE]);
        document.addField(header[ANTALL], csvFelter[ANTALL]);
        return document;
    }
}
