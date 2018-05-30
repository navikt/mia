package no.nav.fo.mia.consumers

import no.nav.fo.mia.Bransje
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.OmradeStilling
import no.nav.fo.mia.Stilling
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

interface StillingerConsumer {
    fun getYrkesomrader(fylkesnummer: String, filtervalg: Filtervalg): List<Bransje>
    fun getLedighetstallForValgtOmrade(filtervalg: Filtervalg): Int
    fun getStillinger(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String, filtervalg: Filtervalg): List<Bransje>
    fun getLedighetstallForKommuner(periode: String, filtervalg: Filtervalg): List<OmradeStilling>
    fun getLedighetstallForFylker(periode: String, filtervalg: Filtervalg): List<OmradeStilling>
}

@Service
@Profile("!mock")
class StillingerConsumerImpl: StillingerConsumer {
    override fun getYrkesomrader(fylkesnummer: String, filtervalg: Filtervalg): List<Bransje> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForValgtOmrade(filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStillinger(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String, filtervalg: Filtervalg): List<Bransje> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForKommuner(periode: String, filtervalg: Filtervalg): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForFylker(periode: String, filtervalg: Filtervalg): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

@Service
@Profile("mock")
class StillingerConsumerMock: StillingerConsumer {
    override fun getYrkesomrader(fylkesnummer: String, filtervalg: Filtervalg): List<Bransje> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForValgtOmrade(filtervalg: Filtervalg): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getStillinger(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String, filtervalg: Filtervalg): List<Bransje> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForKommuner(periode: String, filtervalg: Filtervalg): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForFylker(periode: String, filtervalg: Filtervalg): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}