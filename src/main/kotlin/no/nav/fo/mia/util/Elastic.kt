package no.nav.fo.mia.util

const val periode = "PERIODE"
const val fylkesnummer = "FYLKESNR"
const val komunenummer = "KOMMUNENR"
const val yrkeskode = "YRKESKODE"
const val arbeidledige = "ARBEIDSLEDIGE"
const val yrkesgruppe_lvl_1 = "YRKGR_LVL_1_ID"
const val yrkesgruppe_lvl_2 = "YRKGR_LVL_2_ID"
const val ledigeStillinger = "LEDIGE_STILLINGER"

const val arbeidsledigeIndex = "arbeidsledige"
const val stillingerIndex = "ledigestillinger"

const val doc = "doc"
const val propertys = "properties"


val ledigeProp = arrayOf(periode, fylkesnummer, komunenummer, yrkeskode, ledigeStillinger, yrkesgruppe_lvl_1, yrkesgruppe_lvl_2)
val stilingerProp = arrayOf(periode, fylkesnummer, komunenummer, yrkeskode, arbeidledige, yrkesgruppe_lvl_1, yrkesgruppe_lvl_2)

val indexMap = mapOf(arbeidsledigeIndex to ledigeProp, stillingerIndex to stilingerProp)
