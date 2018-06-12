package no.nav.fo.mia.service

import no.nav.fo.mia.provider.ElasticIndexProvider
import no.nav.fo.mia.util.doc
import no.nav.fo.mia.util.indexMap
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.common.inject.Inject
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*
import kotlin.system.measureTimeMillis

@Service
class IndeksererService @Inject
constructor(
        private val bransjeMappingService: SolrBransjeMappingService,
        private val elastic: ElasticIndexProvider
) {
    private val LOGGER = LoggerFactory.getLogger(IndeksererService::class.java)
    fun recreatIndex(stream: InputStream, index: String): String{
        val bulk = BulkRequest()
        val headers = indexMap[index]!!

        LOGGER.info("rteading inputdata for reindex")
        val allLines = stream
                .reader()
                .readLines()

        LOGGER.info("remapping data")

        val requests = allLines
                .subList(1, allLines.size) //fjerner header linjen
                .map(this::getValues)
                .map {
                    headers
                            .zip(it)
                            .toMap()
                }
                .map { IndexRequest(index, doc).source(it) }

        bulk.add(requests)


        val time = measureTimeMillis {    //TODO fiks denne til å vere nedetidsfri og tryggere (17 sec på mac med stor fil)
            LOGGER.info("Ddeleting and creting index")
            elastic.recreateIndex(index)
            LOGGER.info("indexing")
            elastic.index(bulk)
        }
        LOGGER.info("Indexing took $time")

        return ":) $time"
    }

    private fun getValues(csvString : String): List<Any> {
        val cvsSplitBy = ","
        val cvsValues = csvString
                .replace("\"", "")
                .split(cvsSplitBy.toRegex())

        val yrkesgrupper = bransjeMappingService.getYrkesgruperForStrukturKode(cvsValues[3])

        val yrkersomrader = yrkesgrupper
                .flatMap(bransjeMappingService::getYrkesomradeForYrkesgruppe)
                .distinct()

        val values = LinkedList<Any>()
        values.addAll(cvsValues)
        values.add(if(yrkersomrader.isEmpty()) "-2" else yrkersomrader)
        values.add(if(yrkesgrupper.isEmpty()) "-2" else yrkesgrupper)

        return values
    }

    fun getAll(): Any = elastic.getAll()

}
