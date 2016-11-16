package no.nav.fo.mia.rest;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MuligheterIArbeidsmarkedetApplication extends ResourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(MuligheterIArbeidsmarkedetApplication.class);

    public MuligheterIArbeidsmarkedetApplication() {
        packages("no.nav.fo.mia.rest");
        register(JacksonJaxbJsonProvider.class);
        logger.info("Starter Jersey");
    }
}
