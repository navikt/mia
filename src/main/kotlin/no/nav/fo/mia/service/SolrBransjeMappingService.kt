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
    private val strukturkodeTilYrkesgruppe  = createStrukturkodeTilYrkesgruppe()

    fun getIdForStrukturkode(strukturkode: String): String? =
            strukturkodeTilId[strukturkode]

    fun getStrukturkodeForId(id: String): String? =
            idTilStrukturkodeMapping[id]

    fun getYrkesomradeForYrkesgruppe(yrkesgruppe: String): List<String> =
            yrkesgruppeTilYrkesomradeMapping[yrkesgruppe].orEmpty()

    fun getYrkesgruperForStrukturKode(kode: String): List<String> =
            strukturkodeTilYrkesgruppe[kode].orEmpty()

    private fun createStrukturkodeTilIdMapping(): Map<String, String> =
            stillingstypeConsumer.getAlleYrkesgrupperOgYrkesomrader()
                    .filter { it.strukturkode != null }
                    .map { it.strukturkode!! to it.id }
                    .toMap()

    private fun createIdTilStrukturkodeMapping(): Map<String, String> =
            stillingstypeConsumer.getAlleYrkesgrupperOgYrkesomrader()
                    .filter { it.strukturkode != null }
                    .map { it.id to it.strukturkode!! }
                    .toMap()

    private fun createYrkesgruppeTilYrkesomradeMapping(): Map<String, List<String>> =
            stillingstypeConsumer.getYrkesomrader()
                    .map { it.id to stillingstypeConsumer.getYrkesgrupperForYrkesomrade(it.id).map { it.id } }
                    .toMap()

    private fun createStrukturkodeTilYrkesgruppe(): Map<String, List<String>> =
            stillingstypeConsumer.getAlleYrkesgrupperOgYrkesomrader()
                    .filter { it.strukturkode != null }
                    .map { it.strukturkode!! to it.parents }
                    .toMap()
}
