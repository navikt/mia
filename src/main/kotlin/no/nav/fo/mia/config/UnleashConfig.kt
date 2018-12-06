package no.nav.fo.mia.config

import no.finn.unleash.DefaultUnleash
import no.finn.unleash.FakeUnleash
import no.finn.unleash.Unleash
import no.finn.unleash.strategy.Strategy
import no.finn.unleash.util.UnleashConfig
import no.nav.fo.mia.util.getOptionalProperty
import no.nav.fo.mia.util.getRequiredProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!mock")
open class UnleashConfig {
    @Bean
    open fun unleash(): Unleash =
            DefaultUnleash(UnleashConfig.builder()
                    .appName("mia")
                    .unleashAPI(getRequiredProperty("UNLEASH_API_URL"))
                    .build(), IsNotProdStrategy())


    class IsNotProdStrategy : Strategy {
        override fun getName(): String {
            return "isNotProd"
        }

        override fun isEnabled(map: Map<String, String>) = !isProd()

        private fun isProd(): Boolean {
            val environment = getOptionalProperty("FASIT_ENVIRONMENT_NAME")
            return "p" == environment || "q0" == environment || environment?.isEmpty() ?: true
        }
    }

}


@Configuration
@Profile("mock")
open class unleashMockConfig {
    @Bean
    open fun unleash(): Unleash {
        val fakeUnleash = FakeUnleash()
        fakeUnleash.enableAll()
        return fakeUnleash
    }
}
