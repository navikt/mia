package no.nav.fo.mia.config;

import no.nav.fo.consumer.endpoints.StillingerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EndpointsConfig {
    @Bean
    public static StillingerEndpoint stillingerEndpoint() {
        return new StillingerEndpoint();
    }
}
