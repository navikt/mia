package no.nav.fo.mia.domain.kodeverk;

public class KommuneKodeverk {
    private String navn;

    public KommuneKodeverk() {

    }

    public KommuneKodeverk(String navn) {
        this.navn = navn;
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
}
