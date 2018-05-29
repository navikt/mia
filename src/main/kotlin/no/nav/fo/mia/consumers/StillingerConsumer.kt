package no.nav.fo.mia.consumers

import com.github.javafaker.Faker
import no.nav.fo.mia.Bransje
import no.nav.fo.mia.Filtervalg
import no.nav.fo.mia.OmradeStilling
import no.nav.fo.mia.Stilling
import no.nav.fo.mia.util.stringToSeed
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*

interface StillingerConsumer {
    fun getYrkesomrader(fylkesnummer: String, filtervalg: Filtervalg): List<Bransje>
    fun getLedighetstallForValgtOmrade(filtervalg: Filtervalg): Int
    fun getStillinger(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String, filtervalg: Filtervalg): List<Bransje>
    fun getLedighetstallForKommuner(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, kommuner: List<String>, periode: String): List<OmradeStilling>
    fun getLedighetstallForFylker(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, periode: String): List<OmradeStilling>
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

    override fun getLedighetstallForKommuner(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, kommuner: List<String>, periode: String): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForFylker(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, periode: String): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

@Service
@Profile("mock")
class StillingerConsumerMock: StillingerConsumer {
    override fun getYrkesomrader(fylkesnummer: String, filtervalg: Filtervalg): List<Bransje> {
        val faker = Faker(Random(stringToSeed(fylkesnummer + filtervalg.toString())))
        val numBransjer = faker.number().numberBetween(3, 9)
        return (1..numBransjer).map {
            val navn = faker.company().industry()
            Bransje(
                    navn = navn,
                    id = navn,
                    strukturkode = faker.code().imei(),
                    antallStillinger = faker.number().numberBetween(1, 1000)
            )
        }
    }

    override fun getLedighetstallForValgtOmrade(filtervalg: Filtervalg): Int =
        getLedighetstallForKommuner(
                yrkesomradeid = filtervalg.yrkesomrade?: "",
                yrkesgrupper = filtervalg.yrkesgrupper,
                fylker = filtervalg.fylker,
                kommuner = filtervalg.kommuner,
                periode = ""
        ).sumBy { it.antallLedige }

    override fun getStillinger(yrkesgrupper: List<String>, filtervalg: Filtervalg): List<Stilling> {
        val yrkesgrupperString = yrkesgrupper.joinToString("")
        val faker = Faker(Random(stringToSeed(yrkesgrupperString + filtervalg.toString())))
        val numStillinger = faker.number().numberBetween(10, 10000)
        return (0..numStillinger).map {
            val navn = faker.lordOfTheRings().character()
            Stilling(
                    arbeidsgivernavn = navn,
                    id = navn,
                    tittel = faker.book().title(),
                    stillingstype = faker.book().genre(),
                    antallStillinger = faker.number().numberBetween(1, 10),
                    lokal = false
            )
        }
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String, filtervalg: Filtervalg): List<Bransje> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForKommuner(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, kommuner: List<String>, periode: String): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLedighetstallForFylker(yrkesomradeid: String, yrkesgrupper: List<String>, fylker: List<String>, periode: String): List<OmradeStilling> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}