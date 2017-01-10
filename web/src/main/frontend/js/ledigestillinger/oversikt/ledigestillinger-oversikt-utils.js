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

export const compareOmrader = (k1, k2) => {
    const erGenereltOmrade = omrade => omrade.id.startsWith('110011');

    if(erGenereltOmrade(k1) && !erGenereltOmrade(k2)) {
        return 1;
    } else if(!erGenereltOmrade(k1) && erGenereltOmrade(k2)) {
        return -1
    }
    return k1.navn.localeCompare(k2.navn);
};
