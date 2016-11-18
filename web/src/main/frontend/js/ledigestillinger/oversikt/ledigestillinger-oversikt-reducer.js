const initialState = {
    visKart: true
};

export const actions = {
    vis_kart: "VIS_KART",
    vis_tabell: "VIS_TABELL"
};

const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actions.vis_kart:
            return {...state, visKart: true};
        case actions.vis_tabell:
            return {...state, visKart: false};
        default:
            return state;
    }
};

export default reducer;