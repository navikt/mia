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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filtervalg that = (Filtervalg) o;

        if (eoseu != that.eoseu) return false;
        if (restenavverden != that.restenavverden) return false;
        if (yrkesomrade != null ? !yrkesomrade.equals(that.yrkesomrade) : that.yrkesomrade != null) return false;
        if (yrkesgrupper != null ? !yrkesgrupper.equals(that.yrkesgrupper) : that.yrkesgrupper != null) return false;
        if (fylker != null ? !fylker.equals(that.fylker) : that.fylker != null) return false;
        return kommuner != null ? kommuner.equals(that.kommuner) : that.kommuner == null;
    }

    @Override
    public int hashCode() {
        int result = yrkesomrade != null ? yrkesomrade.hashCode() : 0;
        result = 31 * result + (yrkesgrupper != null ? yrkesgrupper.hashCode() : 0);
        result = 31 * result + (fylker != null ? fylker.hashCode() : 0);
        result = 31 * result + (kommuner != null ? kommuner.hashCode() : 0);
        result = 31 * result + (eoseu ? 1 : 0);
        result = 31 * result + (restenavverden ? 1 : 0);
        return result;
    }

    private static <T> String listToString(List<T> list) {
        if(list == null) {
            return "";
        }

        List<String> listString = (new HashSet<>(list)).stream()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.toList());

        return String.join(",", listString);

    }
}
