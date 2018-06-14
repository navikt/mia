package no.nav.fo.mia.service

import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.consumers.LedighetConsumer
import no.nav.fo.mia.util.sensurerData
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class ArbeidsledighetService @Inject
constructor (
        val ledighetConsumer: LedighetConsumer
) {
    fun getArbeidsledighetForValgteFylker(filtervalg: Filtervalg): Map<String, Int?> =
            sensurerData(ledighetConsumer.getArbeidsledighetPerFylker(ledighetConsumer.getSisteOpplastedeMaaned(), filtervalg))

    fun getArbeidsledighetForValgteKommuner(filtervalg: Filtervalg): Map<String, Int?> =
            sensurerData(ledighetConsumer.getArbeidsledighetPerKommuner(ledighetConsumer.getSisteOpplastedeMaaned(), filtervalg))

    fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int?> =
            sensurerData(ledighetConsumer.getArbeidsledighetForSisteTrettenMaaneder(filtervalg))
}
