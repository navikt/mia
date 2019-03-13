package no.nav.fo.mia.controllers

import no.nav.fo.mia.util.*
import no.nav.fo.mia.consumers.StillingerConsumer
import no.nav.fo.mia.util.hovedkategoriTilUnderkategori
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.inject.Inject

@RestController
@RequestMapping("/rest/rat", "/rest/behovsvurdering")
class BehovsvurderingController @Inject
constructor(val stillingerService: StillingerConsumer) {

    @GetMapping("/fylke", "/stillingerifylke")
    fun rat(
            @RequestParam("kommunennummer") kommuneNummer: String,
            @RequestParam("styrkkode") styrkKode: String
    ): BehovsvurderingDTO {
        val fylkesNummer = komuneNrTilFylkesNr[kommuneNummer] ?: bydelTIlFylkesNr[kommuneNummer]
        val komuner: List<String>? = fylkeTilKommuner[fylkesNummer]
        val hovedkategori: String? = hovedkategori(styrkKode)
        val underkategori: String? = underkategori(styrkKode)
        val underkategorier: List<String>? = hovedkategoriTilUnderkategori[hovedkategori]
        val komuneNavn: String? = kommuneNrTIlNavn[kommuneNummer]
        val fylkesnavn: String? = fylkesnrTilNavn[fylkesNummer]

        val under = if(komuner == null || underkategori == null) 0 else stillingerService.getAntallStillinger(komuner, listOf(underkategori))
        val hoved = if(komuner == null || underkategorier == null) 0 else stillingerService.getAntallStillinger(komuner, underkategorier)

        return BehovsvurderingDTO(
                kommunenavn = komuneNavn ?: "UKJENT",
                fylkesnavn = fylkesnavn ?: "UKJENT",
                hovedkategori = StillingerIKategori(hovedkategori ?: "UKJENT", hoved),
                underkategori = StillingerIKategori(underkategori ?: "UKJENT", under)
        )
    }
}

data class BehovsvurderingDTO(val kommunenavn: String, val fylkesnavn: String, val hovedkategori: StillingerIKategori, val underkategori: StillingerIKategori)
data class StillingerIKategori(val kategori: String, val antallStillinger: Int)
