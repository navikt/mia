package no.nav.fo.mia.service

import no.nav.fo.mia.provider.ElasticIndexProvider
import no.nav.fo.mia.util.ElasticConstants.Companion.doc
import no.nav.fo.mia.util.ElasticConstants.Companion.filHeaderMap
import no.nav.fo.mia.util.ElasticConstants.Companion.indexMap
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
    fun recreatIndex(stream: InputStream, alias: String): String {


        val headers = indexMap[alias]!!

        LOGGER.info("Starter indeksering av $alias")

        val lines = getContentLines(stream, alias)

        val indexName = elastic.createIndexForAlias(alias)

        val time = measureTimeMillis {
            var bulk = BulkRequest()

            lines.forEach {
                val values = getValues(it)
                val content = headers.zip(values).toMap()

                val request = IndexRequest(indexName, doc).source(content)
                bulk.add(request)

                if (bulk.numberOfActions() > 10000) {
                    elastic.index(bulk)
                    bulk = BulkRequest()
                }
            }

            elastic.index(bulk)
        }
        LOGGER.info("Indekserte ${lines.size} dokumenter over $time ms")
        elastic.replaceIndexForAlias(alias = alias, nyIndex = indexName)
        LOGGER.info("Alias: $alias er erstattet av index: $indexName")
        return "Indexen $alias er lagd på nytt, antall indekserte: ${lines.size} på $time ms."
    }

    private fun getContentLines(stream: InputStream, alias: String): List<String> {
        val linesWithHeader = stream
                .reader()
                .readLines()

        if (linesWithHeader.first() != filHeaderMap[alias])
            throw IllegalArgumentException("fil header skal være ${filHeaderMap[alias]} den er: ${linesWithHeader.first()}")

        val lines = linesWithHeader
                .drop(1) // fjerned header-linjen
                .filter(String::isNotEmpty)
        return lines
    }

    private fun getValues(csvString: String): List<Any> {
        val cvsSplitBy = ",".toRegex()
        val cvsValues = csvString
                .replace("\"", "")
                .replace(" ", "")
                .split(cvsSplitBy)

        vallidatecsvVaslues(cvsValues)

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

    private fun vallidatecsvVaslues(csvValues: List<String>) {
        val utlandet = "UT".toRegex()

        if (csvValues.size != 5)
            throw IllegalArgumentException()

        csvValues[0].toInt()

        if (!utlandet.matches(csvValues[1])) {
            csvValues[1].toInt()
            csvValues[2].toInt()
        }

        csvValues[3].toDouble()
        csvValues[4].toInt()

    }

    fun getAll() = elastic.getAllIndexes()
    fun info() = elastic.getCluseterInfo()

}
