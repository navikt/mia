export function getKommunerForValgtFylke(valgtFylke, fylker){
    return valgtFylke === "" ? [] : fylker.find(fylke => fylke.navn === valgtFylke).kommuner;
}

export function getKommuneMedData(kommuneFraKodeverk, kommuneData) {
    const kommunedata = kommuneData.stillinger.find(kommune => kommune.kommunenummer === kommuneFraKodeverk.kommunenummer) || {};
    return {
        navn: kommuneFraKodeverk.navn,
        kommunenummer: kommuneFraKodeverk.kommunenummer,
        antallLedige: kommunedata.antallLedige || 0,
        antallStillinger: kommunedata.antallStillinger || 0
    };
}

export function getStillingerTotalt(kommuner, kommuneData) {
    return kommuner.map(kommune => getKommuneMedData(kommune, kommuneData)).reduce((totalt, kommune) => {
        totalt.antallLedige += kommune.antallLedige;
        totalt.antallStillinger += kommune.antallStillinger;
        return totalt;
    }, { antallLedige: 0, antallStillinger: 0 });
}
