package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.geografi.Omrade;
import no.nav.fo.mia.domain.stillinger.KommuneStilling;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StillingerForKommuneTransformer {
    public static List<KommuneStilling> getStillingerForKommuner(List<FacetField.Count> ledigeStillinger, List<FacetField.Count> arbeidsledige, List<Omrade> fylkerMedKommuner) {
        return fylkerMedKommuner.stream()
                .flatMap(fylke -> fylke.getUnderomrader().stream())
                .map(kommune -> getStillingerForKommune(kommune, ledigeStillinger, arbeidsledige))
                .collect(Collectors.toList());
    }

    private static KommuneStilling getStillingerForKommune(Omrade kommune, List<FacetField.Count> ledigeStillinger, List<FacetField.Count> arbeidsledige) {
        return new KommuneStilling(kommune.getId(), getArbeidsledigeForKommune(kommune, arbeidsledige), getLedigeStillingerForKommune(kommune, ledigeStillinger));
    }

    private static int getLedigeStillingerForKommune(Omrade kommune, List<FacetField.Count> ledigeStillinger) {
        Optional<Integer> antallLedigeStillinger = ledigeStillinger.stream()
                .filter(ledigStilling -> ledigStilling.getName().equalsIgnoreCase(kommune.getId()))
                .map(stilling -> ((int) stilling.getCount()))
                .findFirst();

        return antallLedigeStillinger.isPresent() ? antallLedigeStillinger.get() : 0;
    }

    private static int getArbeidsledigeForKommune(Omrade kommune, List<FacetField.Count> arbeidsledige) {
        return 0;
    }
}
