import {actions} from './ledigestillinger-bransjer-reducer';
import {fetchToJson, handterFeil, sendResultatTilDispatch} from "../../felles/utils/rest-utils";

export const hentYrkesomraderForAlleFylker = () => dispatch => {
    dispatch({ type: actions.laster_yrkesomrader });
    fetchToJson("/bransjer/yrkesomrade/hentForFylke")
        .then(sendResultatTilDispatch(dispatch, actions.lastet_yrkesomrader))
        .catch(handterFeil(dispatch, actions.feilet_yrkesomrader));
};

export const hentYrkesgrupperForOmrade = (yrkesomradeid) => dispatch => {
    dispatch({ type: actions.laster_yrkesgrupper });
    fetchToJson("/bransjer/yrkesgruppe/hentForYrkesomrade?yrkesomrade="+yrkesomradeid)
        .then(sendResultatTilDispatch(dispatch, actions.lastet_yrkesgrupper))
        .catch(handterFeil(dispatch, actions.feilet_yrkesgrupper));
};