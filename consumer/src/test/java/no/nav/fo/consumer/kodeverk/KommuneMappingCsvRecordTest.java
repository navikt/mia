package no.nav.fo.consumer.kodeverk;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KommuneMappingCsvRecordTest {
    @Test
    public void shouldReturnTrueForValidKommune() throws Exception {
        assertThat(KommuneMappingCsvRecord.isValidCsvLine("62914;\"NO18.1818\";\"Herøy i Nordland\"")).isTrue();
    }

    @Test
    public void shouldReturnFalseForKodeWithoutKommunenummer() throws Exception {
        assertThat(KommuneMappingCsvRecord.isValidCsvLine("62914;\"NO18\";\"Herøy i Nordland\"")).isFalse();
    }

    @Test
    public void shouldReturnFalseForKommunenummerWith5Digits() throws Exception {
        assertThat(KommuneMappingCsvRecord.isValidCsvLine("62914;\"NO18.18188\";\"Herøy i Nordland\"")).isFalse();
    }

    @Test
    public void shouldReturnFalseForEmptyLine() throws Exception {
        assertThat(KommuneMappingCsvRecord.isValidCsvLine("")).isFalse();
    }

    @Test
    public void getKommunenummerShouldReturnKommuneNummer() throws Exception {
        KommuneMappingCsvRecord kommuneMappingCsvRecord = new KommuneMappingCsvRecord("62914;\"NO18.1818\";\"Herøy i Nordland\"");
        assertThat(kommuneMappingCsvRecord.getKommunenummer()).isEqualToIgnoringCase("1818");
    }
}