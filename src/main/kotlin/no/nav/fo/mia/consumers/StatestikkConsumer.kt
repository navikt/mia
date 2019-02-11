package no.nav.fo.mia.consumers

import no.nav.fo.mia.util.fylkesnrTilNavn
import no.nav.fo.mia.util.kommuneNrTIlNavn
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

interface StatestikkConsumer {
    fun getArbeidsledighetForSisteTrettenMaaneder(underkategorier: List<String>, komuner: List<String>): Map<String, Int?>
    fun getStillingerForSisteTrettenMaaneder(underkategorier: List<String>, komuner: List<String>): Map<String, Int>
    fun getArbeidsledigePerKomuner(underkategorier: List<String>): Map<String, Int?>
    fun getArbeidsledigePerFylker(underkategorier: List<String>): Map<String, Int?>
}

@Service
@Profile("!mock")
open class StatestikkConsumerImpl @Inject
constructor(
        private val esClient: RestHighLevelClient
) : StatestikkConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(underkategorier: List<String>, komuner: List<String>) =
            esClient
                    .sumPerBucket(
                            filterQuery = must(komuneFilter(komuner), underkategoriFilter(underkategorier)),
                            grupperingsKollone = perioder,
                            summeringskollone = antall,
                            index = arbeidsledighetsIndex
                    ).map { it.key to it.value.sensurer() }
                    .toMap()

    override fun getStillingerForSisteTrettenMaaneder(underkategorier: List<String>, komuner: List<String>) =
            esClient
                    .sumPerBucket(
                            filterQuery = must(komuneFilter(komuner), underkategoriFilter(underkategorier)),
                            grupperingsKollone = perioder,
                            summeringskollone = antall,
                            index = stilingsStattestikkIndex
                    )

    override fun getArbeidsledigePerKomuner(underkategorier: List<String>) =
            esClient
                    .sumPerBucket(
                            filterQuery = must(underkategoriFilter(underkategorier), periodeFilter(getSisteOpplastedeMaaned())),
                            grupperingsKollone = komuneNumer,
                            summeringskollone = antall,
                            index = arbeidsledighetsIndex
                    ).map { it.key to it.value.sensurer() }
                    .toMap()

    override fun getArbeidsledigePerFylker(underkategorier: List<String>) =
            esClient
                    .sumPerBucket(
                            filterQuery = must(underkategoriFilter(underkategorier), periodeFilter(getSisteOpplastedeMaaned())),
                            grupperingsKollone = fylkesnummer,
                            summeringskollone = antall,
                            index = arbeidsledighetsIndex
                    ).map { it.key to it.value.sensurer() }
                    .toMap()

    private fun getSisteOpplastedeMaaned() =
            esClient
                    .max(
                            index = *arrayOf(arbeidsledighetsIndex),
                            kolonne = perioder
                    )
}

@Service
@Profile("mock")
open class StatestikkConsumerMock : StatestikkConsumer {
    override fun getArbeidsledighetForSisteTrettenMaaneder(underkategorier: List<String>, komuner: List<String>): Map<String, Int?> =
            getPerioder()
                    .map {
                        it to getRandom(100_000)
                    }.toMap()

    override fun getStillingerForSisteTrettenMaaneder(underkategorier: List<String>, komuner: List<String>): Map<String, Int> =
            getPerioder().map {
                it to Random.nextInt(0, 100_000)
            }.toMap()

    override fun getArbeidsledigePerKomuner(underkategorier: List<String>): Map<String, Int?> =
            kommuneNrTIlNavn.map {
                it.key to getRandom()
            }.toMap()

    override fun getArbeidsledigePerFylker(underkategorier: List<String>): Map<String, Int?> =
            fylkesnrTilNavn.map {
                it.key to getRandom()
            }.toMap()

    private fun getPerioder() = (1..13L).map {
        val minusMonths = LocalDate.now().minusMonths(it)
        "${minusMonths.year}${minusMonths.monthValue}"
    }.toList()

    private fun getRandom(max: Int = 100): Int? {
        val nextInt = Random.nextInt(0, max)
        if (nextInt % 8 == 0 || nextInt < 4) {
            return null
        }

        return nextInt
    }
}
