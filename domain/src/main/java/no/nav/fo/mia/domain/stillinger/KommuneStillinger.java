package no.nav.fo.mia.domain.stillinger;

public class KommuneStillinger {
    private String kommunenr;
    private int antallLedige;
    private int antallStillinger;

    public KommuneStillinger() {
    }

    public KommuneStillinger(String kommunenr) {
        this.kommunenr = kommunenr;
    }

    public KommuneStillinger(String kommunenr, int antallLedige, int antallStillinger) {
        this.kommunenr = kommunenr;
        this.antallLedige = antallLedige;
        this.antallStillinger = antallStillinger;
    }

    public String getKommunenr() {
        return kommunenr;
    }

    public void setKommunenr(String kommunenr) {
        this.kommunenr = kommunenr;
    }

    public KommuneStillinger withKommunenummer(String kommunenr){
        setKommunenr(kommunenr);
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
