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

export function buildUriParams(params) {
    return Object.keys(params)
        .map(key => `${key}=${params[key]}`)
        .reduce((paramstring, current) => `${paramstring}&${current}`, "");
}