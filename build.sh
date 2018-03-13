#!/bin/bash
# Denne filen må ha LF som line separator.

# Stop scriptet om en kommando feiler
set -e

# Usage string
usage="Script som bygger prosjektet og publiserer til nexus

Om environment variabelen 'versjon' er satt vil den erstatte versjonen som ligger i pom.xml.

Bruk:
./$(basename "$0") OPTIONS

Gyldige OPTIONS:
    -h  | --help        - printer denne hjelpeteksten
"



# Default verdier
PROJECT_ROOT="$( cd "$(dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Hent ut argumenter
for arg in "$@"
do
case $arg in
    -h|--help)
    echo "$usage" >&2
    exit 1
    ;;
    *) # ukjent argument
    printf "Ukjent argument: %s\n" "$1" >&2
    echo ""
    echo "$usage" >&2
    exit 1
    ;;
esac
done


function build_frontend() {
    cd $PROJECT_ROOT/web/src/frontend
    npm install
    #test og build kan legges til når tester og eget byggescript for frontend legges til
    #npm run test
    #npm run build
    cd $PROJECT_ROOT
}


function build_backend() {
    if [[ ${versjon+x} ]]; then
        mvn versions:set -U -DnewVersion=${versjon}
    fi
    mvn clean package dependency:tree help:effective-pom --batch-mode -U
}

function publish() {
    mvn deploy --batch-mode -DskipTests
    if [[ ${versjon+x} ]]; then
        mvn versions:revert
    fi
}


build_frontend
build_backend
publish