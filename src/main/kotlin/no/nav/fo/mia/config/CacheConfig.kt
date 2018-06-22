package no.nav.fo.mia.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.jcache.JCacheCacheManager
import org.springframework.context.annotation.Profile
import javax.cache.Caching


@Configuration
@Profile("!mock")
@EnableCaching
open class CacheConfig {
    @Bean
    open fun cacheManager(): CacheManager {
        val cachingProvider = Caching.getCachingProvider()
        val cacheManager = cachingProvider.getCacheManager(
                javaClass.getResource("/cache.xml").toURI(),
                cachingProvider.defaultClassLoader
        )
        return JCacheCacheManager(cacheManager)
    }
}