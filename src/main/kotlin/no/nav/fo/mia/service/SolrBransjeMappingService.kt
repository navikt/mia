package no.nav.fo.mia.service

import no.nav.fo.mia.consumers.StillingstypeConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SolrBransjeMappingService @Inject
constructor(
        val stillingstypeConsumer: StillingstypeConsumer
) {
    private val yrkesgruppeTilYrkesomradeMapping = createYrkesgruppeTilYrkesomradeMapping()
    private val strukturkodeTilYrkesgruppe = createStrukturkodeTilYrkesgruppe()

    fun getYrkesomradeForYrkesgruppe(yrkesgruppe: String): List<String> =
            yrkesgruppeTilYrkesomradeMapping[yrkesgruppe].orEmpty()

    fun getYrkesgruperForStrukturKode(kode: String): List<String> =
            strukturkodeTilYrkesgruppe[kode].orEmpty()

    private fun createYrkesgruppeTilYrkesomradeMapping(): Map<String, List<String>> =
        stillingstypeConsumer.getYrkesomrader()
                .flatMap { stillingstypeConsumer.getYrkesgrupperForYrkesomrade(it.id) }
                .map { it.id to it.parents }
                .distinct()
                .toMap()

    private fun createStrukturkodeTilYrkesgruppe(): Map<String, List<String>> =
            stillingstypeConsumer.getAlleYrkesgrupperOgYrkesomrader()
                    .filter { it.strukturkode != null }
                    .map { it.strukturkode!! to it.parents }
                    .toMap()
}
