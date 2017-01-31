const harStrukturkode = omrade => omrade.strukturkode != null;

export const finnIdForFylkenummer = (fylkenummer, omrader) => omrader.filter(harStrukturkode).find(omrade => omrade.strukturkode.endsWith(fylkenummer)).id;

export const finnIdForKommunenummer = (kommunenummer, omrader) => getAlleKommunerForOmrader(omrader)
    .filter(harStrukturkode)
    .find(omrade => omrade.strukturkode.endsWith(kommunenummer))
    .id;

export const getAlleKommunerForOmrader = omrader => omrader.map(omrade => omrade.underomrader).reduce((a, b) => a.concat(b), []);

export const getNavnForKommuneId = (kommuneid, omrader) => getAlleKommunerForOmrader(omrader).find(omrade => omrade.id === kommuneid).navn;

export const getNavnForFylkeId = (kommuneid, omrader) => omrader.find(omrade => omrade.id === kommuneid).navn;