package no.nav.fo.consumer.transformers;

import no.nav.fo.mia.domain.stillinger.OmradeStilling;
import org.apache.solr.client.solrj.response.FacetField;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class StillingerForOmradeTransformerTest {

    @Test
    public void skalLeggePaaAntallLedigeStillingerForKommuner() throws Exception {
        List<FacetField.Count> ledigeStillinger = Arrays.asList(
                new FacetField.Count(null, "KOMMUNE_ID_1", 10)
        );

        assertThat(StillingerForOmradeTransformer.getStillingerForKommuner(ledigeStillinger, ledigeStillinger).get(0).getAntallStillinger()).isEqualTo(10);
    }

    @Test
    public void skalLeggePaaAntallLedigeStillingerForFylker() {
        List<FacetField.Count> ledigeStillingerKommuner = Arrays.asList(
                new FacetField.Count(null, "KOMMUNE_ID_1", 10),
                new FacetField.Count(null, "KOMMUNE_ID_2", 22)
        );

        List<FacetField.Count> ledigeStillingerFylker = Arrays.asList(
                new FacetField.Count(null, "FYLKE_ID_1", 33),
                new FacetField.Count(null, "FYLKE_ID_2", 44)
        );

        OmradeStilling kommuneStilling = getStillingerForKommuneid("FYLKE_ID_2", StillingerForOmradeTransformer.getStillingerForKommuner(ledigeStillingerKommuner, ledigeStillingerFylker));
        assertThat(kommuneStilling.getAntallStillinger()).isEqualTo(44);
    }

    private OmradeStilling getStillingerForKommuneid(String kommuneid, List<OmradeStilling> stillinger) {
        Optional<OmradeStilling> kommuneStillinger = stillinger.stream()
                .filter(stilling -> stilling.getId().equalsIgnoreCase(kommuneid))
                .findFirst();

        return kommuneStillinger.isPresent() ? kommuneStillinger.get() : null;
    }
}