import {STATUS, ALTERNATIV_ALLE} from "../../felles/konstanter";
import {getActions} from "../../felles/rest/rest-reducer";
import {buildUriParams, fetchToJson, sendResultatTilDispatch, handterFeil} from "../../felles/rest/rest-utils";

export const hentYrkesgrupper = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('yrkesgrupper');

    if(state.ledigestillinger.bransje.valgtyrkesomrade === ALTERNATIV_ALLE) {
        return;
    }

    dispatch({ type: actions[STATUS.laster] });

    const uri = "/bransjer/yrkesgruppe?" + buildUriParams(getUriParams(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet])
        );
};

function getUriParams(state) {
    const params = {};

    const yrkesomrade = state.ledigestillinger.bransje.valgtyrkesomrade;
    if(yrkesomrade != null) {
        params['yrkesomrade'] = state.ledigestillinger.bransje.valgtyrkesomrade;
    }

    return params;
}
