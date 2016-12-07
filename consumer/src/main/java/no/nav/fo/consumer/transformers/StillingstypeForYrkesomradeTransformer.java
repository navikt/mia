package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.Stillingstype;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;
import java.util.stream.Collectors;

public class StillingstypeForYrkesomradeTransformer {
    public static List<Stillingstype> getStillingstyperForYrkesomrade(SolrDocumentList stillingstyper) {
        return stillingstyper.stream()
                .map(StillingstypeForYrkesomradeTransformer::getStillingstype)
                .collect(Collectors.toList());
    }

    private static Stillingstype getStillingstype(SolrDocument stilling) {
        return new Stillingstype(stilling.getFieldValue("NAVN").toString(), stilling.getFieldValue("ID").toString(),
                getStrukturkode(stilling), stilling.getFieldValues("PARENT").stream().map(Object::toString).collect(Collectors.toList()));
    }

    private static String getStrukturkode(SolrDocument stilling) {
        Object strukturkode = stilling.getFieldValue("STRUKTURKODE");
        return strukturkode == null ? null : strukturkode.toString();
    }
}
