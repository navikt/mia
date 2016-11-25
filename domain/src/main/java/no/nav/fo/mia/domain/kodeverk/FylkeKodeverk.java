package no.nav.fo.mia.domain.kodeverk;

import java.util.ArrayList;
import java.util.List;

public class FylkeKodeverk {
    private List<KommuneKodeverk> kommuner = new ArrayList<>();
    private String navn;

    public FylkeKodeverk() {

    }

    public FylkeKodeverk(String navn) {
        this.navn = navn;
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
}
