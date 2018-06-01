package no.nav.fo.mia.service

import no.nav.fo.mia.consumers.StillingstypeConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SolrBransjeMappingService @Inject
constructor (
        val stillingstypeConsumer: StillingstypeConsumer
) {
    private val idTilStrukturkodeMapping = createIdTilStrukturkodeMapping()
    private val strukturkodeTilId = createStrukturkodeTilIdMapping()
    private val yrkesgruppeTilYrkesomradeMapping = createYrkesgruppeTilYrkesomradeMapping()

    fun getIdForStrukturkode(strukturkode: String): String? =
            strukturkodeTilId[strukturkode]

    fun getStrukturkodeForId(id: String): String? =
            idTilStrukturkodeMapping[id]

    private fun createStrukturkodeTilIdMapping(): Map<String, String> =
            stillingstypeConsumer.getAlleYrkesgrupperOgYrkesomrader()
                    .map { it.strukturkode!! to it.id }
                    .toMap()

    private fun createIdTilStrukturkodeMapping(): Map<String, String> =
            stillingstypeConsumer.getAlleYrkesgrupperOgYrkesomrader()
                    .map { it.id to it.strukturkode!! }
                    .toMap()

    private fun createYrkesgruppeTilYrkesomradeMapping(): Map<String, List<String>> =
            stillingstypeConsumer.getYrkesomrader()
                    .map { it.id to stillingstypeConsumer.getYrkesgrupperForYrkesomrade(it.id).map { it.id } }
                    .toMap()
}