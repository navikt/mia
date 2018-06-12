package no.nav.fo.mia.controllers

import no.nav.fo.mia.service.IndeksererService
import no.nav.fo.mia.util.arbeidsledigeIndex
import no.nav.fo.mia.util.stillingerIndex
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.inject.Inject
import org.springframework.web.multipart.MultipartFile
import kotlin.concurrent.thread

@RestController
@RequestMapping("/internal", produces = [(MediaType.TEXT_PLAIN_VALUE)])
class InternalController @Inject
constructor(
        val service: IndeksererService
) {
    private val LOGGER = LoggerFactory.getLogger(InternalController::class.java)

    @GetMapping("/isAlive")
    fun isAlive() = "Application is UP"

    @GetMapping("/isReady")
    fun isReady() = "Application is READY"

    @GetMapping("/es/indexes")
    fun getIndexes() = service.getAll()

    @PostMapping("/arbeidsledigecore")
    fun arbeidlsedigeIndex(@RequestParam("file")  file: MultipartFile): String {
        LOGGER.info(arbeidsledigeIndex)
        return service.recreatIndex(file.inputStream, arbeidsledigeIndex)
    }

    @PostMapping("/ledigestillingercore")
    fun stilingerIndex(@RequestParam("file")  file: MultipartFile): String {
        LOGGER.info(stillingerIndex)
        thread {
            service.recreatIndex(file.inputStream, stillingerIndex)
        }
        return ":)"
    }

}
