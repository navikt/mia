package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.LedighetConsumer
import no.nav.fo.mia.util.sensurerData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.HashMap
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/ledighet")
class LedighetController @Inject
constructor(
        val ledighetConsumer: LedighetConsumer
) {

    @GetMapping("/statistikk")
    fun hentLedighetForSisteTrettenMaaneder(@BeanParam filtervalg: Filtervalg): Map<String, Map<String, Int?>> {
        val statistikk = HashMap<String, Map<String, Int?>>()
        statistikk["arbeidsledighet"] = sensurerData(ledighetConsumer.getArbeidsledighetForSisteTrettenMaaneder(filtervalg))
        statistikk["ledigestillinger"] = ledighetConsumer.getLedigestillingerForSisteTrettenMaaneder(filtervalg)
        return statistikk
    }
}