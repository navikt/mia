package no.nav.fo.mia.consumers

import no.nav.fo.mia.Filtervalg
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

interface LedighetConsumer {
    fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int>
    fun getSisteOpplastedeMaaned(): String
    fun getLedighetForKommuner(filtervalg: Filtervalg): Map<String, Int>
    fun getLedighetForFylker(filtervalg: Filtervalg): Map<String, Int>
}

@Service
@Profile("!mock")
class LedighetConsumerImpl: LedighetConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSisteOpplastedeMaaned(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForKommuner(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForFylker(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@Service
@Profile("mock")
class LedighetConsumerMock: LedighetConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedigestillingerForSisteTrettenMaaneder(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSisteOpplastedeMaaned(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForKommuner(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetForFylker(filtervalg: Filtervalg): Map<String, Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}