package no.nav.fo.mia.consumers

import no.nav.fo.forenkletdeploy.util.fromJson
import no.nav.fo.mia.Omrade
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.common.SolrDocument
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface GeografiConsumer {
    fun hentAlleOmrader(): List<OmradeDTO>
    fun hentKommunerforFylker(fylker: List<String>): List<OmradeDTO>
}

@Service
@Profile("!mock")
open class GeografiConsumerImpl @Inject
constructor(
        val supportSolrClient: SolrClient
): GeografiConsumer {

    @Cacheable("alleOmrader")
    override fun hentAlleOmrader(): List<OmradeDTO> {
        val query = SolrQuery("NIVAA:[1 TO 3]")
                .addFilterQuery("DOKUMENTTYPE:GEOGRAFI")
        query.rows = 1000
        return supportSolrClient.query(query).results.map(this::documentToOmrade)
    }

    @Cacheable("kommunerForFylker")
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


class Omrader : ArrayList<Omrade>()

@Service
@Profile("mock")
class GeografiConsumerMock: GeografiConsumer {
    override fun hentAlleOmrader(): List<OmradeDTO> {
        val alleOmrader = hentAlleMockOmrader()
        val fylker = alleOmrader.map { omradeToOmradeDTO(it) }
        val kommuner = alleOmrader.flatMap { fylke -> fylke.underomrader.map { omradeToOmradeDTO(it, fylke.id) } }
        return fylker.union(kommuner).toList()
    }

    override fun hentKommunerforFylker(fylker: List<String>): List<OmradeDTO> =
            hentAlleMockOmrader()
                    .filter { fylker.contains(it.id) }
                    .flatMap { it.underomrader }
                    .map { omradeToOmradeDTO(it) }


    private fun omradeToOmradeDTO(omrade: Omrade, parent: String? = null): OmradeDTO =
            OmradeDTO(
                    id = omrade.id,
                    navn = omrade.navn,
                    strukturkode = omrade.strukturkode,
                    nivaa = omrade.nivaa,
                    parent = parent
            )

    private fun hentAlleMockOmrader(): List<Omrade> =
            fromJson(this.javaClass.getResourceAsStream("/mockdata/omrader.json"), Omrader::class.java)
}
