package no.nav.fo.mia.domain.stillinger;

public class KommuneStillinger {
    private String kommunenummer;
    private int antallLedige;
    private int antallStillinger;

    public KommuneStillinger() {
    }

    public KommuneStillinger(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public KommuneStillinger(String kommunenummer, int antallLedige, int antallStillinger) {
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

    public KommuneStillinger withKommunenummer(String kommunenr){
        setKommunenummer(kommunenr);
        return this;
    }

    public int getAntallLedige() {
        return antallLedige;
    }

    public void setAntallLedige(int antallLedige) {
        this.antallLedige = antallLedige;
    }

    public KommuneStillinger withAntallLedige(int antallLedige) {
        setAntallLedige(antallLedige);
        return this;
    }

    public int getAntallStillinger() {
        return antallStillinger;
    }

    public void setAntallStillinger(int antallStillinger) {
        this.antallStillinger = antallStillinger;
    }

    public KommuneStillinger withAntallStillinger(int antallStillinger) {
        setAntallStillinger(antallStillinger);
        return this;
    }
}
