package no.nav.fo.mia.controllers

import no.nav.fo.mia.consumers.StatestikkConsumer
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@RestController
@RequestMapping("/internal", produces = [(MediaType.TEXT_PLAIN_VALUE)])
class InternalController @Inject
constructor(
        private val service: StatestikkConsumer
) {
    @GetMapping("/isAlive")
    fun isAlive(): String {
        return "Application is UP"
    }

    @GetMapping("/isReady")
    fun isReady() = "Application is READY"

}
