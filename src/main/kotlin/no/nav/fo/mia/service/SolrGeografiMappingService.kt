package no.nav.fo.mia.service

import no.nav.fo.mia.consumers.GeografiConsumer
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SolrGeografiMappingService @Inject
constructor (
        val geografiConsumer: GeografiConsumer
) {
    private val strukturkodeTilIdMapping = createStrukturkodeTilIdMapping()
    private val idTilStrukturkodeMapping = createIdTilStrukturkodeMapping()

    fun getIdForStrukturkode(strukturkode: String): String? =
            strukturkodeTilIdMapping[strukturkode]

    fun getStrukturkodeForId(id: String): String? =
            idTilStrukturkodeMapping[id]

    private fun createStrukturkodeTilIdMapping(): Map<String, String> =
            geografiConsumer.hentAlleOmrader()
                    .map { vaskStrukturkode(it.strukturkode) to it.id }
                    .toMap()

    private fun createIdTilStrukturkodeMapping(): Map<String, String> =
            geografiConsumer.hentAlleOmrader()
                    .map { it.id to vaskStrukturkode(it.strukturkode) }
                    .toMap()

    private fun vaskStrukturkode(strukturkode: String): String {
        if (strukturkode.contains(".")) {
            return strukturkode.replace("NO", "").split(".")[1]
        }
        return strukturkode.replace("NO", "")
    }
}