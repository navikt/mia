package no.nav.fo.mia.config;

import org.springframework.context.annotation.*;

@EnableAspectJAutoProxy
@Configuration
@Import({
        ContentConfig.class,
        EndpointsConfig.class,
        MetricsConfig.class,
        CacheConfig.class
})
@ComponentScan(basePackages = {"no.nav.fo.mia.rest", "no.nav.fo.mia.internal.ressurser"})
public class ApplicationConfig {

}