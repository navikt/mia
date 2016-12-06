package no.nav.fo.mia.domain.stillinger;

public class KommuneStilling {
    private String kommunenummer;
    private int antallLedige;
    private int antallStillinger;

    public KommuneStilling() {
    }

    public KommuneStilling(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public KommuneStilling(String kommunenummer, int antallLedige, int antallStillinger) {
        this.kommunenummer = kommunenummer;
        this.antallLedige = antallLedige;
        this.antallStillinger = antallStillinger;
    }

    public String getKommunenummer() {
        return kommunenummer;
    }

    public void setKommunenummer(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public KommuneStilling withKommunenummer(String kommunenr){
        setKommunenummer(kommunenr);
        return this;
    }

    public int getAntallLedige() {
        return antallLedige;
    }

    public void setAntallLedige(int antallLedige) {
        this.antallLedige = antallLedige;
    }

    public KommuneStilling withAntallLedige(int antallLedige) {
        setAntallLedige(antallLedige);
        return this;
    }

    public int getAntallStillinger() {
        return antallStillinger;
    }

    public void setAntallStillinger(int antallStillinger) {
        this.antallStillinger = antallStillinger;
    }

    public KommuneStilling withAntallStillinger(int antallStillinger) {
        setAntallStillinger(antallStillinger);
        return this;
    }
}
