const initialState = {
    visKart: true,
    valgtFylke: "",
    valgtKommune: ""
};

export const actions = {
    vis_kart: "VIS_KART",
    vis_tabell: "VIS_TABELL",
    velg_fylke: "VELG_FYLKE",
    velg_kommune: "VELG_KOMMUNE"
};

const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actions.vis_kart:
            return {...state, visKart: true};
        case actions.vis_tabell:
            return {...state, visKart: false};
        case actions.velg_fylke:
            return {...state, valgtFylke: action.payload, valgtKommune: ""};
        case actions.velg_kommune:
            return {...state, valgtKommune: action.payload};
        default:
            return state;
    }
};

export default reducer;