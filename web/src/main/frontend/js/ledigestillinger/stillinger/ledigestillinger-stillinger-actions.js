import {STATUS, ALTERNATIV_ALLE} from "../../felles/konstanter";
import {getActions} from "../../felles/rest/rest-reducer";
import {
    buildUriParams,
    fetchToJson,
    sendResultatTilDispatch,
    handterFeil,
    getParamsForValgteFylkerOgKommuner
} from "../../felles/rest/rest-utils";

import {
    getHarValgtOmrade,
    getHarValgtYrkesgrupper,
    getValgteYrkesgrupperId,
    getValgtYrkesomradeId
} from "./ledigestillinger-stillinger-selectors";

export const hentStillinger = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('stillinger');

    if (!getHarValgtYrkesgrupper(state)) {
        return;
    }

    dispatch({type: actions[STATUS.laster]});
    const uri = "/bransjer/stillinger?" + buildUriParams(getUriParams(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet])
        );
};

export const hentAntallStillingerForYrkesgruppe = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('oversikt_stillinger');

    if(!getHarValgtOmrade(state)){
        return;
    }
    dispatch({type: actions[STATUS.laster]});

    const uri = "/omrader/kommunedata?" + buildUriParams(getUriParamsForKommunerYrkesgrupperYrkesomrader(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet])
        );
};

const getUriParams = state => {
    const params = getParamsForValgteFylkerOgKommuner(state);
    params.yrkesgrupper = getValgteYrkesgrupperId(state);
    return params;
};

const getUriParamsForKommunerYrkesgrupperYrkesomrader = state => {
    const params = getParamsForValgteFylkerOgKommuner(state);
    params.yrkesgrupper = getValgteYrkesgrupperId(state);
    const valgtYrkesomradeId = getValgtYrkesomradeId(state);
    if (valgtYrkesomradeId !== ALTERNATIV_ALLE) {
        params.yrkesomrade = valgtYrkesomradeId;
    }
    return params;
};

