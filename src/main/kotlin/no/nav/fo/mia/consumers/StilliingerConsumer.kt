package no.nav.fo.mia.consumers

import no.nav.fo.mia.util.fylkesnrTilNavn
import no.nav.fo.mia.util.hovedkategoriTIlunderkategori
import no.nav.fo.mia.util.kommuneNrTIlNavn
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject
import kotlin.random.Random

interface StilliingerConsumer {
    fun getStillingerForHovedkategorier(komuner: List<String>): Map<String, Int>
    fun getStillingerForUnderkattegorier(komuner: List<String>): Map<String, Int>
    fun getStillingerPerKomune(underkategorier: List<String>): Map<String, Int>
    fun getStillingerPerFylke(underkategorier: List<String>): Map<String, Int>
    fun getAntallStillinger(komuner: List<String>, underkategorier: List<String>): Int
}

@Service
@Profile("!mock")
open class StilliingerConsumerImpl @Inject
constructor(
        private val esclient: RestHighLevelClient
) : StilliingerConsumer {
    override fun getStillingerForHovedkategorier(komuner: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = komuneFilter(komuner),
                    summeringskollone = antall,
                    grupperingsKollone = hovedkategori,
                    index = stillingerIndex
            )

    override fun getStillingerForUnderkattegorier(komuner: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = komuneFilter(komuner),
                    summeringskollone = antall,
                    grupperingsKollone = underkattegori,
                    index = stillingerIndex
            )

    override fun getStillingerPerKomune(underkategorier: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = underkategoriFilter(underkategorier),
                    summeringskollone = antall,
                    grupperingsKollone = komuneNumer,
                    index = stillingerIndex
            )

    override fun getStillingerPerFylke(underkategorier: List<String>) =
            esclient.sumPerBucket(
                    filterQuery = underkategoriFilter(underkategorier),
                    summeringskollone = antall,
                    grupperingsKollone = fylkesnummer,
                    index = stillingerIndex
            )

    override fun getAntallStillinger(komuner: List<String>, underkategorier: List<String>) =
            esclient.sum(
                    filterQuery = must(komuneFilter(komuner), underkategoriFilter(underkategorier)),
                    summeringskollone = antall,
                    index = stillingerIndex
            )
}


@Service
@Profile("mock")
open class StilliingerConsumerMock : StilliingerConsumer {
    override fun getStillingerForHovedkategorier(komuner: List<String>) =
            hovedkategoriTIlunderkategori
                    .map { it.key to Random.nextInt(0, 10_000) }
                    .toMap()


    override fun getStillingerForUnderkattegorier(komuner: List<String>): Map<String, Int> =
            hovedkategoriTIlunderkategori
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

    override fun getAntallStillinger(komuner: List<String>, underkategorier: List<String>): Int =
            Random.nextInt(0, 10_000)

}
