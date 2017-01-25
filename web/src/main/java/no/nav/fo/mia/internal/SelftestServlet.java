package no.nav.fo.mia.internal;

import no.nav.sbl.dialogarena.common.web.selftest.SelfTestBaseServlet;
import no.nav.sbl.dialogarena.types.Pingable;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletException;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext;

public class SelftestServlet extends SelfTestBaseServlet {

    private ApplicationContext applicationContext;
    private static final Logger logger = getLogger(SelftestServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        applicationContext = getWebApplicationContext(getServletContext());
    }

    @Override
    public String getApplicationName() {
        return "mia";
    }

    @Override
    public Collection<? extends Pingable> getPingables() {
        final List<Pingable> pingables = new ArrayList<>(applicationContext.getBeansOfType(Pingable.class).values());
        pingables.add(pingUrl("stilling-solr", System.getProperty("stilling.solr.url")));
        pingables.add(pingUrl("mia-solr", getMiaSolrUrl()));
        return pingables;
    }

    private String getMiaSolrUrl() {
        String coreUrl = System.getProperty("miasolr.solr.ledigestillingercore.url");
        int index = coreUrl.lastIndexOf('/');
        return coreUrl.substring(0, index);
    }

    private Pingable pingUrl(final String name, final String url) {
        return () -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(10000);
                return connection.getResponseCode() == HTTP_OK
                        ? Pingable.Ping.lyktes(name)
                        : Pingable.Ping.feilet(name, new RuntimeException(connection.getResponseCode() + " " + connection.getResponseMessage()));
            } catch (Exception e) {
                logger.warn("<<<<<< Could not connect to {} on {}", name, url, e);
                return Pingable.Ping.feilet(name, e);
            }
        };
    }
}
