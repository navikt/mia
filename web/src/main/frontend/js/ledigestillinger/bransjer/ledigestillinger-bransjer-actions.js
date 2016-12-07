import {actions} from './ledigestillinger-bransjer-reducer';
import {fetchToJson, handterFeil, sendResultatTilDispatch} from "../../felles/rest/rest-utils";

export const hentYrkesomraderForAlleFylker = () => dispatch => {
    dispatch({ type: actions.laster_yrkesomrader });
    fetchToJson("/bransjer/level1/hentForFylke")
        .then(sendResultatTilDispatch(dispatch, actions.lastet_yrkesomrader))
        .catch(handterFeil(dispatch, actions.feilet_yrkesomrader));
};
