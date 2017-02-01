package no.nav.fo.mia.domain.stillinger;

public class OmradeStilling {
    private String id;
    private Integer antallLedige;
    private Integer antallStillinger;

    public OmradeStilling() {
    }

    public OmradeStilling(String id) {
        this.id = id;
    }

    public OmradeStilling(String id, Integer antallLedige, Integer antallStillinger) {
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

    public Integer getAntallLedige() {
        return antallLedige;
    }

    public void setAntallLedige(Integer antallLedige) {
        this.antallLedige = antallLedige;
    }

    public OmradeStilling withAntallLedige(Integer antallLedige) {
        setAntallLedige(antallLedige);
        return this;
    }

    public Integer getAntallStillinger() {
        return antallStillinger;
    }

    public void setAntallStillinger(Integer antallStillinger) {
        this.antallStillinger = antallStillinger;
    }

    public OmradeStilling withAntallStillinger(int antallStillinger) {
        setAntallStillinger(antallStillinger);
        return this;
    }
}
