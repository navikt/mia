import {STATUS} from "../../felles/konstanter";

const initialState = {
    valgteyrkesgrupper: [],
    valgtyrkesomrade: 'alle',
    yrkesgrupper: {
        status: STATUS.initialisert,
        data: []
    },
    yrkesomrader: {
        status: STATUS.initialisert,
        data: []
    }
};

export const actions = {
    yrkesgruppeselect: "YRKESGRUPPE_SELECT",
    yrkesgruppedeselect: "YRKESGRUPPE_DESELECT",
    yrkesomradeselect: "YRKESOMRADE_SELECT",
    laster_yrkesgrupper: "LASTER_YRKESGRUPPER",
    lastet_yrkesgrupper: "LASTET_YRKESGRUPPER",
    feilet_yrkesgrupper: "FEILET_YRKESGRUPPER",
    laster_yrkesomrader: "LASTER_YRKESOMRADER",
    lastet_yrkesomrader: "LASTET_YRKESOMRADER",
    feilet_yrkesomrader: "FEILET_YRKESOMRADER",

};

const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actions.yrkesgruppeselect:
            return { ...state, valgteyrkesgrupper: state.valgteyrkesgrupper.concat(action.payload) };
        case actions.yrkesgruppedeselect:
            return { ...state, valgteyrkesgrupper: state.valgteyrkesgrupper.filter(yrkesgruppe => yrkesgruppe !== action.payload) };
        case actions.yrkesomradeselect:
            return { ...state, valgtyrkesomrade: action.payload, valgteyrkesgrupper: [] };
        case actions.laster_yrkesgrupper:
            return {...state, yrkesgrupper: { ...state.yrkesgrupper, status: STATUS.laster } };
        case actions.lastet_yrkesgrupper:
            return {...state, yrkesgrupper: { data: action.payload, status: STATUS.lastet } };
        case actions.feilet_yrkesgrupper:
            return {...state, yrkesgrupper: { ...state.yrkesgrupper, status: STATUS.feilet } };
        case actions.laster_yrkesomrader:
            return {...state, yrkesomrader: { ...state.yrkesomrader, status: STATUS.laster } };
        case actions.lastet_yrkesomrader:
            return {...state, yrkesomrader: { data: action.payload, status: STATUS.lastet } };
        case actions.feilet_yrkesomrader:
            return {...state, yrkesomrader: { ...state.yrkesomrader, status: STATUS.feilet } };
        default:
            return state;
    }
};

export default reducer;