# Brukerflate

## Oppsett lokalt

1. Kjør `mvn clean install`
2. Start jetty-test: `web/src/test/java/no/nav/fo/StartJetty`

## Ting vi trenger:

- [x] Oppsett av servlet
- [x] Legge til frontend med bygging
- [x] Oppsett for kjøring av unit-tester (js)
- [ ] Intergrasjon/unit-tester
- [x] Rullerende versjonering
- [x] Eget steg for javatester
- [ ] Publisere coverage til sonar
- [x] Verifisere ingen snapshot dependencies

Ønske:
- [x] Automatisk deploy til T1
- [ ] Automatisk deploy til Q1

## Lage nye geojson-filer for ønsket nøyaktighet:
```
cat ../webapp/geojson/fylker-original.json | ./node_modules/.bin/simplify-geojson -t 0.003 > ../webapp/geojson/fylker.json
```
