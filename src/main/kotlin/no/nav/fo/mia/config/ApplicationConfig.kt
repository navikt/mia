package no.nav.fo.mia.config

import no.nav.fo.mia.util.CorsFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.cache.annotation.EnableCaching


@Configuration
@EnableCaching
open class ApplicationConfig {
    @Bean
    open fun corsFilterRegistration(): FilterRegistrationBean<CorsFilter> {
        val frb: FilterRegistrationBean<CorsFilter> = FilterRegistrationBean()
        frb.filter = CorsFilter()
        frb.urlPatterns = arrayListOf("/*")
        return frb
    }
}
