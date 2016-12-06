package no.nav.fo.mia.domain.stillinger;

public class BransjeLvl1 {
    private String bransjenavn;
    private String bransjeid;
    private int antallStillinger;

    public BransjeLvl1() {
    }

    public BransjeLvl1(String bransjenavn, String bransjeid, int antallStillinger) {
        this.bransjenavn = bransjenavn;
        this.bransjeid = bransjeid;
        this.antallStillinger = antallStillinger;
    }

    public String getBransjenavn() {
        return bransjenavn;
    }

    public void setBransjenavn(String bransjenavn) {
        this.bransjenavn = bransjenavn;
    }

    public String getBransjeid() {
        return bransjeid;
    }

    public void setBransjeid(String bransjeid) {
        this.bransjeid = bransjeid;
    }

    public int getAntallStillinger() {
        return antallStillinger;
    }

    public void setAntallStillinger(int antallStillinger) {
        this.antallStillinger = antallStillinger;
    }
}
