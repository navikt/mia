package no.nav.fo.mia.transformers;

import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import no.nav.fo.mia.domain.kodeverk.KommuneKodeverk;
import no.nav.fo.mia.domain.stillinger.KommuneStillinger;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StillingerTransformer {
    public static List<KommuneStillinger> getStillingerForKommuner(List<FacetField.Count> ledigeStillinger, List<FacetField.Count> arbeidsledige, List<FylkeKodeverk> fylkerFraKodeverk) {
        return fylkerFraKodeverk.stream()
                .flatMap(fylke -> fylke.getKommuner().stream())
                .map(kommune -> getStillingerForKommune(kommune, ledigeStillinger, arbeidsledige))
                .collect(Collectors.toList());
    }

    private static KommuneStillinger getStillingerForKommune(KommuneKodeverk kommune, List<FacetField.Count> ledigeStillinger, List<FacetField.Count> arbeidsledige) {
        return new KommuneStillinger(kommune.getKommunenummer(), getArbeidsledigeForKommune(kommune, arbeidsledige), getLedigeStillingerForKommune(kommune, ledigeStillinger));
    }

    private static int getLedigeStillingerForKommune(KommuneKodeverk kommune, List<FacetField.Count> ledigeStillinger) {
        Optional<Integer> antallLedigeStillinger = ledigeStillinger.stream()
                .filter(ledigStilling -> ledigStilling.getName().equalsIgnoreCase(kommune.getStillingsSolrId()))
                .map(stilling -> ((int) stilling.getCount()))
                .findFirst();

        return antallLedigeStillinger.isPresent() ? antallLedigeStillinger.get() : 0;
    }

    private static int getArbeidsledigeForKommune(KommuneKodeverk kommune, List<FacetField.Count> arbeidsledige) {
        return 0;
    }
}
