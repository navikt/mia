import {actions} from "./ledigestillinger-oversikt-reducer";
import {fetchToJson, handterFeil, sendResultatTilDispatch} from "../../felles/utils/rest-utils";

export const lastOversiktAlleKommuner = () => dispatch => {
    dispatch({type: actions.laster_oversikt_stillinger});
    fetchToJson("/stillinger/oversiktAlleKommuner")
        .then(sendResultatTilDispatch(dispatch, actions.lastet_oversikt_stillinger))
        .catch(handterFeil(dispatch, actions.feilet_oversikt_stillinger));
};
