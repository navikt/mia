package no.nav.fo.mia.config

import no.nav.fo.mia.LoggerInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.Resource
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver

import org.springframework.web.servlet.config.annotation.InterceptorRegistry

@Configuration
open class WebMvcConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/geojson/*")
                .addResourceLocations("classpath:/kart/geojson/")
                .resourceChain(true)
                .addResolver(object : PathResourceResolver() {
                    override fun getResource(resourcePath: String, location: Resource): Resource {
                        return location.createRelative(resourcePath)
                    }
                })

        registry.addResourceHandler("/tiles/*")
                .addResourceLocations("classpath:/kart/tiles/")
                .resourceChain(true)
                .addResolver(object : PathResourceResolver() {
                    override fun getResource(resourcePath: String, location: Resource): Resource {
                        val file = location.createRelative(resourcePath)
                        if (file.exists()) {
                            return file
                        }
                        return location.createRelative("blank.png")
                    }
                })
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(LoggerInterceptor())
    }
}
