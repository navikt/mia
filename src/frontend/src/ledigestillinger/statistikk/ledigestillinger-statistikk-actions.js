import { STATUS, ALTERNATIV_ALLE } from './../../felles/konstanter';
import { getActions } from './../../felles/rest/rest-reducer';
import {
    buildUriParams,
    fetchToJson,
    sendResultatTilDispatch,
    handterFeil,
    getParamsForValgteFylkerOgKommuner
} from '../../felles/rest/rest-utils';

import {getValgteYrkesgrupperId} from './../stillinger/ledigestillinger-stillinger-selectors';

export const hentStatistikk = () => (dispatch, getState) => {
    const state = getState();
    const actions = getActions('statistikk');

    dispatch({ type: actions[STATUS.laster] });

    const uri = "/ledighet/statistikk?" + buildUriParams(getUriParams(state));
    fetchToJson(uri)
        .then(
            sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
            handterFeil(dispatch, actions[STATUS.feilet], false)
        );
};

const getUriParams = state => {
    const params = getParamsForValgteFylkerOgKommuner(state);
    const yrkesomrade = state.ledigestillinger.bransje.valgtyrkesomrade;
    if (yrkesomrade != null && yrkesomrade !== ALTERNATIV_ALLE) {
        params['yrkesomrade'] = yrkesomrade;
    }
    params.yrkesgrupper = getValgteYrkesgrupperId(state);
    return params;
};
