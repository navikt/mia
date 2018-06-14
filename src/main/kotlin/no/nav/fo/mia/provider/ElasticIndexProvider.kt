package no.nav.fo.mia.provider

import no.nav.fo.mia.util.*
import no.nav.fo.mia.util.ElasticConstants.Companion.arbeidledige
import no.nav.fo.mia.util.ElasticConstants.Companion.arbeidsledigeIndex
import no.nav.fo.mia.util.ElasticConstants.Companion.doc
import no.nav.fo.mia.util.ElasticConstants.Companion.fylkesnummer
import no.nav.fo.mia.util.ElasticConstants.Companion.komunenummer
import no.nav.fo.mia.util.ElasticConstants.Companion.ledigeStillinger
import no.nav.fo.mia.util.ElasticConstants.Companion.periode
import no.nav.fo.mia.util.ElasticConstants.Companion.propertys
import no.nav.fo.mia.util.ElasticConstants.Companion.stillingerIndex
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkesgruppe_lvl_1
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkesgruppe_lvl_2
import no.nav.fo.mia.util.ElasticConstants.Companion.yrkeskode
import org.apache.http.util.EntityUtils
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.client.RestHighLevelClient
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface ElasticIndexProvider {
    fun createArbeidsledigeIndex()
    fun createStillingerIndex()
    fun getAllIndexes(): String
    fun index(bulk: BulkRequest)
    fun recreateIndex(index: String)
}

@Service
@Profile("!mock")
class ElasticIndexProviderImpl @Inject
constructor(
        val client: RestHighLevelClient
) : ElasticIndexProvider {
    private val LOGGER = LoggerFactory.getLogger(ElasticIndexProviderImpl::class.java)

    override fun recreateIndex(index: String) {
        when (index) {
            arbeidsledigeIndex -> {
                deleteArbeidsledigeIndex()
                createArbeidsledigeIndex()
            }
            stillingerIndex -> {
                deleteStillingerIndex()
                createStillingerIndex()
            }
            else -> throw error("$index kan ikke recreates")
        }
    }

    override fun index(bulk: BulkRequest) {
        val response = client.bulk(bulk)
        if(response.hasFailures()){
            throw RuntimeException(response.buildFailureMessage())
        }
    }

    override fun createArbeidsledigeIndex() {
        LOGGER.info("lager $arbeidsledigeIndex")

        val request = CreateIndexRequest(arbeidsledigeIndex)
        val properties = HashMap<String, Any>()
        val ledige = HashMap<String, Any>()
        val jsonMap = HashMap<String, Any>()

        properties[periode] = keyword
        properties[fylkesnummer] = keyword
        properties[komunenummer] = keyword
        properties[yrkeskode] = keyword
        properties[arbeidledige] = integer
        properties[yrkesgruppe_lvl_1] = keyword
        properties[yrkesgruppe_lvl_2] = keyword

        ledige[propertys] = properties
        jsonMap[doc] = ledige

        request.mapping(doc, jsonMap)

        client.indices().create(request)

        LOGGER.info("$arbeidsledigeIndex er velykket opprettet")
    }

    override fun createStillingerIndex() {
        LOGGER.info("lager $stillingerIndex")

        val request = CreateIndexRequest(stillingerIndex)
        val properties = HashMap<String, Any>()
        val ledige = HashMap<String, Any>()
        val jsonMap = HashMap<String, Any>()

        properties[periode] = keyword
        properties[fylkesnummer] = keyword
        properties[komunenummer] = keyword
        properties[yrkeskode] = keyword
        properties[ledigeStillinger] = integer
        properties[yrkesgruppe_lvl_1] = keyword
        properties[yrkesgruppe_lvl_2] = keyword

        ledige[propertys] = properties
        jsonMap[doc]

        request.mapping(doc, jsonMap)

        client.indices().create(request)

        LOGGER.info("$stillingerIndex er velykket opprettet")
    }

    private fun deleteArbeidsledigeIndex() {
        try {
            LOGGER.info("deleating $arbeidsledigeIndex")
            client
                    .indices()
                    .delete(DeleteIndexRequest(arbeidsledigeIndex))
            LOGGER.info("$arbeidsledigeIndex er slettet")
        } catch (e: Exception) {
            LOGGER.warn("sletting av $arbeidsledigeIndex feilet: ${e.message}")
        }
    }

    private fun deleteStillingerIndex() {
        try {
            LOGGER.info("deleating $stillingerIndex")
            client
                    .indices()
                    .delete(DeleteIndexRequest(stillingerIndex))
            LOGGER.info("$stillingerIndex er slettet")

        } catch (e: Exception) {
            LOGGER.warn("sletting av $stillingerIndex feilet: ${e.message}")
        }
    }

    override fun getAllIndexes(): String {
        val response = client.lowLevelClient.performRequest("GET", "/_cat/indices?v")
        return EntityUtils.toString(response.entity)
    }

    private val keyword = hashMapOf<String, Any>("type" to "keyword")
    private val integer = hashMapOf<String, Any>("type" to "integer")
}

@Service
@Profile("mock")
class ElasticIndexProviderMock : ElasticIndexProvider {
    override fun createArbeidsledigeIndex() {
    }

    override fun createStillingerIndex() {
    }

    override fun getAllIndexes(): String = "MOCK"

    override fun index(bulk: BulkRequest) {
    }

    override fun recreateIndex(index: String) {
    }
}
