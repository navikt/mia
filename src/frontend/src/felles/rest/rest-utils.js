import {RESTURL, EOS_EU, RESTEN_AV_VERDEN} from '../konstanter.js';
import {apne_modal as apne_feilmodal} from '../../feilmodal/feilmodal-actions';

export function sjekkStatuskode(response) {
    if (response.status >= 200 && response.status < 300) {
        return response;
    }
    var error = new Error(response.statusText);
    error.response = response;
    throw error;
}

export function toJson(response) {
    return response.json();
}

export function sendResultatTilDispatch(dispatch, action) {
    return data => dispatch({type: action, payload: data});
}

export function handterFeil(dispatch, action, visPopup=true) {
    const visFeilmelding = (error, data) => {
        console.error(error, error.stack, data); // eslint-disable-line no-console
        dispatch({type: action, payload: data});
        if(visPopup) {
            dispatch({...apne_feilmodal, feilkode: data});
        }
    };

    return error => {
        if (error.response && error.response.json){
            try {
                error.response.json().then((data) => {
                    visFeilmelding(error, data.callId);
                });
            } catch (e) {
                visFeilmelding(error);
            }
        } else {
            visFeilmelding(error);
        }
    };
}

const secConfig = {credentials: 'same-origin', redirect: 'manual'};
export function fetchToJson(url, config = {}) {
    const headers = new Headers();
    headers.append('pragma', 'no-cache');
    headers.append('cache-control', 'no-cache');

    return fetch(RESTURL + url, {...secConfig, ...config, headers})
        .then(sjekkStatuskode)
        .then(toJson, err => Promise.reject(err));
}

function reduceParamList(paramlist) {
    return paramlist.join("&");
}

function getUriParam(key, param) {
    if(Array.isArray(param)) {
        return reduceParamList(param.map(p => `${key}=${p}`));
    }
    return `${key}=${param}`;
}

export function buildUriParams(params) {
    return reduceParamList(Object.keys(params)
        .filter(key => params[key] != null)
        .map(key => getUriParam(key, params[key])));
}

export function getParamsForValgteFylkerOgKommuner(state) {
    const valgteKommuner = state.ledigestillinger.oversikt.valgteKommuner;
    const valgteFylkerOgLandomrader = state.ledigestillinger.oversikt.valgteFylker;
    const landomrader = [EOS_EU, RESTEN_AV_VERDEN];
    const valgteFylker = valgteFylkerOgLandomrader.filter(omrade => !landomrader.includes(omrade));

    const harIkkeValgtKommuneIFylke = valgtFylke => {
        return !state.rest.omrader.data.find(fylke => fylke.id === valgtFylke).underomrader.find(kommune => valgteKommuner.includes(kommune.id));
    };

    const params = {};
    params['fylker'] = valgteFylker.filter(harIkkeValgtKommuneIFylke);
    params['kommuner'] = valgteKommuner;
    params['eoseu'] = valgteFylkerOgLandomrader.some(omrade => omrade === EOS_EU);
    params['restenavverden'] = valgteFylkerOgLandomrader.some(omrade => omrade === RESTEN_AV_VERDEN);
    return params;
}

export function getParamsForValgteFylker(state) {
    const valgteFylker = state.ledigestillinger.oversikt.valgteFylker;

    return {
        fylker: valgteFylker
    };
}