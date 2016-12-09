import {STATUS, ALTERNATIV_ALLE} from "../../felles/konstanter";
import {getActions} from "../../felles/rest/rest-reducer";
import {
    buildUriParams,
    fetchToJson,
    sendResultatTilDispatch,
    handterFeil,
    getParamsForValgteFylkerOgKommuner
} from "../../felles/rest/rest-utils";

export const hentYrkesomrader = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('yrkesomrader');

    dispatch({type: actions[STATUS.laster]});

    const uri = "/bransjer/yrkesomrade?" + buildUriParams(getParamsForValgteFylkerOgKommuner(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet])
        );
};

export const hentYrkesgrupper = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('yrkesgrupper');

    if (state.ledigestillinger.bransje.valgtyrkesomrade === ALTERNATIV_ALLE) {
        return;
    }

    dispatch({type: actions[STATUS.laster]});

    const uri = "/bransjer/yrkesgruppe?" + buildUriParams(getUriParams(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet])
        );
};

function getUriParams(state) {
    const params = getParamsForValgteFylkerOgKommuner(state);

    const yrkesomrade = state.ledigestillinger.bransje.valgtyrkesomrade;
    if (yrkesomrade != null) {
        params['yrkesomrade'] = yrkesomrade;
    }

    return params;
}
