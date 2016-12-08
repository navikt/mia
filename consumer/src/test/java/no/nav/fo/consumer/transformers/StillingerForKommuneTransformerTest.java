package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.geografi.Omrade;
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

        List<Omrade> fylkerFraKodeverk = Arrays.asList(
                new Omrade("2", "1", "FYLKE", "NO01")
                    .withUnderomrade(new Omrade("3", "KOMMUNE_ID_1", "KOMMUNE_NAVN", "NO01.1985"))
        );

        assertThat(StillingerForKommuneTransformer.getStillingerForKommuner(ledigeStillinger, null, fylkerFraKodeverk).get(0).getAntallStillinger()).isEqualTo(10);
    }

    @Test
    public void skalSetteAntallStillingerTil0OmIngenTreff() {
        List<FacetField.Count> ledigeStillinger = Arrays.asList(
                new FacetField.Count(null, "KOMMUNE_ID_1", 10),
                new FacetField.Count(null, "KOMMUNE_ID_2", 22)
        );

        List<Omrade> fylkerFraKodeverk = Arrays.asList(
                new Omrade("2", "1", "FYLKE", "NO01")
                        .withUnderomrade(new Omrade("3", "KOMMUNE_ID_1", "KOMMUNE_NAVN1", "NO01.1985"))
                        .withUnderomrade(new Omrade("3", "KOMMUNE_ID_2", "KOMMUNE_NAVN2", "NO01.1986"))
                        .withUnderomrade(new Omrade("3", "KOMMUNE_ID_5", "KOMMUNE_NAVN3", "NO01.1987"))
        );

        KommuneStilling kommuneStilling = getStillingerForKommuneid("KOMMUNE_ID_5", StillingerForKommuneTransformer.getStillingerForKommuner(ledigeStillinger, null, fylkerFraKodeverk));
        assertThat(kommuneStilling.getAntallStillinger()).isEqualTo(0);
    }

    private KommuneStilling getStillingerForKommuneid(String kommuneid, List<KommuneStilling> stillinger) {
        Optional<KommuneStilling> kommuneStillinger = stillinger.stream()
                .filter(stilling -> stilling.getKommuneid().equalsIgnoreCase(kommuneid))
                .findFirst();

        return kommuneStillinger.isPresent() ? kommuneStillinger.get() : null;
    }
}