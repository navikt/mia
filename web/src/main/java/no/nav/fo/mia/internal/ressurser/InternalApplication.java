package no.nav.fo.mia.internal.ressurser;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternalApplication extends ResourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(InternalApplication.class);

    public InternalApplication() {
        packages("no.nav.fo.mia.internal.ressurser");
        register(JacksonJaxbJsonProvider.class);
        register(MultiPartFeature.class);
        logger.info("Starter Jersey for Internal-API");
    }
}
