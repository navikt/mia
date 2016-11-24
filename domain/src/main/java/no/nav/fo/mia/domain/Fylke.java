package no.nav.fo.mia.domain;

import java.util.ArrayList;
import java.util.List;

public class Fylke {
    private List<Kommune> kommuner = new ArrayList<>();
    private String navn;

    public Fylke() {

    }

    public Fylke(String navn) {
        this.navn = navn;
    }

    public Fylke withNavn(String navn) {
        this.setNavn(navn);
        return this;
    }

    public Fylke withKommune(Kommune kommune) {
        this.kommuner.add(kommune);
        return this;
    }

    public Fylke withKommuner(List<Kommune> kommuner) {
        this.kommuner.addAll(kommuner);
        return this;
    }

    public List<Kommune> getKommuner() {
        return kommuner;
    }

    public void setKommuner(List<Kommune> kommuner) {
        this.kommuner = kommuner;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }
}
