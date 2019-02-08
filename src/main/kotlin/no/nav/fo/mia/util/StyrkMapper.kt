package no.nav.fo.mia.util

val hovedkategoriTIlunderkategori = mapOf(
        "Uoppgitt/ ikke identifiserbare" to listOf("Ikke identifiserbare"),
        "Sikkerhet og beredskap" to listOf("Forsvar/militære", "Brann-, utryknings- og redningspersonell", "Politi, fengsel og toll", "Vakt-, sikrings- og kontrollarbeid"),
        "Kontor og økonomi" to listOf("Organisasjonsarbeid og politikk", "Ledelse, administrasjon og rådgivning", "Bank finans forsikring", "Personal, arbeidsmiljø og rekruttering", "økonomi, statistikk og regnskap", "Kontor, forvaltning og saksbehandling", "Juridisk arbeid", "Megling"),
        "Salg og service" to listOf("Salg, butikk- og varehandel", "Markedsføring og reklame", "Personlig tjenesteyting", "Tolk og oversettere", "Eiendomsmegler", "Post og måleavlesere", "Frisør og velvære", "Renholdere og renovasjon"),
        "Utdanning" to listOf("Forskningsarbeid", "Ledere av undervisning og utdanning", "Universitet og høyskole", "Videregående skole", "Barnehage og grunnskole", "Instruktører og pedagoger"),
        "Natur og miljø" to listOf("Skogbruk, gartnerarbeid og hagebruk", "Naturvitenskapelige yrker", "Biologer, zoologer, botanikere", "Jordbruk og dyrehold", "Miljøvern", "Veterinærer og dyrepleiere", "Matproduksjon og næringsmiddelarbeid", "Fiske, fangst og oppdrett", "Fiske, fangst og oppdrett"),
        "Industri og produksjon" to listOf("Arbeidsleder, industri", "Olje, gass og bergverk", "Fysikk, kjemi og metallurgi", "Maskinteknikk og mekanikk", "Elektro/elektronikk", "Jern og metall", "Matproduksjon og næringsmiddelarbeid", "Trevarearbeid og –foredling", "Tekstil og presisjonsarbeid", "Andre hjelpearbeidere"),
        "Bygg og anlegg" to listOf("Arbeidsleder, bygg og anlegg", "Ingeniør bygg", "Ingeniør miljøteknikk", "Arealplanlegging og arkitektur", "Skytebaser og sprengningsarbeidere", "Hjelpearbeider Bygg og anlegg", "Maskin- og kranfører"),
        "Transport og lager" to listOf("Logistikk, lagerarbeid og innkjøp", "Sjøfart", "Lufttrafikk", "Speditør og befrakter", "Tog-, sporvogn- og vegtrafikk", "Truckfører"),
        "IT" to listOf("Ledere av IKT-enheter", "Interaksjonsdesign", "Utvikling", "Drift, vedlikehold"),
        "Helse og sosial" to listOf("Leder innenfor helsetjenester", "Leger, psykologer og terapeuter", "Sykepleier", "Helse", "Tannhelse/-pleie", "Farmasøytisk og apotekerarbeid", "Sosial", "Bioingeniør", "Optiker"),
        "Reiseliv og mat" to listOf("Reiseliv, hotell og overnatting", "Restaurant", "Kokk", "Baker og konditor"),
        "Kultur og kreative yrker" to listOf("Fritid", "Design, grafisk arbeid og illustrasjon", "Journalistikk og litteratur", "Museum, bibliotek", "Religiøst arbeid", "Kunst og kunsthåndverk", "Musikk/lyd, foto og video", "Underholdning, scene og teater", "Sport og idrett", "Andre yrker innen estetiske fag", "Reklame og dekorasjon"),
        "Utdanning" to listOf("Forskningsarbeid", "Instruktører og pedagoger", "SFO og fritidsleder"),
        "Håndverkere" to listOf("Elektriker/elektro", "Vaktmester", "øvrige håndverksyrker", "Murer", "Betongfagarbeider", "Tømrer og snekker", "Maler", "Rørlegger", "Feier", "Platearbeider og sveiser", "Mekaniker", "Skredder"))

val styrkTilUnderkategori = mapOf(
    "0" to "Ikke identifiserbare",
    "110" to "Forsvar/militære",
    "210" to "Forsvar/militære",
    "310" to "Forsvar/militære",
    "1111" to "Organisasjonsarbeid og politikk",
    "1112" to "Ledelse, administrasjon og rådgivning",
    "1114" to "Organisasjonsarbeid og politikk",
    "1120" to "Ledelse, administrasjon og rådgivning",
    "1211" to "Bank finans forsikring",
    "1212" to "Personal, arbeidsmiljø og rekruttering",
    "1213" to "Ledelse, administrasjon og rådgivning",
    "1219" to "Ledelse, administrasjon og rådgivning",
    "1221" to "Salg, butikk- og varehandel",
    "1222" to "Markedsføring og reklame",
    "1223" to "Forskningsarbeid",
    "1311" to "Skogbruk, gartnerarbeid og hagebruk",
    "1312" to "Fiske, fangst og oppdrett",
    "1321" to "Arbeidsleder, industri",
    "1322" to "Olje, gass og bergverk",
    "1323" to "Arbeidsleder, bygg og anlegg",
    "1324" to "Logistikk, lagerarbeid og innkjøp",
    "1330" to "Ledere av IKT-enheter",
    "1341" to "Leder innenfor helsetjenester",
    "1342" to "Leder innenfor helsetjenester",
    "1343" to "Leder innenfor helsetjenester",
    "1344" to "Leder innenfor helsetjenester",
    "1345" to "Ledere av undervisning og utdanning",
    "1346" to "Bank finans forsikring",
    "1349" to "Personlig tjenesteyting",
    "1411" to "Reiseliv, hotell og overnatting",
    "1412" to "Restaurant",
    "1420" to "Salg, butikk- og varehandel",
    "1431" to "Fritid",
    "1439" to "Personlig tjenesteyting",
    "2111" to "Fysikk, kjemi og metallurgi",
    "2112" to "Naturvitenskapelige yrker",
    "2113" to "Fysikk, kjemi og metallurgi",
    "2114" to "Naturvitenskapelige yrker",
    "2120" to "økonomi, statistikk og regnskap",
    "2131" to "Biologer, zoologer, botanikere",
    "2132" to "Jordbruk og dyrehold",
    "2133" to "Miljøvern",
    "2141" to "Fysikk, kjemi og metallurgi",
    "2142" to "Ingeniør bygg",
    "2143" to "Ingeniør miljøteknikk",
    "2144" to "Maskinteknikk og mekanikk",
    "2145" to "Fysikk, kjemi og metallurgi",
    "2146" to "Olje, gass og bergverk",
    "2149" to "Forskningsarbeid",
    "2151" to "Elektro/elektronikk",
    "2152" to "Elektro/elektronikk",
    "2153" to "Elektro/elektronikk",
    "2161" to "Arealplanlegging og arkitektur",
    "2162" to "Arealplanlegging og arkitektur",
    "2163" to "Design, grafisk arbeid og illustrasjon",
    "2164" to "Arealplanlegging og arkitektur",
    "2165" to "Arealplanlegging og arkitektur",
    "2166" to "Interaksjonsdesign",
    "2211" to "Leger, psykologer og terapeuter",
    "2212" to "Leger, psykologer og terapeuter",
    "2221" to "Sykepleier",
    "2222" to "Sykepleier",
    "2223" to "Sykepleier",
    "2224" to "Helse",
    "2250" to "Veterinærer og dyrepleiere",
    "2261" to "Tannhelse/-pleie",
    "2262" to "Farmasøytisk og apotekerarbeid",
    "2263" to "Helse",
    "2264" to "Leger, psykologer og terapeuter",
    "2265" to "Leger, psykologer og terapeuter",
    "2266" to "Leger, psykologer og terapeuter",
    "2267" to "Leger, psykologer og terapeuter",
    "2269" to "Leger, psykologer og terapeuter",
    "2310" to "Universitet og høyskole",
    "2320" to "Videregående skole",
    "2330" to "Videregående skole",
    "2341" to "Barnehage og grunnskole",
    "2342" to "Barnehage og grunnskole",
    "2351" to "Instruktører og pedagoger",
    "2352" to "Instruktører og pedagoger",
    "2353" to "Barnehage og grunnskole",
    "2354" to "Barnehage og grunnskole",
    "2355" to "Barnehage og grunnskole",
    "2356" to "Barnehage og grunnskole",
    "2359" to "Barnehage og grunnskole",
    "2411" to "økonomi, statistikk og regnskap",
    "2412" to "Bank finans forsikring",
    "2413" to "Bank finans forsikring",
    "2421" to "Personal, arbeidsmiljø og rekruttering",
    "2422" to "Kontor, forvaltning og saksbehandling",
    "2423" to "Personal, arbeidsmiljø og rekruttering",
    "2424" to "Personal, arbeidsmiljø og rekruttering",
    "2431" to "Markedsføring og reklame",
    "2432" to "Journalistikk og litteratur",
    "2433" to "Salg, butikk- og varehandel",
    "2434" to "Salg, butikk- og varehandel",
    "2511" to "Utvikling",
    "2512" to "Utvikling",
    "2513" to "Utvikling",
    "2514" to "Utvikling",
    "2519" to "Utvikling",
    "2521" to "Drift, vedlikehold",
    "2522" to "Drift, vedlikehold",
    "2523" to "Drift, vedlikehold",
    "2529" to "Utvikling",
    "2611" to "Juridisk arbeid",
    "2612" to "Juridisk arbeid",
    "2619" to "Juridisk arbeid",
    "2621" to "Museum, bibliotek",
    "2622" to "Museum, bibliotek",
    "2631" to "Forskningsarbeid",
    "2632" to "Forskningsarbeid",
    "2633" to "Forskningsarbeid",
    "2634" to "Leger, psykologer og terapeuter",
    "2635" to "Sosial",
    "2636" to "Religiøst arbeid",
    "2641" to "Journalistikk og litteratur",
    "2642" to "Journalistikk og litteratur",
    "2643" to "Tolk og oversettere",
    "2651" to "Kunst og kunsthåndverk",
    "2652" to "Musikk/lyd, foto og video",
    "2653" to "Underholdning, scene og teater",
    "2654" to "Underholdning, scene og teater",
    "2655" to "Underholdning, scene og teater",
    "2656" to "Underholdning, scene og teater",
    "2659" to "Underholdning, scene og teater",
    "3112" to "Ingeniør bygg",
    "3113" to "Elektro/elektronikk",
    "3114" to "Elektro/elektronikk",
    "3115" to "Maskinteknikk og mekanikk",
    "3116" to "Fysikk, kjemi og metallurgi",
    "3117" to "Olje, gass og bergverk",
    "3118" to "Maskinteknikk og mekanikk",
    "3119" to "Forskningsarbeid",
    "3121" to "Olje, gass og bergverk",
    "3122" to "Arbeidsleder, industri",
    "3123" to "Arbeidsleder, bygg og anlegg",
    "3131" to "Elektro/elektronikk",
    "3132" to "Maskinteknikk og mekanikk",
    "3133" to "Fysikk, kjemi og metallurgi",
    "3134" to "Olje, gass og bergverk",
    "3135" to "Jern og metall",
    "3139" to "Fysikk, kjemi og metallurgi",
    "3141" to "Biologer, zoologer, botanikere",
    "3142" to "Jordbruk og dyrehold",
    "3143" to "Skogbruk, gartnerarbeid og hagebruk",
    "3151" to "Sjøfart",
    "3152" to "Sjøfart",
    "3153" to "Lufttrafikk",
    "3154" to "Lufttrafikk",
    "3155" to "Lufttrafikk",
    "3211" to "Leger, psykologer og terapeuter",
    "3212" to "Bioingeniør",
    "3213" to "Farmasøytisk og apotekerarbeid",
    "3214" to "Tannhelse/-pleie",
    "3230" to "Leger, psykologer og terapeuter",
    "3240" to "Veterinærer og dyrepleiere",
    "3251" to "Tannhelse/-pleie",
    "3254" to "Optiker",
    "3256" to "Helse",
    "3257" to "Matproduksjon og næringsmiddelarbeid",
    "3258" to "Brann-, utryknings- og redningspersonell",
    "3259" to "Helse",
    "3311" to "Megling",
    "3312" to "Bank finans forsikring",
    "3313" to "økonomi, statistikk og regnskap",
    "3315" to "Bank finans forsikring",
    "3321" to "Bank finans forsikring",
    "3322" to "Salg, butikk- og varehandel",
    "3323" to "Salg, butikk- og varehandel",
    "3324" to "Megling",
    "3331" to "Speditør og befrakter",
    "3332" to "Reiseliv, hotell og overnatting",
    "3333" to "Personal, arbeidsmiljø og rekruttering",
    "3334" to "Eiendomsmegler",
    "3339" to "Megling",
    "3341" to "Ledelse, administrasjon og rådgivning",
    "3342" to "Juridisk arbeid",
    "3343" to "Kontor, forvaltning og saksbehandling",
    "3351" to "Politi, fengsel og toll",
    "3352" to "Kontor, forvaltning og saksbehandling",
    "3353" to "Kontor, forvaltning og saksbehandling",
    "3354" to "Kontor, forvaltning og saksbehandling",
    "3355" to "Politi, fengsel og toll",
    "3359" to "Kontor, forvaltning og saksbehandling",
    "3411" to "Vakt-, sikrings- og kontrollarbeid",
    "3412" to "SFO og fritidsleder",
    "3413" to "Religiøst arbeid",
    "3421" to "Sport og idrett",
    "3422" to "Sport og idrett",
    "3423" to "Sport og idrett",
    "3431" to "Musikk/lyd, foto og video",
    "3432" to "Design, grafisk arbeid og illustrasjon",
    "3433" to "Museum, bibliotek",
    "3434" to "Kokk",
    "3439" to "Andre yrker innen estetiske fag",
    "3511" to "Drift, vedlikehold",
    "3512" to "Drift, vedlikehold",
    "3513" to "Drift, vedlikehold",
    "3514" to "Drift, vedlikehold",
    "3521" to "Elektriker/elektro",
    "3522" to "Elektriker/elektro",
    "4110" to "Kontor, forvaltning og saksbehandling",
    "4131" to "Kontor, forvaltning og saksbehandling",
    "4132" to "Kontor, forvaltning og saksbehandling",
    "4211" to "Bank finans forsikring",
    "4212" to "Salg, butikk- og varehandel",
    "4213" to "Salg, butikk- og varehandel",
    "4214" to "Kontor, forvaltning og saksbehandling",
    "4221" to "Reiseliv, hotell og overnatting",
    "4222" to "Kontor, forvaltning og saksbehandling",
    "4223" to "Kontor, forvaltning og saksbehandling",
    "4224" to "Reiseliv, hotell og overnatting",
    "4225" to "Kontor, forvaltning og saksbehandling",
    "4226" to "Kontor, forvaltning og saksbehandling",
    "4227" to "Kontor, forvaltning og saksbehandling",
    "4229" to "Salg, butikk- og varehandel",
    "4311" to "økonomi, statistikk og regnskap",
    "4312" to "Bank finans forsikring",
    "4313" to "økonomi, statistikk og regnskap",
    "4321" to "Logistikk, lagerarbeid og innkjøp",
    "4322" to "Logistikk, lagerarbeid og innkjøp",
    "4323" to "Logistikk, lagerarbeid og innkjøp",
    "4411" to "Museum, bibliotek",
    "4412" to "Post og måleavlesere",
    "4413" to "Kontor, forvaltning og saksbehandling",
    "4415" to "Kontor, forvaltning og saksbehandling",
    "4416" to "Personal, arbeidsmiljø og rekruttering",
    "5111" to "Reiseliv, hotell og overnatting",
    "5112" to "Reiseliv, hotell og overnatting",
    "5113" to "Reiseliv, hotell og overnatting",
    "5120" to "Kokk",
    "5131" to "Restaurant",
    "5132" to "Restaurant",
    "5141" to "Frisør og velvære",
    "5142" to "Frisør og velvære",
    "5151" to "Renholdere og renovasjon",
    "5152" to "Renholdere og renovasjon",
    "5153" to "Vaktmester",
    "5161" to "Personlig tjenesteyting",
    "5163" to "øvrige håndverksyrker",
    "5164" to "Veterinærer og dyrepleiere",
    "5165" to "Instruktører og pedagoger",
    "5169" to "Personlig tjenesteyting",
    "5211" to "Salg, butikk- og varehandel",
    "5212" to "Salg, butikk- og varehandel",
    "5221" to "Salg, butikk- og varehandel",
    "5222" to "Salg, butikk- og varehandel",
    "5223" to "Salg, butikk- og varehandel",
    "5230" to "Salg, butikk- og varehandel",
    "5241" to "Reklame og dekorasjon",
    "5242" to "Salg, butikk- og varehandel",
    "5243" to "Salg, butikk- og varehandel",
    "5244" to "Salg, butikk- og varehandel",
    "5245" to "Salg, butikk- og varehandel",
    "5246" to "Salg, butikk- og varehandel",
    "5249" to "Salg, butikk- og varehandel",
    "5311" to "Barnehage og grunnskole",
    "5312" to "Barnehage og grunnskole",
    "5321" to "Helse",
    "5322" to "Helse",
    "5329" to "Helse",
    "5411" to "Brann-, utryknings- og redningspersonell",
    "5413" to "Politi, fengsel og toll",
    "5414" to "Vakt-, sikrings- og kontrollarbeid",
    "5419" to "Vakt-, sikrings- og kontrollarbeid",
    "6111" to "Skogbruk, gartnerarbeid og hagebruk",
    "6112" to "Skogbruk, gartnerarbeid og hagebruk",
    "6113" to "Skogbruk, gartnerarbeid og hagebruk",
    "6114" to "Skogbruk, gartnerarbeid og hagebruk",
    "6121" to "Jordbruk og dyrehold",
    "6122" to "Jordbruk og dyrehold",
    "6123" to "Jordbruk og dyrehold",
    "6129" to "Jordbruk og dyrehold",
    "6130" to "Jordbruk og dyrehold",
    "6210" to "Skogbruk, gartnerarbeid og hagebruk",
    "6221" to "Fiske, fangst og oppdrett",
    "6222" to "Fiske, fangst og oppdrett",
    "6224" to "Fiske, fangst og oppdrett",
    "7112" to "Murer",
    "7113" to "øvrige håndverksyrker",
    "7114" to "Betongfagarbeider",
    "7115" to "Tømrer og snekker",
    "7119" to "øvrige håndverksyrker",
    "7121" to "øvrige håndverksyrker",
    "7122" to "Murer",
    "7123" to "Maler",
    "7124" to "øvrige håndverksyrker",
    "7125" to "øvrige håndverksyrker",
    "7126" to "Rørlegger",
    "7127" to "Rørlegger",
    "7131" to "Maler",
    "7132" to "Maler",
    "7133" to "Feier",
    "7211" to "øvrige håndverksyrker",
    "7212" to "Platearbeider og sveiser",
    "7213" to "øvrige håndverksyrker",
    "7214" to "Platearbeider og sveiser",
    "7215" to "øvrige håndverksyrker",
    "7221" to "Jern og metall",
    "7222" to "øvrige håndverksyrker",
    "7223" to "Maskinteknikk og mekanikk",
    "7224" to "Maskinteknikk og mekanikk",
    "7231" to "Mekaniker",
    "7232" to "Mekaniker",
    "7233" to "Maskinteknikk og mekanikk",
    "7234" to "Mekaniker",
    "7311" to "øvrige håndverksyrker",
    "7312" to "øvrige håndverksyrker",
    "7313" to "øvrige håndverksyrker",
    "7314" to "Kunst og kunsthåndverk",
    "7315" to "Kunst og kunsthåndverk",
    "7316" to "Kunst og kunsthåndverk",
    "7317" to "Kunst og kunsthåndverk",
    "7318" to "Kunst og kunsthåndverk",
    "7319" to "Kunst og kunsthåndverk",
    "7321" to "Design, grafisk arbeid og illustrasjon",
    "7322" to "Design, grafisk arbeid og illustrasjon",
    "7323" to "Design, grafisk arbeid og illustrasjon",
    "7411" to "Elektriker/elektro",
    "7412" to "Elektriker/elektro",
    "7413" to "Elektriker/elektro",
    "7421" to "Elektriker/elektro",
    "7422" to "Elektriker/elektro",
    "7511" to "Matproduksjon og næringsmiddelarbeid",
    "7512" to "Baker og konditor",
    "7513" to "Matproduksjon og næringsmiddelarbeid",
    "7514" to "Matproduksjon og næringsmiddelarbeid",
    "7515" to "Matproduksjon og næringsmiddelarbeid",
    "7522" to "Trevarearbeid og –foredling",
    "7531" to "Skredder",
    "7532" to "Tekstil og presisjonsarbeid",
    "7534" to "Tekstil og presisjonsarbeid",
    "7535" to "Tekstil og presisjonsarbeid",
    "7536" to "Tekstil og presisjonsarbeid",
    "7541" to "Brann-, utryknings- og redningspersonell",
    "7542" to "Skytebaser og sprengningsarbeidere",
    "7543" to "øvrige håndverksyrker",
    "7544" to "øvrige håndverksyrker",
    "7549" to "øvrige håndverksyrker",
    "8111" to "Olje, gass og bergverk",
    "8112" to "Olje, gass og bergverk",
    "8113" to "Olje, gass og bergverk",
    "8114" to "Betongfagarbeider",
    "8121" to "Fysikk, kjemi og metallurgi",
    "8122" to "Fysikk, kjemi og metallurgi",
    "8131" to "Fysikk, kjemi og metallurgi",
    "8132" to "Fysikk, kjemi og metallurgi",
    "8141" to "Fysikk, kjemi og metallurgi",
    "8142" to "Fysikk, kjemi og metallurgi",
    "8143" to "Tekstil og presisjonsarbeid",
    "8151" to "Tekstil og presisjonsarbeid",
    "8152" to "Tekstil og presisjonsarbeid",
    "8153" to "Tekstil og presisjonsarbeid",
    "8154" to "Tekstil og presisjonsarbeid",
    "8155" to "Tekstil og presisjonsarbeid",
    "8156" to "Tekstil og presisjonsarbeid",
    "8157" to "Tekstil og presisjonsarbeid",
    "8159" to "Tekstil og presisjonsarbeid",
    "8160" to "Matproduksjon og næringsmiddelarbeid",
    "8171" to "Trevarearbeid og –foredling",
    "8172" to "Trevarearbeid og –foredling",
    "8181" to "Maskinteknikk og mekanikk",
    "8182" to "Maskinteknikk og mekanikk",
    "8183" to "Maskinteknikk og mekanikk",
    "8189" to "Maskinteknikk og mekanikk",
    "8211" to "Maskinteknikk og mekanikk",
    "8212" to "Elektro/elektronikk",
    "8219" to "Hjelpearbeider Bygg og anlegg",
    "8311" to "Tog-, sporvogn- og vegtrafikk",
    "8312" to "Tog-, sporvogn- og vegtrafikk",
    "8322" to "Tog-, sporvogn- og vegtrafikk",
    "8331" to "Tog-, sporvogn- og vegtrafikk",
    "8332" to "Tog-, sporvogn- og vegtrafikk",
    "8341" to "Maskin- og kranfører",
    "8342" to "Maskin- og kranfører",
    "8343" to "Maskin- og kranfører",
    "8344" to "Truckfører",
    "8350" to "Sjøfart",
    "9111" to "Renholdere og renovasjon",
    "9112" to "Renholdere og renovasjon",
    "9122" to "Renholdere og renovasjon",
    "9123" to "Renholdere og renovasjon",
    "9129" to "Renholdere og renovasjon",
    "9211" to "Skogbruk, gartnerarbeid og hagebruk",
    "9212" to "Jordbruk og dyrehold",
    "9213" to "Jordbruk og dyrehold",
    "9214" to "Skogbruk, gartnerarbeid og hagebruk",
    "9215" to "Skogbruk, gartnerarbeid og hagebruk",
    "9216" to "Fiske, fangst og oppdrett",
    "9311" to "Olje, gass og bergverk",
    "9312" to "Hjelpearbeider Bygg og anlegg",
    "9313" to "Hjelpearbeider Bygg og anlegg",
    "9321" to "Logistikk, lagerarbeid og innkjøp",
    "9329" to "Andre hjelpearbeidere",
    "9331" to "Logistikk, lagerarbeid og innkjøp",
    "9333" to "Logistikk, lagerarbeid og innkjøp",
    "9334" to "Salg, butikk- og varehandel",
    "9412" to "Reiseliv, hotell og overnatting",
    "9510" to "Markedsføring og reklame",
    "9611" to "Renholdere og renovasjon",
    "9612" to "Renholdere og renovasjon",
    "9613" to "Renholdere og renovasjon",
    "9621" to "Speditør og befrakter",
    "9622" to "Vaktmester",
    "9623" to "Post og måleavlesere",
    "9629" to "øvrige håndverksyrker"
)

val styrkTilHovedkategori = mapOf(
        "0" to "Uoppgitt/ ikke identifiserbare",
        "110" to "Sikkerhet og beredskap",
        "210" to "Sikkerhet og beredskap",
        "310" to "Sikkerhet og beredskap",
        "1111" to "Kontor og økonomi",
        "1112" to "Kontor og økonomi",
        "1114" to "Kontor og økonomi",
        "1120" to "Kontor og økonomi",
        "1211" to "Kontor og økonomi",
        "1212" to "Kontor og økonomi",
        "1213" to "Kontor og økonomi",
        "1219" to "Kontor og økonomi",
        "1221" to "Salg og service",
        "1222" to "Salg og service",
        "1223" to "Utdanning",
        "1311" to "Natur og miljø",
        "1312" to "Natur og miljø",
        "1321" to "Industri og produksjon",
        "1322" to "Industri og produksjon",
        "1323" to "Bygg og anlegg",
        "1324" to "Transport og lager",
        "1330" to "IT",
        "1341" to "Helse og sosial",
        "1342" to "Helse og sosial",
        "1343" to "Helse og sosial",
        "1344" to "Helse og sosial",
        "1345" to "Utdanning",
        "1346" to "Kontor og økonomi",
        "1349" to "Salg og service",
        "1411" to "Reiseliv og mat",
        "1412" to "Reiseliv og mat",
        "1420" to "Salg og service",
        "1431" to "Kultur og kreative yrker",
        "1439" to "Salg og service",
        "2111" to "Industri og produksjon",
        "2112" to "Natur og miljø",
        "2113" to "Industri og produksjon",
        "2114" to "Natur og miljø",
        "2120" to "Kontor og økonomi",
        "2131" to "Natur og miljø",
        "2132" to "Natur og miljø",
        "2133" to "Natur og miljø",
        "2141" to "Industri og produksjon",
        "2142" to "Bygg og anlegg",
        "2143" to "Bygg og anlegg",
        "2144" to "Industri og produksjon",
        "2145" to "Industri og produksjon",
        "2146" to "Industri og produksjon",
        "2149" to "Utdanning",
        "2151" to "Industri og produksjon",
        "2152" to "Industri og produksjon",
        "2153" to "Industri og produksjon",
        "2161" to "Bygg og anlegg",
        "2162" to "Bygg og anlegg",
        "2163" to "Kultur og kreative yrker",
        "2164" to "Bygg og anlegg",
        "2165" to "Bygg og anlegg",
        "2166" to "IT",
        "2211" to "Helse og sosial",
        "2212" to "Helse og sosial",
        "2221" to "Helse og sosial",
        "2222" to "Helse og sosial",
        "2223" to "Helse og sosial",
        "2224" to "Helse og sosial",
        "2250" to "Natur og miljø",
        "2261" to "Helse og sosial",
        "2262" to "Helse og sosial",
        "2263" to "Helse og sosial",
        "2264" to "Helse og sosial",
        "2265" to "Helse og sosial",
        "2266" to "Helse og sosial",
        "2267" to "Helse og sosial",
        "2269" to "Helse og sosial",
        "2310" to "Utdanning",
        "2320" to "Utdanning",
        "2330" to "Utdanning",
        "2341" to "Utdanning",
        "2342" to "Utdanning",
        "2351" to "Utdanning",
        "2352" to "Utdanning",
        "2353" to "Utdanning",
        "2354" to "Utdanning",
        "2355" to "Utdanning",
        "2356" to "Utdanning",
        "2359" to "Utdanning",
        "2411" to "Kontor og økonomi",
        "2412" to "Kontor og økonomi",
        "2413" to "Kontor og økonomi",
        "2421" to "Kontor og økonomi",
        "2422" to "Kontor og økonomi",
        "2423" to "Kontor og økonomi",
        "2424" to "Kontor og økonomi",
        "2431" to "Salg og service",
        "2432" to "Kultur og kreative yrker",
        "2433" to "Salg og service",
        "2434" to "Salg og service",
        "2511" to "IT",
        "2512" to "IT",
        "2513" to "IT",
        "2514" to "IT",
        "2519" to "IT",
        "2521" to "IT",
        "2522" to "IT",
        "2523" to "IT",
        "2529" to "IT",
        "2611" to "Kontor og økonomi",
        "2612" to "Kontor og økonomi",
        "2619" to "Kontor og økonomi",
        "2621" to "Kultur og kreative yrker",
        "2622" to "Kultur og kreative yrker",
        "2631" to "Utdanning",
        "2632" to "Utdanning",
        "2633" to "Utdanning",
        "2634" to "Helse og sosial",
        "2635" to "Helse og sosial",
        "2636" to "Kultur og kreative yrker",
        "2641" to "Kultur og kreative yrker",
        "2642" to "Kultur og kreative yrker",
        "2643" to "Salg og service",
        "2651" to "Kultur og kreative yrker",
        "2652" to "Kultur og kreative yrker",
        "2653" to "Kultur og kreative yrker",
        "2654" to "Kultur og kreative yrker",
        "2655" to "Kultur og kreative yrker",
        "2656" to "Kultur og kreative yrker",
        "2659" to "Kultur og kreative yrker",
        "3112" to "Bygg og anlegg",
        "3113" to "Industri og produksjon",
        "3114" to "Industri og produksjon",
        "3115" to "Industri og produksjon",
        "3116" to "Industri og produksjon",
        "3117" to "Industri og produksjon",
        "3118" to "Industri og produksjon",
        "3119" to "Utdanning",
        "3121" to "Industri og produksjon",
        "3122" to "Industri og produksjon",
        "3123" to "Bygg og anlegg",
        "3131" to "Industri og produksjon",
        "3132" to "Industri og produksjon",
        "3133" to "Industri og produksjon",
        "3134" to "Industri og produksjon",
        "3135" to "Industri og produksjon",
        "3139" to "Industri og produksjon",
        "3141" to "Natur og miljø",
        "3142" to "Natur og miljø",
        "3143" to "Natur og miljø",
        "3151" to "Transport og lager",
        "3152" to "Transport og lager",
        "3153" to "Transport og lager",
        "3154" to "Transport og lager",
        "3155" to "Transport og lager",
        "3211" to "Helse og sosial",
        "3212" to "Helse og sosial",
        "3213" to "Helse og sosial",
        "3214" to "Helse og sosial",
        "3230" to "Helse og sosial",
        "3240" to "Natur og miljø",
        "3251" to "Helse og sosial",
        "3254" to "Helse og sosial",
        "3256" to "Helse og sosial",
        "3257" to "Natur og miljø",
        "3258" to "Sikkerhet og beredskap",
        "3259" to "Helse og sosial",
        "3311" to "Kontor og økonomi",
        "3312" to "Kontor og økonomi",
        "3313" to "Kontor og økonomi",
        "3315" to "Kontor og økonomi",
        "3321" to "Kontor og økonomi",
        "3322" to "Salg og service",
        "3323" to "Salg og service",
        "3324" to "Kontor og økonomi",
        "3331" to "Transport og lager",
        "3332" to "Reiseliv og mat",
        "3333" to "Kontor og økonomi",
        "3334" to "Salg og service",
        "3339" to "Kontor og økonomi",
        "3341" to "Kontor og økonomi",
        "3342" to "Kontor og økonomi",
        "3343" to "Kontor og økonomi",
        "3351" to "Sikkerhet og beredskap",
        "3352" to "Kontor og økonomi",
        "3353" to "Kontor og økonomi",
        "3354" to "Kontor og økonomi",
        "3355" to "Sikkerhet og beredskap",
        "3359" to "Kontor og økonomi",
        "3411" to "Sikkerhet og beredskap",
        "3412" to "Utdanning",
        "3413" to "Kultur og kreative yrker",
        "3421" to "Kultur og kreative yrker",
        "3422" to "Kultur og kreative yrker",
        "3423" to "Kultur og kreative yrker",
        "3431" to "Kultur og kreative yrker",
        "3432" to "Kultur og kreative yrker",
        "3433" to "Kultur og kreative yrker",
        "3434" to "Reiseliv og mat",
        "3439" to "Kultur og kreative yrker",
        "3511" to "IT",
        "3512" to "IT",
        "3513" to "IT",
        "3514" to "IT",
        "3521" to "Håndverkere",
        "3522" to "Håndverkere",
        "4110" to "Kontor og økonomi",
        "4131" to "Kontor og økonomi",
        "4132" to "Kontor og økonomi",
        "4211" to "Kontor og økonomi",
        "4212" to "Salg og service",
        "4213" to "Salg og service",
        "4214" to "Kontor og økonomi",
        "4221" to "Reiseliv og mat",
        "4222" to "Kontor og økonomi",
        "4223" to "Kontor og økonomi",
        "4224" to "Reiseliv og mat",
        "4225" to "Kontor og økonomi",
        "4226" to "Kontor og økonomi",
        "4227" to "Kontor og økonomi",
        "4229" to "Salg og service",
        "4311" to "Kontor og økonomi",
        "4312" to "Kontor og økonomi",
        "4313" to "Kontor og økonomi",
        "4321" to "Transport og lager",
        "4322" to "Transport og lager",
        "4323" to "Transport og lager",
        "4411" to "Kultur og kreative yrker",
        "4412" to "Salg og service",
        "4413" to "Kontor og økonomi",
        "4415" to "Kontor og økonomi",
        "4416" to "Kontor og økonomi",
        "5111" to "Reiseliv og mat",
        "5112" to "Reiseliv og mat",
        "5113" to "Reiseliv og mat",
        "5120" to "Reiseliv og mat",
        "5131" to "Reiseliv og mat",
        "5132" to "Reiseliv og mat",
        "5141" to "Salg og service",
        "5142" to "Salg og service",
        "5151" to "Salg og service",
        "5152" to "Salg og service",
        "5153" to "Håndverkere",
        "5161" to "Salg og service",
        "5163" to "Håndverkere",
        "5164" to "Natur og miljø",
        "5165" to "Utdanning",
        "5169" to "Salg og service",
        "5211" to "Salg og service",
        "5212" to "Salg og service",
        "5221" to "Salg og service",
        "5222" to "Salg og service",
        "5223" to "Salg og service",
        "5230" to "Salg og service",
        "5241" to "Kultur og kreative yrker",
        "5242" to "Salg og service",
        "5243" to "Salg og service",
        "5244" to "Salg og service",
        "5245" to "Salg og service",
        "5246" to "Salg og service",
        "5249" to "Salg og service",
        "5311" to "Utdanning",
        "5312" to "Utdanning",
        "5321" to "Helse og sosial",
        "5322" to "Helse og sosial",
        "5329" to "Helse og sosial",
        "5411" to "Sikkerhet og beredskap",
        "5413" to "Sikkerhet og beredskap",
        "5414" to "Sikkerhet og beredskap",
        "5419" to "Sikkerhet og beredskap",
        "6111" to "Natur og miljø",
        "6112" to "Natur og miljø",
        "6113" to "Natur og miljø",
        "6114" to "Natur og miljø",
        "6121" to "Natur og miljø",
        "6122" to "Natur og miljø",
        "6123" to "Natur og miljø",
        "6129" to "Natur og miljø",
        "6130" to "Natur og miljø",
        "6210" to "Natur og miljø",
        "6221" to "Natur og miljø",
        "6222" to "Natur og miljø",
        "6224" to "Natur og miljø",
        "7112" to "Håndverkere",
        "7113" to "Håndverkere",
        "7114" to "Håndverkere",
        "7115" to "Håndverkere",
        "7119" to "Håndverkere",
        "7121" to "Håndverkere",
        "7122" to "Håndverkere",
        "7123" to "Håndverkere",
        "7124" to "Håndverkere",
        "7125" to "Håndverkere",
        "7126" to "Håndverkere",
        "7127" to "Håndverkere",
        "7131" to "Håndverkere",
        "7132" to "Håndverkere",
        "7133" to "Håndverkere",
        "7211" to "Håndverkere",
        "7212" to "Håndverkere",
        "7213" to "Håndverkere",
        "7214" to "Håndverkere",
        "7215" to "Håndverkere",
        "7221" to "Industri og produksjon",
        "7222" to "Håndverkere",
        "7223" to "Industri og produksjon",
        "7224" to "Industri og produksjon",
        "7231" to "Håndverkere",
        "7232" to "Håndverkere",
        "7233" to "Industri og produksjon",
        "7234" to "Håndverkere",
        "7311" to "Håndverkere",
        "7312" to "Håndverkere",
        "7313" to "Håndverkere",
        "7314" to "Kultur og kreative yrker",
        "7315" to "Kultur og kreative yrker",
        "7316" to "Kultur og kreative yrker",
        "7317" to "Kultur og kreative yrker",
        "7318" to "Kultur og kreative yrker",
        "7319" to "Kultur og kreative yrker",
        "7321" to "Kultur og kreative yrker",
        "7322" to "Kultur og kreative yrker",
        "7323" to "Kultur og kreative yrker",
        "7411" to "Håndverkere",
        "7412" to "Håndverkere",
        "7413" to "Håndverkere",
        "7421" to "Håndverkere",
        "7422" to "Håndverkere",
        "7511" to "Industri og produksjon",
        "7512" to "Reiseliv og mat",
        "7513" to "Industri og produksjon",
        "7514" to "Industri og produksjon",
        "7515" to "Industri og produksjon",
        "7522" to "Industri og produksjon",
        "7531" to "Håndverkere",
        "7532" to "Industri og produksjon",
        "7534" to "Industri og produksjon",
        "7535" to "Industri og produksjon",
        "7536" to "Industri og produksjon",
        "7541" to "Sikkerhet og beredskap",
        "7542" to "Bygg og anlegg",
        "7543" to "Håndverkere",
        "7544" to "Håndverkere",
        "7549" to "Håndverkere",
        "8111" to "Industri og produksjon",
        "8112" to "Industri og produksjon",
        "8113" to "Industri og produksjon",
        "8114" to "Håndverkere",
        "8121" to "Industri og produksjon",
        "8122" to "Industri og produksjon",
        "8131" to "Industri og produksjon",
        "8132" to "Industri og produksjon",
        "8141" to "Industri og produksjon",
        "8142" to "Industri og produksjon",
        "8143" to "Industri og produksjon",
        "8151" to "Industri og produksjon",
        "8152" to "Industri og produksjon",
        "8153" to "Industri og produksjon",
        "8154" to "Industri og produksjon",
        "8155" to "Industri og produksjon",
        "8156" to "Industri og produksjon",
        "8157" to "Industri og produksjon",
        "8159" to "Industri og produksjon",
        "8160" to "Industri og produksjon",
        "8171" to "Industri og produksjon",
        "8172" to "Industri og produksjon",
        "8181" to "Industri og produksjon",
        "8182" to "Industri og produksjon",
        "8183" to "Industri og produksjon",
        "8189" to "Industri og produksjon",
        "8211" to "Industri og produksjon",
        "8212" to "Industri og produksjon",
        "8219" to "Bygg og anlegg",
        "8311" to "Transport og lager",
        "8312" to "Transport og lager",
        "8322" to "Transport og lager",
        "8331" to "Transport og lager",
        "8332" to "Transport og lager",
        "8341" to "Bygg og anlegg",
        "8342" to "Bygg og anlegg",
        "8343" to "Bygg og anlegg",
        "8344" to "Transport og lager",
        "8350" to "Transport og lager",
        "9111" to "Salg og service",
        "9112" to "Salg og service",
        "9122" to "Salg og service",
        "9123" to "Salg og service",
        "9129" to "Salg og service",
        "9211" to "Natur og miljø",
        "9212" to "Natur og miljø",
        "9213" to "Natur og miljø",
        "9214" to "Natur og miljø",
        "9215" to "Natur og miljø",
        "9216" to "Natur og miljø",
        "9311" to "Industri og produksjon",
        "9312" to "Bygg og anlegg",
        "9313" to "Bygg og anlegg",
        "9321" to "Transport og lager",
        "9329" to "Industri og produksjon",
        "9331" to "Transport og lager",
        "9333" to "Transport og lager",
        "9334" to "Salg og service",
        "9412" to "Reiseliv og mat",
        "9510" to "Salg og service",
        "9611" to "Salg og service",
        "9612" to "Salg og service",
        "9613" to "Salg og service",
        "9621" to "Transport og lager",
        "9622" to "Håndverkere",
        "9623" to "Salg og service",
        "9629" to "Håndverkere"
)
