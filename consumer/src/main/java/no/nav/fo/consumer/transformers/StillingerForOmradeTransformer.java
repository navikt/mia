package no.nav.fo.consumer.transformers;

import no.nav.fo.consumer.extractor.AntallStillingerExtractor;
import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StillingerForOmradeTransformer {
    public static List<OmradeStilling> getStillingerForKommuner(List<FacetField.Count> ledigeStillingerKommune, List<FacetField.Count> ledigeStillingerFylke) {
        List<FacetField.Count> omrader = new ArrayList<>();
        omrader.addAll(ledigeStillingerFylke);
        omrader.addAll(ledigeStillingerKommune);

        return omrader.stream()
                .map(omrade -> new OmradeStilling(omrade.getName(), 0, (int)omrade.getCount()))
                .collect(Collectors.toList());
    }

    public static OmradeStilling getOmradeStillingForKommuner(FacetField.Count ledigStillingKommune) {
        return new OmradeStilling(ledigStillingKommune.getName(), 0, (int)ledigStillingKommune.getCount());
    }
}
