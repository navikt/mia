# Muligheter i Arbeidsmarkedet

Muligheter i Arbeidsmarkedet er en applikasjon hvor man kan få oversikt over arbeidsmarkedet
gjennom å se på ulike bransjer om områder. Tjenesten har en kartløsning for å lett presentere
dataene på en forstålig måte.

## Utvikling lokalt

Applikasjonen er delt i 2 ulike delere. En backend og en frontend. Backenden kan kjøres opp ved å starte
Main-metoden i `Application.kt`, mens frontenden kan startes ved å kjøre `npm run start` i mappen `src/frontend`.

Ved å sette miljøvariabelen `USE_MOCK=true` vil backenden starte opp med mock-profilen, og mocke alle
konsument-tjenestene.

## Lage nye geojson-filer for ønsket nøyaktighet:

Applikasjonen bruker vektordata for å tegne opp grensene for ulike fylker og kommuner i kartet. Disse kartdataene er hentet
fra kartverket, men som originalfiler er de alt for store og har så høy nøyaktighet at nettleseren kan få problemer med å
tegne de opp. Vi har derfor tatt i bruk en tjeneste som heter `simplify-geojson` som kan brukes til å simplifisere
geojson-filer, og vil prøve å fokusere på å fjerne "unødvendige" datapunker.

Kommandoen som brukes til dette er:

```
cat ../webapp/geojson/{input-geojson}.json | ./node_modules/.bin/simplify-geojson -t 0.003 > ../webapp/geojson/{output-geojson}.json
```

* input-geojson: Inputfil med geojsondata fra kartverket. Vi bruker vektordata om fylker og kommuner i dag.
* Faktor: En faktor som sier hvor nøyaktige streke skal være. Høyere tall gir færre datapunker men større unøyaktighet.
* output-geojson: Navnet på output-filen. For at det skal fungere uten endringer av koden må navnet være `kommuner` eller `fylker`.


