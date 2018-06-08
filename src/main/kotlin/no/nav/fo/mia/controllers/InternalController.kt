package no.nav.fo.mia.controllers

import no.nav.fo.mia.provider.ElasticIndexProvider
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
@RequestMapping("/internal", produces = [(MediaType.TEXT_PLAIN_VALUE)])
class InternalController @Inject
constructor(val es: ElasticIndexProvider) {
    @GetMapping("/isAlive")
    fun isAlive() = "Application is UP"

    @GetMapping("/isReady")
    fun isReady() = "Application is READY"

    @GetMapping("/createIndexes")
    fun createIndexes(): String {
        es.createArbeidsledigeIndex()
        es.createStillingerIndex()
        return es.getAll()
    }
}
