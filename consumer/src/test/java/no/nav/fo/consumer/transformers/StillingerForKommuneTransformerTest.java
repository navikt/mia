package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import no.nav.fo.mia.domain.kodeverk.KommuneKodeverk;
import no.nav.fo.mia.domain.stillinger.KommuneStilling;
import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StillingerForKommuneTransformerTest {

    @Test
    public void skalLeggePaaAntallLedigeStillingerForKommuner() throws Exception {
        List<FacetField.Count> ledigeStillinger = Arrays.asList(
                new FacetField.Count(null, "KOMMUNE_ID_1", 10)
        );

        List<FylkeKodeverk> fylkerFraKodeverk = Arrays.asList(
                new FylkeKodeverk("FYLKE", "1")
                    .withKommune(new KommuneKodeverk("KOMMUNE1", "0101", "KOMMUNE_ID_1"))
        );

        assertThat(StillingerForKommuneTransformer.getStillingerForKommuner(ledigeStillinger, null, fylkerFraKodeverk).get(0).getAntallStillinger()).isEqualTo(10);
    }

    @Test
    public void skalSetteAntallStillingerTil0OmIngenTreff() {
        List<FacetField.Count> ledigeStillinger = Arrays.asList(
                new FacetField.Count(null, "KOMMUNE_ID_1", 10),
                new FacetField.Count(null, "KOMMUNE_ID_2", 22)
        );

        List<FylkeKodeverk> fylkerFraKodeverk = Arrays.asList(
                new FylkeKodeverk("FYLKE", "1")
                        .withKommune(new KommuneKodeverk("KOMMUNE1", "1", "KOMMUNE_ID_1"))
                        .withKommune(new KommuneKodeverk("KOMMUNE2", "2", "KOMMUNE_ID_2"))
                        .withKommune(new KommuneKodeverk("KOMMUNE5", "5", "KOMMUNE_ID_5"))
        );

        KommuneStilling kommuneStilling = getStillingerForKommunenummer("5", StillingerForKommuneTransformer.getStillingerForKommuner(ledigeStillinger, null, fylkerFraKodeverk));
        assertThat(kommuneStilling.getAntallStillinger()).isEqualTo(0);
    }

    private KommuneStilling getStillingerForKommunenummer(String kommunenummer, List<KommuneStilling> stillinger) {
        Optional<KommuneStilling> kommuneStillinger = stillinger.stream()
                .filter(stilling -> stilling.getKommunenummer().equalsIgnoreCase(kommunenummer))
                .findFirst();

        return kommuneStillinger.isPresent() ? kommuneStillinger.get() : null;
    }
}