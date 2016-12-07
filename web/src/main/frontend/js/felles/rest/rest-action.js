import {fetchToJson, sendResultatTilDispatch, handterFeil} from "../rest/rest-utils";
import {getActions} from "./rest-reducer";
import {STATUS} from "../konstanter";

const restActionCreator = (reducernavn, uri) => {
    const actions = getActions(reducernavn);
    return dispatch => {
        dispatch({type: actions[STATUS.laster]});
        return fetchToJson(uri)
            .then(
                sendResultatTilDispatch(dispatch, actions[STATUS.lastet]),
                handterFeil(dispatch, actions[STATUS.feilet])
            );
    };
};

export default restActionCreator;