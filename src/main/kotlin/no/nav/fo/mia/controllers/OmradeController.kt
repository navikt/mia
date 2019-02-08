package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.Omrade
import no.nav.fo.mia.OmradeStilling
import no.nav.fo.mia.consumers.StatestikkConsumer
import no.nav.fo.mia.consumers.StilliingerConsumer
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/omrader")
class OmradeController2 @Inject
constructor(
        val statestikkConsumer: StatestikkConsumer,
        val stilliingerConsumer: StilliingerConsumer
) {
    @GetMapping
    fun hentRelevanteOmrader(): List<Omrade> = alleRellevanteOmråder()

    @GetMapping("/fylkesdata")
    fun hentTallForValgteFylker(@BeanParam filtervalg: Filtervalg): List<OmradeStilling> {
        val arbeidsledigePerFylker = statestikkConsumer.getArbeidsledigePerFylker(underkategorierFra(filtervalg))
        val stillingerPerFylke = stilliingerConsumer.getStillingerPerFylke(underkategorierFra(filtervalg))
        return filtervalg.fylker
                .map {
                    OmradeStilling(
                            id = it,
                            antallLedige = arbeidsledigePerFylker[it],
                            antallStillinger = stillingerPerFylke[it]?: 0
                    )
                }
    }

    @GetMapping("/kommunedata")
    fun hentTallForValgteKommuner(@BeanParam filtervalg: Filtervalg): List<OmradeStilling> {
        val arbeidsledigePerKomuner = statestikkConsumer.getArbeidsledigePerKomuner(underkategorierFra(filtervalg))
        val stillingerPerKomune = stilliingerConsumer.getStillingerPerKomune(underkategorierFra(filtervalg))

        return komunerFra(filtervalg)
                .map {
                    OmradeStilling(
                            id = it,
                            antallLedige = arbeidsledigePerKomuner[it],
                            antallStillinger = stillingerPerKomune[it]?: 0
                    )
                }
    }

}
