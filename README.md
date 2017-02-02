# Brukerflate

## Oppsett lokalt

1. Kjør `mvn clean install`
2. Start jetty-test: `web/src/test/java/no/nav/fo/StartJetty`

## Ved utvikling

For utvikling har vi satt opp en del hjelpekommandoer som kan kjøres gjennom npm.
Alle kommandoene kan kjøres fra kommandilinjen i mappen `web/src/frontend`.

| Kommando              | Beskrivelse                                                       |
| ----------------------|-------------------------------------------------------------------|
| `npm install`         | Installerer frontend-avhengigheter                                |
| `npm run build`       | Bygger frontenden (i dev-modus)                                   |
| `npm run buildProd`   | Bygger frontenden for produksjon (minifisert)                     |
| `npm run dev`         | Setter opp kontinuerlig bygging frontend-koden                    |
| `npm run test`        | Kjører frontendtestene                                            |
| `npm run eslint`      | Vil kjøre linting av frontend (kodekvalitetstets)                 |
| `npm run integration` | Starter integrasjonstestene. Støtter både chrome og phantomjs     |


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


