package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.Stillingstype;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.util.List;
import java.util.stream.Collectors;

public class StillingstypeForYrkesomradeTransformer {
    public static List<Stillingstype> getStillingstyperForYrkesgrupper(SolrDocumentList stillingstyper) {
        return stillingstyper.stream()
                .map(StillingstypeForYrkesomradeTransformer::getStillingstype)
                .collect(Collectors.toList());
    }

    private static Stillingstype getStillingstype(SolrDocument stilling) {
        List<String> parents = stilling.getFieldValues("PARENT").stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new Stillingstype(getValue(stilling, "NAVN"), getValue(stilling, "ID"), getStrukturkode(stilling), parents);
    }

    private static String getStrukturkode(SolrDocument stilling) {
        Object strukturkode = stilling.getFieldValue("STRUKTURKODE");
        return strukturkode == null ? null : strukturkode.toString();
    }

    private static String getValue(SolrDocument document, String fieldname) {
        Object fieldvalue = document.getFieldValue(fieldname);
        return fieldvalue != null ? fieldvalue.toString() : null;
    }
}
