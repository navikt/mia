package no.nav.fo.mia.service

import no.nav.fo.mia.Bransje
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.Stilling
import no.nav.fo.mia.consumers.LedighetConsumer
import no.nav.fo.mia.consumers.StillingerConsumer
import no.nav.fo.mia.consumers.StillingstypeConsumer
import no.nav.fo.mia.util.pmap
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class StillingerService @Inject
constructor (
        val stillingerConsumer: StillingerConsumer,
        val stillingstypeConsumer: StillingstypeConsumer,
        val ledighetConsumer: LedighetConsumer
) {
    fun getYrkesomraderMedAntallStillinger(filtervalg: Filtervalg): List<Bransje> =
            stillingstypeConsumer.getYrkesomrader()
                    .map {
                        Bransje(
                                navn = it.navn,
                                id = it.id,
                                antallStillinger = stillingerConsumer.getAntallStillingerForYrkesomrade(it.id, filtervalg)
                        )
                    }

    fun getYrkesgrupperMedAntallStillinger(yrkesomrade: String, filtervalg: Filtervalg): List<Bransje> =
            stillingstypeConsumer.getYrkesgrupperForYrkesomrade(yrkesomrade)
                    .map {
                        Bransje(
                                navn = it.navn,
                                id = it.id,
                                strukturkode = it.strukturkode,
                                parent = it.parents,
                                antallStillinger = stillingerConsumer.getAntallStillingerForYrkesgruppe(it.id, filtervalg)
                        )
                    }

    fun getAntallStillingerForValgtOmrade(filtervalg: Filtervalg): Int =
            stillingerConsumer.getAntallStillingerForValgtOmrade(filtervalg)

    fun getStillingsannonser(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> =
            stillingerConsumer.getStillingsannonser(yrkesgrupper, filtervalg)

    fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int?> =
            ledighetConsumer.getLedigestillingerForSisteTrettenMaaneder(filtervalg)

    fun getAntallStillingerForValgteFylker(filtervalg: Filtervalg): Map<String, Int> =
            filtervalg.fylker
                    .pmap { it to stillingerConsumer.getLedigeStillingerForFylke(it, filtervalg) }
                    .toMap()

    fun getAntallStillingerForValgteKommuner(filtervalg: Filtervalg): Map<String, Int> =
            filtervalg.kommuner
                    .pmap { it to stillingerConsumer.getLedigeStillingerForKommune(it, filtervalg) }
                    .toMap()
}
