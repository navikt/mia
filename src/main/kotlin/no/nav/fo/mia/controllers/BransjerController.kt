package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.StillingerConsumer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/bransjer")
class BransjerController @Inject
constructor(
        val stillingerConsumer: StillingerConsumer
) {

    @GetMapping("/yrkesomrade")
    fun hentYrkesomrader(@RequestParam("fylkesnummer") fylkesnummer: String, @BeanParam filtervalg: Filtervalg) =
            stillingerConsumer.getYrkesomrader(fylkesnummer, filtervalg)

    @GetMapping("/yrkesgrupper")
    fun hentYrkesgrupper(@RequestParam("yrkesomrade") yrkesomrade: String, @BeanParam filtervalg: Filtervalg) =
            stillingerConsumer.getYrkesgrupperForYrkesomrade(yrkesomrade, filtervalg)

    @GetMapping("/stillinger")
    fun hentStillinger(@RequestParam("yrkesgrupper[]") yrkesgrupper: List<String>, @BeanParam filtervalg: Filtervalg) =
            stillingerConsumer.getStillinger(yrkesgrupper, filtervalg)
}