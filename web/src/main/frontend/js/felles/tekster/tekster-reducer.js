import {fetchToJson, sendResultatTilDispatch, handterFeil} from "../utils/rest-utils";

export const LASTER_TEKSTER = 'LASTER_TEKSTER';
export const LASTET_TEKSTER = 'LASTET_TEKSTER';
export const FEIL_TEKSTER = 'FEIL_TEKSTER';

const defaultstate = {
    locale: "nb",
    lastet: false,
    messages: {},
    defaultLocale: "nb"
};
export default function teksterReducer(state = defaultstate, action) {
    switch (action.type) {
        case LASTET_TEKSTER:
            return {...state, lastet: true, messages: action.data};
        default:
            return state;
    }
}

function leggCmsKeyPaaTekster(dispatch, visCmsKeys) {
    return ({type, data}) => {
        if(visCmsKeys) {
            const meldinger = {};
            Object.keys(data).forEach(key => {
                meldinger[key] = `${data[key]} [${key}]`;
            });
            return dispatch({type, data: meldinger});
        }
        return data;
    };
}

export function lastTekster(visCmsKeys) {
    return function (dispatch) {
        dispatch({ type: LASTER_TEKSTER});

        return fetchToJson('/tekster?lang=nb')
            .then(sendResultatTilDispatch(dispatch, LASTET_TEKSTER))
            .then(leggCmsKeyPaaTekster(dispatch, visCmsKeys))
            .catch(handterFeil(dispatch, FEIL_TEKSTER));
    };
}