# Muligheter i Arbeidsmarkedet

[![Build Status](https://travis-ci.org/navikt/mia.svg?branch=master)](https://travis-ci.org/navikt/mia)

Muligheter i Arbeidsmarkedet er en applikasjon hvor man kan få oversikt over arbeidsmarkedet
gjennom å se på ulike bransjer om områder. Tjenesten har en kartløsning for å lett presentere
dataene på en forstålig måte.

## Utvikling lokalt

Applikasjonen er delt i 2 ulike delere. En backend og en frontend. Backenden kan kjøres opp ved å starte
Main-metoden i `Application.kt`, mens frontenden kan startes ved å kjøre `npm install && npm run start` i mappen `src/frontend`.

Ved å sette miljøvariabelen `USE_MOCK=true` vil backenden starte opp med mock-profilen, og mocke alle
konsument-tjenestene.

For å kjøre opp uten mock må følgende miljøvariabler være definert:

| Variabelnavn            | Beskrivelse                                                              |
| ----------------------- | ------------------------------------------------------------------------ |
| MIA_ELASTIC_USER        | Brukernavn til mia sin elastic-search for statistikkdata                 |
| MIA_ELASTIC_PSW         | Passord for brukeren til mia i elastic-search for statatistikkdata       |
| MIA_ELASTIC_HOSTNAME    | Hostname til elastic-search som holder på mia sin statistikkdata         |
| MIA_ELASTIC_SCHEME      | http eller https basert på om elastic er tilgjengelig via SSL eller ikke |

## Lage nye geojson-filer for ønsket nøyaktighet:
Last ned geojson filene for fylker og kommuner fra [kartverket](https://kartkatalog.geonorge.no/tema/administrative-inndelinger/3).  
Siden filene er alt for store må vi forenkle disse  
Fra fylker filen kopier inholdet i `administrative_enheter.fylke` sin `features`.  
Gjør det samme i kommuner filen, men kopier fra `administrative_enheter.kommune`.  
Putt alt dette inn i en ny fil som ser slik ut: 
```
{
    "type" : "FeatureCollection",
    "features" : [
        innholdet fra fylker og kommuner
    ]
}
```
Dette gjøres for å sørge for at fylkesgrensene forenkles likt som kommunene.  
Deretter forenkler man filen med npm modulen [mapshaper](https://mapshaper.org/), man kan eventult bruke nettsiden.
Vi har funnet at 2,3% er en fornuftig cutoff for forenklingen
```
mapshaper -i snap fylkeskommuner.json -simplify 0.023 keep-shapes -o fylkeskommunerSimple.json 
```
splitet tilbake til fylker.json og kommuner.json med samme struktur som over og konverterer kordinatene med
```
cat  {input-geojson}.json | reproject --eio --from=EPSG:25833 --to=EPSG:4326 > {output-geojson}.json
```

* input-geojson: Inputfil med geojsondata fra kartverket. Vi bruker vektordata om fylker og kommuner i dag.
* Faktor: En faktor som sier hvor nøyaktige streke skal være. Høyere tall gir færre datapunker men større unøyaktighet.
* output-geojson: Navnet på output-filen. For at det skal fungere uten endringer av koden må navnet være `kommuner` eller `fylker`.


## Henvendelser

Opprett en issue i GitHub for eventuelle spørsmål.

Er du ansatt i NAV kan du stille spørsmål på Slack i kanalen #fo
