package no.nav.fo.mia.controllers

import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.inject.Inject

@RestController
@RequestMapping("/internal", produces = [(MediaType.TEXT_PLAIN_VALUE)])
class InternalController @Inject
constructor(

) {
    private val LOGGER = LoggerFactory.getLogger(InternalController::class.java)

    @GetMapping("/isAlive")
    fun isAlive() = "Application is UP"

    @GetMapping("/isReady")
    fun isReady() = "Application is READY"


}
