export function getValgteKommunerForFylke(fylke, fylker, valgteKommuner){
    const valgteKommunderForFylke = fylker.find(f => f.id === fylke).underomrader.filter(omrade => valgteKommuner.includes(omrade.id));
    return valgteKommunderForFylke.length === 0 ? fylker.find(f => f.id === fylke).underomrader : valgteKommunderForFylke;
}

export function getKommuneMedData(kommune, stillinger) {
    const stillingdata = stillinger.find(stilling => stilling.id === kommune.id) || {};
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

export function getStillingerTotaltForKommuneIFylke(fylke, fylker, valgteKommuner, stillinger) {
    return getStillingerTotalt(getValgteKommunerForFylke(fylke, fylker, valgteKommuner), stillinger);
}
