package no.nav.fo.mia.consumers

import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.common.SolrDocument
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface GeografiConsumer {
    fun hentAlleOmrader(): List<OmradeDTO>
    fun hentKommunerforFylker(fylker: List<String>): List<OmradeDTO>
}

@Service
@Profile("!mock")
class GeografiConsumerImpl @Inject
constructor(
        val supportSolrClient: SolrClient
): GeografiConsumer {

    override fun hentAlleOmrader(): List<OmradeDTO> {
        val query = SolrQuery("NIVAA:[1 TO 3]")
                .addFilterQuery("DOKUMENTTYPE:GEOGRAFI")
        query.rows = 1000
        return supportSolrClient.query(query).results.map(this::documentToOmrade)
    }

    override fun hentKommunerforFylker(fylker: List<String>): List<OmradeDTO> {
        if (fylker.isEmpty()) {
            return emptyList()
        }

        val query = SolrQuery("*:*")
                .addFilterQuery("DOKUMENTTYPE:GEOGRAFI")
                .addFilterQuery("NIVAA:3")
                .addFilterQuery("PARENT: ${fylker.joinToString(" OR ")}")
        query.rows = 1000
        return supportSolrClient.query(query).results.map(this::documentToOmrade)
    }

    private fun documentToOmrade(document: SolrDocument): OmradeDTO =
            OmradeDTO(
                    id = document.getFieldValue("ID") as String,
                    strukturkode = document.getFieldValue("STRUKTURKODE") as String? ?: "",
                    navn = document.getFieldValue("NAVN") as String,
                    nivaa = document.getFieldValue("NIVAA") as String,
                    parent = document.getFieldValues("PARENT")?.first() as String?
            )
}

@Service
@Profile("mock")
class GeografiConsumerMock: GeografiConsumer {
    override fun hentAlleOmrader(): List<OmradeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hentKommunerforFylker(fylker: List<String>): List<OmradeDTO> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
