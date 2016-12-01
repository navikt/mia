package no.nav.fo.consumer.kodeverk;

import no.nav.fo.mia.domain.kodeverk.FylkeKodeverk;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FylkerOgKommunerReaderTest {
    List<FylkeKodeverk> fylker = FylkerOgKommunerReader.getFylkerOgKommuner();

    @Test
    public void skalHenteAlleFylker() {
        assertThat(fylker.size()).isEqualTo(4);
    }

    @Test
    public void etFylkeSkalInneholdeAlleKommuner() {
        FylkeKodeverk nordland = fylker.stream().filter(fylke -> fylke.getNavn().equalsIgnoreCase("nordland")).findAny().get();
        assertThat(nordland.getKommuner().size()).isEqualTo(3);
    }

    @Test
    public void kommuneSkalHaKommunenummer() {
        FylkeKodeverk sorTrondelag = fylker.stream().filter(fylke -> fylke.getNavn().equalsIgnoreCase("Sør-Trøndelag")).findAny().get();
        assertThat(sorTrondelag.getKommuner().get(0).getKommunenummer()).isEqualTo("1640");
    }
}