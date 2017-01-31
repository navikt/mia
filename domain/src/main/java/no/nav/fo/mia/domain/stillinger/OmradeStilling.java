package no.nav.fo.mia.domain.stillinger;

public class OmradeStilling {
    private String id;
    private String antallLedige;
    private int antallStillinger;

    public OmradeStilling() {
    }

    public OmradeStilling(String id) {
        this.id = id;
    }

    public OmradeStilling(String id, String antallLedige, int antallStillinger) {
        this.id = id;
        this.antallLedige = antallLedige;
        this.antallStillinger = antallStillinger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OmradeStilling withId(String id){
        setId(id);
        return this;
    }

    public String getAntallLedige() {
        return antallLedige;
    }

    public void setAntallLedige(String antallLedige) {
        this.antallLedige = antallLedige;
    }

    public OmradeStilling withAntallLedige(String antallLedige) {
        setAntallLedige(antallLedige);
        return this;
    }

    public int getAntallStillinger() {
        return antallStillinger;
    }

    public void setAntallStillinger(int antallStillinger) {
        this.antallStillinger = antallStillinger;
    }

    public OmradeStilling withAntallStillinger(int antallStillinger) {
        setAntallStillinger(antallStillinger);
        return this;
    }
}
