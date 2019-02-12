package no.nav.fo.mia.consumers

import no.nav.fo.mia.util.fylkesnrTilNavn
import no.nav.fo.mia.util.hovedkategoriTilUnderkategori
import no.nav.fo.mia.util.kommuneNrTIlNavn
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject
import kotlin.random.Random

interface StillingerConsumer {
    fun getStillingerPerHovedkategorier(komuner: List<String>): Map<String, Int>
    fun getStillingerPerUnderkategorier(komuner: List<String>): Map<String, Int>
    fun getStillingerPerKomune(underkategorier: List<String>): Map<String, Int>
    fun getStillingerPerFylke(underkategorier: List<String>): Map<String, Int>
    fun getAntallStillinger(komuner: List<String>, underkategorier: List<String>): Int
}

@Service
@Profile("!mock")
open class StillingerConsumerImpl @Inject
constructor(
        private val esclient: RestHighLevelClient
) : StillingerConsumer {
    override fun getStillingerPerHovedkategorier(kommuner: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = must(
                            komuneFilter(kommuner),
                            aktivPublicQuery()),
                    summeringskollone = antall,
                    grupperingsKollone = hovedkategori,
                    index = stillingerIndex
            )

    override fun getStillingerPerUnderkategorier(kommuner: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = must(
                            komuneFilter(kommuner),
                            aktivPublicQuery()),
                    summeringskollone = antall,
                    grupperingsKollone = underkattegori,
                    index = stillingerIndex
            )

    override fun getStillingerPerKomune(underkategorier: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = must(
                            underkategoriFilter(underkategorier),
                            aktivPublicQuery()),
                    summeringskollone = antall,
                    grupperingsKollone = komuneNumer,
                    index = stillingerIndex
            )

    override fun getStillingerPerFylke(underkategorier: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = must(
                            underkategoriFilter(underkategorier),
                            aktivPublicQuery()),
                    summeringskollone = antall,
                    grupperingsKollone = fylkesnummerStillinger,
                    index = stillingerIndex
            )

    override fun getAntallStillinger(kommuner: List<String>, underkategorier: List<String>) =
            esclient.sum(
                    filterQuery = must(
                            komuneFilter(kommuner),
                            underkategoriFilter(underkategorier),
                            aktivPublicQuery()),
                    summeringskollone = antall,
                    index = stillingerIndex
            )
}


@Service
@Profile("mock")
open class StillingerConsumerMock : StillingerConsumer {
    override fun getStillingerPerHovedkategorier(kommuner: List<String>) =
            hovedkategoriTilUnderkategori
                    .map { it.key to Random.nextInt(0, 10_000) }
                    .toMap()


    override fun getStillingerPerUnderkategorier(kommuner: List<String>): Map<String, Int> =
            hovedkategoriTilUnderkategori
                    .flatMap { hovedkategori ->
                        hovedkategori.value.map {underkategori ->
                            underkategori to Random.nextInt(0, 10_000)
                        }
                    }.toMap()

    override fun getStillingerPerKomune(underkategorier: List<String>): Map<String, Int>  =
            kommuneNrTIlNavn
                    .map { it.key to Random.nextInt(0, 10_000) }
                    .toMap()

    override fun getStillingerPerFylke(underkategorier: List<String>): Map<String, Int> =
            fylkesnrTilNavn
                    .map { it.key to Random.nextInt(0, 10_000) }
                    .toMap()

    override fun getAntallStillinger(kommuner: List<String>, underkategorier: List<String>): Int =
            Random.nextInt(0, 10_000)

}
