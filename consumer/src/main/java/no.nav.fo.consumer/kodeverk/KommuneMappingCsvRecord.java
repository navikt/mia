package no.nav.fo.consumer.kodeverk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class KommuneMappingCsvRecord {
    private String kommunenummer;
    private String solrid;

    KommuneMappingCsvRecord(String csvline) {
        String[] splittedLine = csvline.split(";");
        Matcher matcher = Pattern.compile("(\\d{4})").matcher(splittedLine[1]);
        if(matcher.find()) {
            setKommunenummer(matcher.group(1));
        }
        setSolrid(splittedLine[0]);
    }

    public static boolean isValidCsvLine(String csvline) {
        String[] splittedLine = csvline.split(";");
        return splittedLine.length == 3 && splittedLine[1].matches("\"NO\\d{2}\\.\\d{4}\"");
    }

    public String getKommunenummer() {
        return kommunenummer;
    }

    public void setKommunenummer(String kommunenummer) {
        this.kommunenummer = kommunenummer;
    }

    public String getSolrid() {
        return solrid;
    }

    public void setSolrid(String solrid) {
        this.solrid = solrid;
    }
}