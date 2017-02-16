package no.nav.fo.mia.config;

import no.nav.fo.consumer.fillager.Fillager;
import no.nav.sbl.tekster.TeksterAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class ContentConfig {
    @Value("${folder.ledetekster.path}")
    private String ledeteksterPath;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public static Fillager fillageg() {
        return new Fillager();
    }

    @Bean
    public TeksterAPI teksterApi() {
        return new TeksterAPI(ledeteksterPath + "/tekster", "mia");
    }
}
