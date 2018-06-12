package no.nav.fo.mia.provider

import no.nav.fo.mia.util.*
import org.apache.http.util.EntityUtils
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.stereotype.Service
import javax.inject.Inject

interface ElasticIndexProvider {
    fun createArbeidsledigeIndex()
    fun createStillingerIndex()
    fun tryDeleteArbeidsledigeIndex()
    fun tryDeleteStillingerIndex()
    fun getAll(): String
    fun index(bulk: BulkRequest)
    fun recreateIndex(index: String)
}

@Service
//@Profile("!mock")
class ElasticIndexProviderImpl @Inject
constructor(
        val client: RestHighLevelClient
) : ElasticIndexProvider {
    override fun recreateIndex(index: String) {
        when (index) {
            arbeidsledigeIndex -> {
                tryDeleteArbeidsledigeIndex()
                createArbeidsledigeIndex()
            }
            stillingerIndex -> {
                tryDeleteStillingerIndex()
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
    }

    override fun createStillingerIndex() {
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
    }

    override fun tryDeleteArbeidsledigeIndex() {
        try {
            client
                    .indices()
                    .delete(DeleteIndexRequest(arbeidsledigeIndex))
        } catch (e: Exception) {
            //TODO
        }
    }

    override fun tryDeleteStillingerIndex() {
        try {
            client
                    .indices()
                    .delete(DeleteIndexRequest(stillingerIndex))
        } catch (e: Exception) {
            //TODO
        }
    }

    override fun getAll(): String {
        val response = client.lowLevelClient.performRequest("GET", "/_cat/indices?v")
        return EntityUtils.toString(response.entity)
    }

    private val keyword = hashMapOf<String, Any>("type" to "keyword")
    private val integer = hashMapOf<String, Any>("type" to "integer")
}

