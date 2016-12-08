package no.nav.fo.mia.domain.stillinger;

public class KommuneStilling {
    private String kommuneid;
    private int antallLedige;
    private int antallStillinger;

    public KommuneStilling() {
    }

    public KommuneStilling(String kommuneid) {
        this.kommuneid = kommuneid;
    }

    public KommuneStilling(String kommuneid, int antallLedige, int antallStillinger) {
        this.kommuneid = kommuneid;
        this.antallLedige = antallLedige;
        this.antallStillinger = antallStillinger;
    }

    public String getKommuneid() {
        return kommuneid;
    }

    public void setKommuneid(String kommuneid) {
        this.kommuneid = kommuneid;
    }

    public KommuneStilling withKommuneid(String kommuneid){
        setKommuneid(kommuneid);
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
