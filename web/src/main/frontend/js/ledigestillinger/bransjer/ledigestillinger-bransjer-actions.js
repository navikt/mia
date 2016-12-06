import {actions} from './ledigestillinger-bransjer-reducer';
import {fetchToJson, handterFeil, sendResultatTilDispatch} from "../../felles/utils/rest-utils";

export const hentYrkesomraderForAlleFylker = () => dispatch => {
    dispatch({ type: actions.laster_yrkesomrader });
    fetchToJson("/bransjer/yrkesomrade/hentForFylke")
        .then(sendResultatTilDispatch(dispatch, actions.lastet_yrkesomrader))
        .catch(handterFeil(dispatch, actions.feilet_yrkesomrader));
};
