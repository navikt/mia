package no.nav.fo.mia.domain.geografi;

import java.util.ArrayList;
import java.util.List;

public class Omrade {
    private String nivaa;
    private String id;
    private String navn;
    private String strukturkode;
    private List<Omrade> underomrader = new ArrayList<>();
    private List<String> parentIds = new ArrayList<>();

    public Omrade() {
    }

    public Omrade(String nivaa, String id, String navn, String strukturkode) {
        this.nivaa = nivaa;
        this.id = id;
        this.navn = navn;
        this.strukturkode = strukturkode;
    }

    public String getNivaa() {
        return nivaa;
    }

    public void setNivaa(String nivaa) {
        this.nivaa = nivaa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getStrukturkode() {
        return strukturkode;
    }

    public void setStrukturkode(String strukturkode) {
        this.strukturkode = strukturkode;
    }

    public List<Omrade> getUnderomrader() {
        return underomrader;
    }

    public Omrade withUnderomrade(Omrade underomrade) {
        this.underomrader.add(underomrade);
        return this;
    }

    public Omrade withUnderomrader(List<Omrade> underomrader) {
        this.underomrader.addAll(underomrader);
        return this;
    }

    public List<String> getParentIds() {
        return parentIds;
    }

    public Omrade withParent(String parentId) {
        this.parentIds.add(parentId);
        return this;
    }

    public Omrade withParents(List<String> parents) {
        this.parentIds.addAll(parents);
        return this;
    }

    public boolean hasParent(String parentId) {
        return this.parentIds.contains(parentId);
    }
}
