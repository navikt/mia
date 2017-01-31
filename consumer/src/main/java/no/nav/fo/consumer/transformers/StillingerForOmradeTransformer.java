package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StillingerForOmradeTransformer {
    static List<OmradeStilling> getStillingerForKommuner(List<FacetField.Count> ledigeStillingerKommune, List<FacetField.Count> ledigeStillingerFylke) {
        List<FacetField.Count> omrader = new ArrayList<>();
        omrader.addAll(ledigeStillingerFylke);
        omrader.addAll(ledigeStillingerKommune);

        return omrader.stream()
                .map(omrade -> new OmradeStilling(omrade.getName(), "-", (int)omrade.getCount()))
                .collect(Collectors.toList());
    }

    public static OmradeStilling getOmradeStillingForKommuner(String navn, List<FacetField.Count> antallStillingerPerStillingsannonseForKommune, String antallArbeidsledigeForKommune) {
       int antallStillinger = 0;
        for (FacetField.Count count : antallStillingerPerStillingsannonseForKommune) {
            if (count.getName() == null) {
                antallStillinger += count.getCount();
            } else {
                antallStillinger += Integer.parseInt(count.getName()) * count.getCount();
            }
        }

        return new OmradeStilling(navn, antallArbeidsledigeForKommune, antallStillinger);
    }
}
