package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.StatestikkConsumer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap
import javax.inject.Inject

@RestController
@RequestMapping("/rest/ledighet")
class LedighetController2 @Inject
constructor(
        val statestikkService: StatestikkConsumer
) {

    @GetMapping("/statistikk")
    fun hentLedighetForSisteTrettenMaaneder(@RequestParam filtervalg: Filtervalg): Map<String, Map<String, Int?>> {
        val underkategorier = underkategorierFra(filtervalg)
        val komuner = komunerFra(filtervalg)
        val statistikk = HashMap<String, Map<String, Int?>>()
        statistikk["arbeidsledighet"] = statestikkService.getArbeidsledighetForSisteTrettenMaaneder(underkategorier = underkategorier, komuner = komuner)
        statistikk["ledigestillinger"] = statestikkService.getStillingerForSisteTrettenMaaneder(underkategorier = underkategorier, komuner = komuner)
        return statistikk
    }
}
