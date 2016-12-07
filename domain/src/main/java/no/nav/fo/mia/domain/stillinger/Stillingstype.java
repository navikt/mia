package no.nav.fo.mia.domain.stillinger;

import java.util.List;

public class Stillingstype {
    private String navn;
    private String id;
    private String strukturkode;
    private List<String> parent;

    public Stillingstype() {
    }

    public Stillingstype(String navn, String id, String strukturkode, List<String> parent) {
        this.navn = navn;
        this.id = id;
        this.strukturkode = strukturkode;
        this.parent = parent;
    }

    public List<String> getParent() {
        return parent;
    }

    public void setParent(List<String> parent) {
        this.parent = parent;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrukturkode() {
        return strukturkode;
    }

    public void setStrukturkode(String strukturkode) {
        this.strukturkode = strukturkode;
    }
}
