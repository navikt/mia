const initialState = {
    valgteyrkesgrupper: [],
    valgtyrkesomrade: 'alle'
};

export const actions = {
    yrkesgruppeselect: "YRKESGRUPPE_SELECT",
    yrkesgruppedeselect: "YRKESGRUPPE_DESELECT",
    yrkesomradeselect: "YRKESOMRADE_SELECT"
};

const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actions.yrkesgruppeselect:
            return { ...state, valgteyrkesgrupper: state.valgteyrkesgrupper.concat(action.payload) };
        case actions.yrkesgruppedeselect:
            return { ...state, valgteyrkesgrupper: state.valgteyrkesgrupper.filter(yrkesgruppe => yrkesgruppe !== action.payload) };
        case actions.yrkesomradeselect:
            return { ...state, valgtyrkesomrade: action.payload, valgteyrkesgrupper: [] };
        default:
            return state;
    }
};

export default reducer;