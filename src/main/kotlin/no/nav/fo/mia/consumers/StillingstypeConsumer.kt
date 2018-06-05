package no.nav.fo.mia.consumers

import com.github.javafaker.Faker
import no.nav.fo.mia.util.stringToSeed
import org.apache.solr.client.solrj.SolrClient
import org.apache.solr.client.solrj.SolrQuery
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*
import javax.inject.Inject

interface StillingstypeConsumer {
    fun getYrkesomrader(): List<YrkesomradeDTO>
    fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO>
    fun getAlleYrkesgrupperOgYrkesomrader(): List<YrkesgruppeDTO>
}

@Service
@Profile("!mock")
class StillingstypeConsumerImpl @Inject
constructor (
        val supportSolrClient: SolrClient,
        val stillingSolrClient: SolrClient
) : StillingstypeConsumer {
    override fun getYrkesomrader(): List<YrkesomradeDTO> {
        val query = SolrQuery("*:*")
        query.addFacetField("YRKGR_LVL_1")
                .addFacetField("YRKGR_LVL_1_ID")
                .rows = 0

        val response = stillingSolrClient.query(query)
        val navnFacets = response.getFacetField("YRKGR_LVL_1")
        val idFacets = response.getFacetField("YRKGR_LVL_1_ID")

        return (0 until navnFacets.valueCount).map {
            YrkesomradeDTO(
                    id = idFacets.values[it]?.name ?: "",
                    navn = navnFacets.values[it]?.name ?: ""
            )
        }
    }

    override fun getAlleYrkesgrupperOgYrkesomrader(): List<YrkesgruppeDTO> =
            getStillingstyper("*:*")

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO> =
            getStillingstyper("PARENT:$yrkesomradeid")

    private fun getStillingstyper(query: String): List<YrkesgruppeDTO> {
        val solrQuery = SolrQuery(query)
                .addFilterQuery("DOKUMENTTYPE:STILLINGSTYPE")

        return supportSolrClient.query(solrQuery).results.map {
            YrkesgruppeDTO(
                    id = it.getFieldValue("ID") as String,
                    navn = it.getFieldValue("NAVN") as String,
                    strukturkode = it.getFieldValue("STRUKTURKODE") as String?,
                    parents = it.getFieldValues("PARENT").map { it as String }
            )
        }
    }
}

@Service
@Profile("mock")
class StillingstypeConsumerMock: StillingstypeConsumer {
    override fun getYrkesomrader(): List<YrkesomradeDTO> {
        val faker = Faker(Random(stringToSeed("yrkesomrader")))
        return (0 until 9).map {
            YrkesomradeDTO(
                    id = faker.number().digits(6),
                    navn = faker.company().industry()
            )
        }
    }

    override fun getAlleYrkesgrupperOgYrkesomrader(): List<YrkesgruppeDTO> {
        val yrkesomrader = getYrkesomrader().map { YrkesgruppeDTO(id = it.id, navn = it.navn, strukturkode = it.id, parents = emptyList()) }
        val yrkesgrupper = yrkesomrader.flatMap { getYrkesgrupperForYrkesomrade(it.id) }
        return yrkesomrader.union(yrkesgrupper).toList()
    }

    override fun getYrkesgrupperForYrkesomrade(yrkesomradeid: String): List<YrkesgruppeDTO> {
        val faker = Faker(Random(stringToSeed("yrkesomrade:$yrkesomradeid")))
        val numGrupper = faker.number().numberBetween(4, 9)
        return (0 until numGrupper).map {
            YrkesgruppeDTO(
                    id = faker.number().digits(6),
                    navn = faker.pokemon().name(),
                    strukturkode = faker.numerify("struktur.#####"),
                    parents = listOf(yrkesomradeid)
            )
        }
    }
}
