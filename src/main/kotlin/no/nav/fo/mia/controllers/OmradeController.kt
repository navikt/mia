package no.nav.fo.mia.controllers

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.Omrade
import no.nav.fo.mia.OmradeStilling
import no.nav.fo.mia.service.ArbeidsledighetService
import no.nav.fo.mia.service.GeografiService
import no.nav.fo.mia.service.StillingerService
import no.nav.fo.mia.util.sensurerData
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject
import javax.ws.rs.BeanParam

@RestController
@RequestMapping("/rest/omrader")
class OmradeController @Inject
constructor(
        val geografiService: GeografiService,
        val stillingerService: StillingerService,
        val arbeidsledighetService: ArbeidsledighetService
) {
    @GetMapping
    fun hentRelevanteOmrader(): List<Omrade> =
            geografiService.hentAlleRelevanteOmrader()

    @GetMapping("/fylkesdata")
    fun hentTallForValgteFylker(@BeanParam filtervalg: Filtervalg): List<OmradeStilling> {
        val ledigestillingerForFylker = stillingerService.getAntallStillingerForValgteFylker(filtervalg)
        val arbeidsledighetForFylker = arbeidsledighetService.getArbeidsledighetForValgteFylker(filtervalg)
        return filtervalg.fylker
                .map {
                    OmradeStilling(
                            id = it,
                            antallLedige = arbeidsledighetForFylker[it],
                            antallStillinger = ledigestillingerForFylker[it]?: 0
                    )
                }
    }

    @GetMapping("/kommunedata")
    fun hentTallForValgteKommuner(@BeanParam filtervalg: Filtervalg): List<OmradeStilling> {
        val kommunerForValgteHeleFylker = filtervalg.fylker
                .flatMap { geografiService.getKommunerForFylke(it) }
                .map { it.id }

        filtervalg.kommuner = filtervalg.kommuner.union(kommunerForValgteHeleFylker).toList()

        val ledigestillingerForKommuner = stillingerService.getAntallStillingerForValgteKommuner(filtervalg)
        val arbeidsledighetForKommuner = arbeidsledighetService.getArbeidsledighetForValgteKommuner(filtervalg)
        return filtervalg.kommuner
                .map {
                    OmradeStilling(
                            id = it,
                            antallLedige = arbeidsledighetForKommuner[it],
                            antallStillinger = ledigestillingerForKommuner[it]?: 0
                    )
                }
    }

}