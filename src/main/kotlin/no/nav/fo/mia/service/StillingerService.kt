package no.nav.fo.mia.service

import no.nav.fo.mia.Bransje
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.StillingerConsumer
import no.nav.fo.mia.consumers.StillingstypeConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class StillingerService @Inject
constructor (
        val stillingerConsumer: StillingerConsumer,
        val stillingstypeConsumer: StillingstypeConsumer
) {
    fun getYrkesomraderMedAntallStillinger(filtervalg: Filtervalg): List<Bransje> =
            stillingerConsumer.getYrkesomrader()
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
}