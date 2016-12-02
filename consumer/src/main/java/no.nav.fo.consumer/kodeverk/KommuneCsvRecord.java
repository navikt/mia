package no.nav.fo.consumer.kodeverk;

class KommuneCsvRecord {
    private String fylkenavn;
    private String fylkeid;
    private String kommunenavn;
    private String kommunenummer;

    KommuneCsvRecord(String csvline) {
        String[] splittedLine = csvline.split(",");

        this.fylkenavn = splittedLine[1];
        this.fylkeid = splittedLine[2];
        this.kommunenavn = splittedLine[4];
        this.kommunenummer = splittedLine[5];
    }

    public static boolean isValidCsvLine(String csvline) {
        return csvline.split(",").length == 6;
    }

    String getFylkenavn() {
        return fylkenavn;
    }

    void setFylkenavn(String fylkenavn) {
        this.fylkenavn = fylkenavn;
    }

    String getFylkesnummer() {
        return fylkeid;
    }

    void setFylkeid(String fylkeid) {
        this.fylkeid = fylkeid;
    }

    String getKommunenavn() {
        return kommunenavn;
    }

    void setKommunenavn(String kommunenavn) {
        this.kommunenavn = kommunenavn;
    }

    String getKommunenummer() {
        return kommunenummer;
    }

    void setKommunenummer(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }
}