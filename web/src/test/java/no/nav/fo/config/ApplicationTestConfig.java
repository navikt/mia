package no.nav.fo.config;

import no.nav.fo.mia.config.EndpointsConfig;
import no.nav.fo.mia.config.ContentConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@EnableAspectJAutoProxy
@Configuration
@Import({
        ContentConfig.class,
        EndpointsConfig.class
})
@ComponentScan(basePackages = {"no.nav.fo.mia.rest", "no.nav.fo.mia.internal.ressurser"})
public class ApplicationTestConfig {

}