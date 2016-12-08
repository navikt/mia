package no.nav.fo.mia.config;

import org.springframework.context.annotation.*;

@EnableAspectJAutoProxy
@Configuration
@Import({
        EnvironmentPropertiesConfig.class,
        EndpointsConfig.class,
        MetricsConfig.class,
        CacheConfig.class
})
@ComponentScan(basePackages = "no.nav.fo.mia.rest")
public class ApplicationConfig {

}