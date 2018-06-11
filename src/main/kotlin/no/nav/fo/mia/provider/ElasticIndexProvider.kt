package no.nav.fo.mia.provider

import org.apache.http.util.EntityUtils
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import javax.inject.Inject

interface ElasticIndexProvider{
    fun createArbeidsledigeIndex()
    fun createStillingerIndex()
    fun getAll(): String
}

@Service
@Profile("!mock")
class ElasticIndexProviderImpl @Inject
constructor(
        val client: RestHighLevelClient
): ElasticIndexProvider {
    override fun createArbeidsledigeIndex() {
        val request = CreateIndexRequest("arbeidsledige")
        val properties = HashMap<String, Any>()
        val ledige = HashMap<String, Any>()
        val jsonMap = HashMap<String, Any>()



        properties["PERIODE"] = keyword
        properties["FYLKESNR"] = keyword
        properties["KOMMUNENR"] = keyword
        properties["YRKESKODE"] = keyword
        properties["ARBEIDSLEDIGE"] = integer
        properties["YRKGR_LVL_1_ID"] = keyword
        properties["YRKGR_LVL_2_ID"] = keyword

        ledige["properties"] = properties
        jsonMap["arbeidsledige"] = ledige

        request.mapping("arbeidsledige", jsonMap)

        client.indices().create(request)
    }

    override fun createStillingerIndex() {
        val request = CreateIndexRequest("ledigestillinger")
        val properties = HashMap<String, Any>()
        val ledige = HashMap<String, Any>()
        val jsonMap = HashMap<String, Any>()

        properties["PERIODE"] = keyword
        properties["FYLKESNR"] = keyword
        properties["KOMMUNENR"] = keyword
        properties["YRKESKODE"] = keyword
        properties["LEDIGE_STILLINGER"] = integer
        properties["YRKGR_LVL_1_ID"] = keyword
        properties["YRKGR_LVL_2_ID"] = keyword

        ledige["properties"] = properties
        jsonMap["ledigestillinger"]

        request.mapping("ledigestillinger", jsonMap)

        client.indices().create(request)
    }

    override fun getAll(): String {
        val response = client.lowLevelClient.performRequest("GET", "/_cat/indices?v")
        return EntityUtils.toString(response.entity)
    }

}


private val keyword = hashMapOf<String, Any>("type" to "keyword")
private val integer = hashMapOf<String, Any>("type" to "integer")
