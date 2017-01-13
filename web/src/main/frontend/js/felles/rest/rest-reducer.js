import {combineReducers} from "redux";
import {STATUS} from "../konstanter";

export const getActions = navn => {
    return Object.keys(STATUS)
        .map(key => ({key: STATUS[key], value: `${navn.toUpperCase()}_${STATUS[key]}`}))
        .reduce((curr, kv) => {
            curr[kv.key] = kv.value;
            return curr;
        }, {});
};

const defaultState = {
    status: STATUS.initialisert,
    data: {}
};

export function createRestReducer(navn) {
    const actions = getActions(navn);

    return function(state = defaultState, action) {
        switch(action.type) {
            case actions[STATUS.initialisert]:
                return {...state, status: STATUS.initialisert};
            case actions[STATUS.laster]:
                return {...state, status: STATUS.laster};
            case actions[STATUS.lastet]:
                return {data: action.payload, status: STATUS.lastet};
            case actions[STATUS.feilet]:
                return {...state, status: STATUS.feilet};
            default:
                return state;
        }
    };
}

export default combineReducers({
    omrader: createRestReducer('omrader'),
    oversiktStillinger: createRestReducer('oversikt_stillinger'),
    yrkesomrader: createRestReducer('yrkesomrader'),
    yrkesgrupper: createRestReducer('yrkesgrupper'),
    stillinger: createRestReducer('stillinger'),
    totantallstillinger: createRestReducer('totantallstillinger'),
    miljovariabler: createRestReducer('miljovariabler')
});
