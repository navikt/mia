import {buildUriParams, fetchToJson} from '../../../felles/rest/rest-utils';
import {ALTERNATIV_ALLE} from '../../../felles/konstanter';

export const getPopupMedInnholdslaster = (navn) => {
    return `
            <h3 class="typo-etikett">${navn}</h3>
            <div class="innholdslaster"><div class="spinner spinner-m" /></div>`;
};

export const getPopupForOmrade = (navn, data) => {
    return `
            <div>
                <h3 class="typo-etikett blokk-xxs">${navn}</h3>
                <span>Arbeidsledige: ${data.antallLedige == null ? "<4" : data.antallLedige}</span><br />
                <span>Ledige stillinger: ${data.antallStillinger == null ? 0 : data.antallStillinger}</span>
            </div>`;
};

export const hentDataForKommune = (id, yrkesomrade, yrkesgrupper) => {
    return hentDataForOmrade(id, null, yrkesomrade, yrkesgrupper, "/omrader/kommunedata");
};

export const hentDataForFylke = (id, yrkesomrade, yrkesgrupper) => {
    return hentDataForOmrade(null, id, yrkesomrade, yrkesgrupper, "/omrader/fylkesdata");
};

const hentDataForOmrade = (kommune, fylke, yrkesomrade, yrkesgrupper, baseUri) => {
    const params = {
        kommuner: kommune != null ? [kommune] : null,
        fylker: fylke != null ? [fylke] : null,
        yrkesomrade: yrkesomrade === ALTERNATIV_ALLE ? null : yrkesomrade,
        yrkesgrupper: yrkesgrupper
    };
    const uri = baseUri + "?" + buildUriParams(params);
    return fetchToJson(uri);
};
