package no.nav.fo.mia.controllers

import no.nav.fo.mia.util.*
import no.nav.fo.mia.consumers.StillingerConsumer
import no.nav.fo.mia.util.hovedkategoriTilUnderkategori
import no.nav.fo.mia.util.styrkTilHovedkategori
import no.nav.fo.mia.util.styrkTilUnderkategori
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.NullPointerException
import javax.inject.Inject

@RestController
@RequestMapping("/rest/rat")
class RatController @Inject
constructor(val stillingerService: StillingerConsumer) {

    @GetMapping("/fylke")
    fun rat(
            @RequestParam("kommunennummer") kommuneNummer: String,
            @RequestParam("styrkkode") styrkKode: String
    ): BehovsvurderingDTO {
        val komuner: List<String>
        val hovedkategori: String
        val underkategori: String
        val underkategorier: List<String>
        val komuneNavn: String
        val fylkesnavn: String
        try {
            val fylkesNummer = komuneNrTilFylkesNr[kommuneNummer]!!
            komuner = fylkeTilKommuner[fylkesNummer]!!
            hovedkategori = styrkTilHovedkategori[styrkKode]!!
            underkategori = styrkTilUnderkategori[styrkKode]!!
            underkategorier = hovedkategoriTilUnderkategori[hovedkategori]!!
            komuneNavn = kommuneNrTIlNavn[kommuneNummer]!!
            fylkesnavn = fylkesnrTilNavn[fylkesNummer]!!
        } catch (e: NullPointerException) {
            throw IllegalArgumentException("ikke gyldig")
        }

        val under = stillingerService.getAntallStillinger(komuner, listOf(underkategori))
        val hoved = stillingerService.getAntallStillinger(komuner, underkategorier)

        return BehovsvurderingDTO(
                kommuneNavn = komuneNavn,
                fylkesnavn = fylkesnavn,
                hovedkategori = StillingerIKategori(hovedkategori, hoved),
                underkategori = StillingerIKategori(underkategori, under)
        )
    }
}

data class BehovsvurderingDTO(val kommuneNavn: String, val fylkesnavn: String, val hovedkategori: StillingerIKategori, val underkategori: StillingerIKategori)
data class StillingerIKategori(val kategori: String, val antall: Int)
