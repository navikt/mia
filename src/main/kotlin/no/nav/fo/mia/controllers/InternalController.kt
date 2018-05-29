package no.nav.fo.mia.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal", produces = [(MediaType.TEXT_PLAIN_VALUE)])
class InternalController {
    @GetMapping("/isAlive")
    fun isAlive() = "Application is UP"

    @GetMapping("/isReady")
    fun isReady() = "Application is READY"
}