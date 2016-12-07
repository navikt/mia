export function getKommunerForValgtFylke(valgtFylke, fylker){
    return valgtFylke === "" ? [] : fylker.find(fylke => fylke.id === valgtFylke).underomrader;
}

export function getKommuneMedData(kommune, stillinger) {
    const stillingdata = stillinger.find(stilling => stilling.kommuneid === kommune.id) || {};
    return {
        navn: kommune.navn,
        id: kommune.id,
        antallLedige: stillingdata.antallLedige || 0,
        antallStillinger: stillingdata.antallStillinger || 0
    };
}

export function getStillingerTotalt(kommuner, stillinger) {
    return kommuner.map(kommune => getKommuneMedData(kommune, stillinger)).reduce((totalt, kommune) => {
        totalt.antallLedige += kommune.antallLedige;
        totalt.antallStillinger += kommune.antallStillinger;
        return totalt;
    }, { antallLedige: 0, antallStillinger: 0 });
}
