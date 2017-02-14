import {highlightStyling, selectedStyling, geojsonStyling} from './kart-styling'

const harStrukturkode = omrade => omrade.strukturkode != null;

export const finnIdForFylkenummer = (fylkenummer, omrader) => omrader.filter(harStrukturkode).find(omrade => omrade.strukturkode.endsWith(fylkenummer)).id;

export const finnIdForKommunenummer = (kommunenummer, omrader) => {
    const kommune = getAlleKommunerForOmrader(omrader)
        .filter(harStrukturkode)
        .find(omrade => omrade.strukturkode.endsWith(kommunenummer));
    return kommune == null ? null : kommune.id;
};

export const getAlleKommunerForOmrader = omrader => omrader.map(omrade => omrade.underomrader).reduce((a, b) => a.concat(b), []);

export const getNavnForKommuneId = (kommuneid, omrader) => getAlleKommunerForOmrader(omrader).find(omrade => omrade.id === kommuneid).navn;

export const getNavnForFylkeId = (kommuneid, omrader) => omrader.find(omrade => omrade.id === kommuneid).navn;

export const highlightFeature = e => {
    const layer = e.target;
    let styling = highlightStyling;

    if(layer.feature.properties.valgt === true) {
        styling = {...styling, ...selectedStyling};
    }
    layer.setStyle(styling);
};

export const resetHighlight = e =>  {
    e.target.setStyle(geojsonStyling);
    if(e.target.feature.properties.valgt === true) {
        e.target.setStyle(selectedStyling);
    }
};