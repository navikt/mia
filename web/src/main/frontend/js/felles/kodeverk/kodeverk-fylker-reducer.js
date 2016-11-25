import {fetchToJson, sendResultatTilDispatch, handterFeil} from "../utils/rest-utils";
import { STATUS } from "../konstanter";

export const LASTER_FYLKER = 'LASTER_FYLKER';
export const LASTET_FYLKER = 'LASTET_FYLKER';
export const FEIL_FYLKER = 'FEIL_FYLKER';

const defaultstate = {
    status: STATUS.initialisert,
    fylker: []
};
export default function fylkerReducer(state = defaultstate, action) {
    switch (action.type) {
        case LASTER_FYLKER:
            return {...state, status: STATUS.laster};
        case FEIL_FYLKER:
            return {...state, status: STATUS.feilet};
        case LASTET_FYLKER:
            return {...state, status: STATUS.lastet, fylker: action.payload};
        default:
            return state;
    }
}

export function lastFylker() {
    return function (dispatch) {
        dispatch({type: LASTER_FYLKER});

        return fetchToJson('/kodeverk/fylker')
            .then(sendResultatTilDispatch(dispatch, LASTET_FYLKER))
            .catch(handterFeil(dispatch, FEIL_FYLKER));
    };
}