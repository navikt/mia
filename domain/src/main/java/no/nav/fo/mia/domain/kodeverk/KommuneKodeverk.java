package no.nav.fo.mia.domain.kodeverk;

import no.nav.fo.mia.domain.stillinger.KommuneStillinger;

public class KommuneKodeverk {
    private String navn;
    private String kommunenr;

    public KommuneKodeverk() {

    }

    public KommuneKodeverk(String navn, String kommunenr) {
        this.navn = navn;
        this.kommunenr = kommunenr;
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

    public String getKommunenr() {
        return kommunenr;
    }

    public void setKommunenr(String kommunenr) {
        this.kommunenr = kommunenr;
    }

    public KommuneKodeverk withKommunenr(String kommunenr) {
        this.setKommunenr(kommunenr);
        return this;
    }
}
