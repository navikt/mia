package no.nav.fo.mia.domain;

import javax.ws.rs.QueryParam;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Filtervalg {
    @QueryParam("yrkesomrade")
    public String yrkesomrade;

    @QueryParam("yrkesgrupper[]")
    public List<String> yrkesgrupper;

    @QueryParam("fylker[]")
    public List<String> fylker;

    @QueryParam("kommuner[]")
    public List<String> kommuner;

    @QueryParam("eoseu")
    public boolean eoseu;

    @QueryParam("restenavverden")
    public boolean restenavverden;

    public String toString() {
        return this.yrkesomrade + listToString(this.yrkesgrupper) + listToString(this.fylker) + listToString(this.kommuner) + "eoseu" + eoseu + "restenavverden" + restenavverden;
    }

    private static <T> String listToString(List<T> list) {
        if(list == null) {
            return "";
        }

        List<String> listString = (new HashSet<>(list)).stream()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.toList());

        return String.join("", listString);

    }
}
