package no.nav.fo.mia.domain;

public class Kommune {
    private String navn;

    public Kommune() {

    }

    public Kommune(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Kommune withNavn(String navn) {
        this.setNavn(navn);
        return this;
    }
}
