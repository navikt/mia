package no.nav.fo.mia.domain.stillinger;

import java.util.ArrayList;
import java.util.List;

public class Stilling {
    private String arbeidsgivernavn;
    private String id;
    private String tittel;
    private List<String> yrkesgrupper = new ArrayList<>();
    private List<String> yrkesomrader = new ArrayList<>();
    private String soknadfrist;
    private String stillingstype;

    public Stilling() {
    }

    public Stilling(String arbeidsgivernavn, String id) {
        this.arbeidsgivernavn = arbeidsgivernavn;
        this.id = id;
    }

    public String getArbeidsgivernavn() {
        return arbeidsgivernavn;
    }

    public void setArbeidsgivernavn(String arbeidsgivernavn) {
        this.arbeidsgivernavn = arbeidsgivernavn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTittel() {
        return tittel;
    }

    public void setTittel(String tittel) {
        this.tittel = tittel;
    }

    public List<String> getYrkesgrupper() {
        return yrkesgrupper;
    }

    public void setYrkesgrupper(List<String> yrkesgrupper) {
        this.yrkesgrupper = yrkesgrupper;
    }

    public List<String> getYrkesomrader() {
        return yrkesomrader;
    }

    public void setYrkesomrader(List<String> yrkesomrader) {
        this.yrkesomrader = yrkesomrader;
    }

    public String getSoknadfrist() {
        return soknadfrist;
    }

    public void setSoknadfrist(String soknadfrist) {
        this.soknadfrist = soknadfrist;
    }

    public Stilling withTittel(String tittel) {
        this.setTittel(tittel);
        return this;
    }

    public Stilling withSoknadfrist(String soknadfrist) {
        this.setSoknadfrist(soknadfrist);
        return this;
    }

    public Stilling withYrkesgrupper(List<String> yrkesgrupper) {
        this.yrkesgrupper.addAll(yrkesgrupper);
        return this;
    }

    public Stilling withYrkesgruppe(String yrkesgruppe) {
        this.yrkesgrupper.add(yrkesgruppe);
        return this;
    }

    public Stilling withYrkesomrader(List<String> yrkesomrader) {
        this.yrkesomrader.addAll(yrkesomrader);
        return this;
    }

    public Stilling withYrkesomrade(String yrkesomrade) {
        this.yrkesomrader.add(yrkesomrade);
        return this;
    }

    public String getStillingstype() {
        return stillingstype;
    }

    public void setStillingstype(String stillingstype) {
        this.stillingstype = stillingstype;
    }

    public Stilling withStillingstype(String stillingstype) {
        this.setStillingstype(stillingstype);
        return this;
    }
}
