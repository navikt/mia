import {STATUS} from "../../felles/konstanter";
import {getActions} from "../../felles/rest/rest-reducer";
import {
    buildUriParams,
    fetchToJson,
    sendResultatTilDispatch,
    handterFeil,
    getParamsForValgteFylkerOgKommuner
} from "../../felles/rest/rest-utils";

import {
    getHarValgtYrkesgrupper,
    getValgteYrkesgrupperId
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

const getUriParams = state => {
    const params = getParamsForValgteFylkerOgKommuner(state);
    params.yrkesgrupper = getValgteYrkesgrupperId(state);
    return params;
};
