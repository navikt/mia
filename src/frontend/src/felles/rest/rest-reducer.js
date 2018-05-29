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

const defaultStateObj = {
    status: STATUS.initialisert,
    data: {}
};

const defaultStateList = {
    status: STATUS.initialisert,
    data: []
};

export function createRestReducer(navn, initialState = defaultStateObj) {
    const actions = getActions(navn);

    return function(state = initialState, action) {
        switch(action.type) {
            case actions[STATUS.initialisert]:
                return {...state, status: STATUS.initialisert};
            case actions[STATUS.laster]:
                return {...state, status: STATUS.laster};
            case actions[STATUS.lastet]:
                return {data: action.payload, status: STATUS.lastet};
            case actions[STATUS.feilet]:
                return {...state, status: STATUS.feilet, data: action.payload};
            default:
                return state;
        }
    };
}

export default combineReducers({
    omrader: createRestReducer('omrader', defaultStateList),
    oversiktStillinger: createRestReducer('oversikt_stillinger'),
    yrkesomrader: createRestReducer('yrkesomrader', defaultStateList),
    yrkesgrupper: createRestReducer('yrkesgrupper', defaultStateList),
    stillinger: createRestReducer('stillinger', defaultStateList),
    fylkergeojson: createRestReducer('fylkergeojson'),
    kommunergeojson: createRestReducer('kommunergeojson'),
    totantallstillinger: createRestReducer('totantallstillinger'),
    miljovariabler: createRestReducer('miljovariabler', defaultStateList),
    statistikk: createRestReducer('statistikk')
});
