package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.Stilling;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StillingTransformer {
    public static List<Stilling> getStillinger(SolrDocumentList stillinger) {
        return stillinger.stream()
                .map(StillingTransformer::getStilling)
                .collect(Collectors.toList());
    }

    private static Stilling getStilling(SolrDocument stilling) {
        List<String> yrkesomrader = stilling.getFieldValues("YRKGR_LVL_1_ID").stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        List<String> yrkesgrupper = stilling.getFieldValues("YRKGR_LVL_2_ID").stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        return new Stilling(getValue(stilling, "ARBEIDSGIVERNAVN"), getValue(stilling, "ID"))
                .withTittel(getValue(stilling, "TITTEL"))
                .withStillingstype(getValue(stilling, "STILLINGSTYPE_5"))
                .withSoknadfrist(getDateString((Date)stilling.getFieldValue("SOKNADSFRIST")))
                .withYrkesgrupper(yrkesgrupper)
                .withYrkesomrader(yrkesomrader)
                .withAntallStillinger(getAntallStillinger(stilling));
    }

    private static String getValue(SolrDocument document, String fieldname) {
        Object fieldvalue = document.getFieldValue(fieldname);
        return fieldvalue != null ? fieldvalue.toString() : null;
    }

    private static int getAntallStillinger(SolrDocument document) {
        Integer antallstillinger = (Integer) document.getFieldValue("ANTALLSTILLINGER");
        return antallstillinger != null ? antallstillinger : 1;
    }

    private static String getDateString(Date date) {
        if(date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
