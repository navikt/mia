import {RESTURL} from '../konstanter.js';

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

export function handterFeil(dispatch, action) {
    return error => {
        if (error.response){
            error.response.json().then((data) => {
                console.error(error, error.stack, data); // eslint-disable-line no-console
                dispatch({type: action, data: {response: error.response, payload: data}});
            });
        } else {
            console.error(error, error.stack); // eslint-disable-line no-console
            dispatch({type: action, payload: error.toString()});
        }
    };
}

const secConfig = {credentials: 'same-origin', redirect: 'manual'};
export function fetchToJson(url, config = {}) {
    return fetch(RESTURL + url, {...secConfig, ...config})
        .then(sjekkStatuskode)
        .then(toJson);
}

function reduceParamList(paramlist) {
    return paramlist.join("&");
}

function getUriParam(key, param) {
    if(Array.isArray(param)) {
        return reduceParamList(param.map(p => `${key}[]=${p}`));
    }
    return `${key}=${param}`;
}

export function buildUriParams(params) {
    return reduceParamList(Object.keys(params)
        .map(key => getUriParam(key, params[key])));
}

export function getParamsForValgteFylkerOgKommuner(state) {
    const valgteKommuner = state.ledigestillinger.oversikt.valgteKommuner;
    const valgteFylker = state.ledigestillinger.oversikt.valgteFylker;

    const harIkkeValgtKommuneIFylke = valgtFylke => {
        return !state.rest.omrader.data.find(fylke => fylke.id === valgtFylke).underomrader.find(kommune => valgteKommuner.includes(kommune.id));
    };

    const params = {};
    params['fylker'] = valgteFylker.filter(harIkkeValgtKommuneIFylke);
    params['kommuner'] = valgteKommuner;
    return params;
}