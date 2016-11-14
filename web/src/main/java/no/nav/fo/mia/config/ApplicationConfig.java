package no.nav.fo.mia.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({
        EnvironmentPropertiesConfig.class
})
@ComponentScan(basePackages = "no.nav.fo.mia.rest")
public class ApplicationConfig {

}