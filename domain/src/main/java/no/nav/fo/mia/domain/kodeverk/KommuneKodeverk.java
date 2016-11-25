package no.nav.fo.mia.domain.kodeverk;

public class KommuneKodeverk {
    private String navn;
    private String kommunenummer;

    public KommuneKodeverk() {

    }

    public KommuneKodeverk(String navn, String kommunenummer) {
        this.navn = navn;
        this.kommunenummer = kommunenummer;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public KommuneKodeverk withNavn(String navn) {
        this.setNavn(navn);
        return this;
    }

    public String getKommunenummer() {
        return kommunenummer;
    }

    public void setKommunenummer(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public KommuneKodeverk withKommunenr(String kommunenr) {
        this.setKommunenummer(kommunenr);
        return this;
    }
}
