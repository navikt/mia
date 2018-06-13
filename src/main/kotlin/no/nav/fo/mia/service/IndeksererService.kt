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
    fun recreatIndex(stream: InputStream, index: String): String {

        val headers = indexMap[index]!!

        LOGGER.info("recreating index")
        val allLines = stream
                .reader()
                .readLines()

        val lines = allLines
                .subList(1, allLines.size) //fjerner header linjen

        elastic.recreateIndex(index)


        val time = measureTimeMillis {

            var bulk = BulkRequest()

            lines.forEach {
                val values = getValues(it)
                val content = headers.zip(values).toMap()

                val request = IndexRequest(index, doc).source(content)
                bulk.add(request)

                if (bulk.numberOfActions() > 10000) {
                    elastic.index(bulk)
                    bulk = BulkRequest()
                }
            }

            elastic.index(bulk)

            LOGGER.info("totalt indekserte ${lines.size}")
        }
        LOGGER.info("time: $time")


        return "indexen $index er lagd på nytt, antall indekserte: ${lines.size} på $time ms :)"
    }


    private fun getValues(csvString: String): List<Any> {
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
        values.add(if (yrkersomrader.isEmpty()) "-2" else yrkersomrader)
        values.add(if (yrkesgrupper.isEmpty()) "-2" else yrkesgrupper)

        return values
    }

    fun getAll(): Any = elastic.getAllIndexes()

}
