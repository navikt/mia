import {fetchToJson, sendResultatTilDispatch, handterFeil} from "../rest/rest-utils";
import { STATUS } from "../konstanter";

export const LASTER_TEKSTER = 'LASTER_TEKSTER';
export const LASTET_TEKSTER = 'LASTET_TEKSTER';
export const FEIL_TEKSTER = 'FEIL_TEKSTER';

const defaultstate = {
    locale: "nb",
    status: STATUS.initialisert,
    messages: {},
    defaultLocale: "nb"
};
export default function teksterReducer(state = defaultstate, action) {
    switch (action.type) {
        case LASTER_TEKSTER:
            return {...state, status: STATUS.laster};
        case FEIL_TEKSTER:
            return {...state, status: STATUS.feilet};
        case LASTET_TEKSTER:
            return {...state, status: STATUS.lastet, messages: action.payload};
        default:
            return state;
    }
}

function leggCmsKeyPaaTekster(dispatch, visCmsKeys) {
    return ({type, payload}) => {
        if(visCmsKeys) {
            const meldinger = {};
            Object.keys(payload).forEach(key => {
                meldinger[key] = `${payload[key]} [${key}]`;
            });
            return dispatch({type, payload: meldinger});
        }
        return payload;
    };
}

export function lastTekster(visCmsKeys) {
    return function (dispatch) {
        dispatch({type: LASTER_TEKSTER});

        return fetchToJson('/tekster?lang=nb')
            .then(sendResultatTilDispatch(dispatch, LASTET_TEKSTER))
            .then(leggCmsKeyPaaTekster(dispatch, visCmsKeys))
            .catch(handterFeil(dispatch, FEIL_TEKSTER));
    };
}