import { STATUS, ALTERNATIV_ALLE } from '../../felles/konstanter';
import { getActions } from '../../felles/rest/rest-reducer';
import {
  buildUriParams,
  fetchToJson,
  sendResultatTilDispatch,
  handterFeil,
  getParamsForValgteFylkerOgKommuner,
} from '../../felles/rest/rest-utils';

import {
  getHarValgtOmrade,
  getValgteYrkesgrupperId,
  getValgtYrkesomradeId,
} from './ledigestillinger-stillinger-selectors';



export const hentAntallStillingerForYrkesgruppe = () => (dispatch, getState) => {
  const state = getState();
  const actions = getActions('oversikt_stillinger');

  if (!getHarValgtOmrade(state)) {
    return;
  }
  dispatch({ type: actions[STATUS.laster] });

  const uri = `/omrader/kommunedata?${buildUriParams(getUriParamsForFiltrering(state))}`;
  fetchToJson(uri)
    .then(
      sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
      handterFeil(dispatch, actions[STATUS.feilet]),
    );
};

const getUriParamsForFiltrering = (state) => {
  const params = getParamsForValgteFylkerOgKommuner(state);
  params.yrkesgrupper = getValgteYrkesgrupperId(state);
  const valgtYrkesomradeId = getValgtYrkesomradeId(state);
  if (valgtYrkesomradeId !== ALTERNATIV_ALLE) {
    params.yrkesomrade = valgtYrkesomradeId;
  }
  return params;
};

