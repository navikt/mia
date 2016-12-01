package no.nav.fo.mia.domain.kodeverk;

import java.util.ArrayList;
import java.util.List;

public class FylkeKodeverk {
    private List<KommuneKodeverk> kommuner = new ArrayList<>();
    private String navn;
    private String fylkesnummer;

    public FylkeKodeverk() {

    }

    public FylkeKodeverk(String navn, String fylkesnummer) {
        this.navn = navn;
        this.fylkesnummer = fylkesnummer;
    }

    public FylkeKodeverk withNavn(String navn) {
        this.setNavn(navn);
        return this;
    }

    public FylkeKodeverk withKommune(KommuneKodeverk kommuneKodeverk) {
        this.kommuner.add(kommuneKodeverk);
        return this;
    }

    public FylkeKodeverk withKommuner(List<KommuneKodeverk> kommuner) {
        this.kommuner.addAll(kommuner);
        return this;
    }

    public List<KommuneKodeverk> getKommuner() {
        return kommuner;
    }

    public void setKommuner(List<KommuneKodeverk> kommuner) {
        this.kommuner = kommuner;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getFylkesnummer() {
        return fylkesnummer;
    }

    public void setFylkesnummer(String fylkesnummer) {
        this.fylkesnummer = fylkesnummer;
    }

    public FylkeKodeverk withFylkesnummer(String fylkesnummer) {
        setFylkesnummer(fylkesnummer);
        return this;
    }

    public boolean equals(Object other) {
        if(other instanceof FylkeKodeverk) {
            return ((FylkeKodeverk)other).getFylkesnummer().equals(this.getFylkesnummer());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getFylkesnummer().hashCode();
    }
}
