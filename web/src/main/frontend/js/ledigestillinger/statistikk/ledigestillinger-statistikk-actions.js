import { STATUS } from './../../felles/konstanter';
import { getActions } from './../../felles/rest/rest-reducer';
import {
    buildUriParams,
    fetchToJson,
    sendResultatTilDispatch,
    handterFeil,
    getParamsForValgteFylker
} from './../../felles/rest/rest-utils';

export const hentStatistikk = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('statistikk');

    dispatch({ type: actions[STATUS.laster] });

    const uri = "/statistikk/ledighet?" + buildUriParams(getParamsForValgteFylker(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet])
        );
};
