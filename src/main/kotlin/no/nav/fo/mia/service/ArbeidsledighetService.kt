package no.nav.fo.mia.service

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.LedighetConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class ArbeidsledighetService @Inject
constructor (
        val ledighetConsumer: LedighetConsumer
) {
    fun getArbeidsledighetForValgteFylker(filtervalg: Filtervalg): Map<String, Int> =
            ledighetConsumer.getLedighetForFylker(ledighetConsumer.getSisteOpplastedeMaaned(), filtervalg)

    fun getArbeidsledighetForValgteKommuner(filtervalg: Filtervalg): Map<String, Int> =
            ledighetConsumer.getLedighetForKommuner(ledighetConsumer.getSisteOpplastedeMaaned(), filtervalg)
}