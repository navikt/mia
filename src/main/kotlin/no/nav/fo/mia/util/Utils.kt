package no.nav.fo.mia.util

fun getOptionalProperty(propName: String): String? =
        System.getenv(propName) ?: System.getProperty(propName)

fun getRequiredProperty(propName: String) =
        getOptionalProperty(propName) ?: throw IllegalArgumentException("Missing required property: $propName")

fun setPropertyIfNotAlredyExists(propName: String, propValue: String) {
    if(getOptionalProperty(propName) == null) {
        System.setProperty(propName, propValue)
    }
}

fun setupTruststore() {
    val truststore = "javax.net.ssl.trustStore"
    val truststorepassword = "javax.net.ssl.trustStorePassword"

    getOptionalProperty("NAV_TRUSTSTORE_PATH")?.let { System.setProperty(truststore, it) }
    getOptionalProperty("NAV_TRUSTSTORE_PASSWORD")?.let { System.setProperty(truststorepassword, it) }
}

fun sensurerData(statistik: Map<String, Int>): Map<String, Int?> =
        statistik.map { Pair(it.key, if (it.value > 4) it.value else null) }.toMap()
