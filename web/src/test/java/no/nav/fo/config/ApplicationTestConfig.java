package no.nav.fo.config;

import no.nav.fo.mia.config.EndpointsConfig;
import no.nav.fo.mia.config.EnvironmentPropertiesConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@Configuration
@Import({
        EnvironmentPropertiesConfig.class,
        EndpointsConfig.class
})
@ComponentScan(basePackages = "no.nav.fo.mia.rest")
public class ApplicationTestConfig {

}