import {STATUS} from "../../felles/konstanter";

const initialState = {
    visKart: true,
    valgtFylke: null,
    valgtKommune: null,
    kommunedata: {
        status: STATUS.initialisert,
        stillinger: []
    }
};

export const actions = {
    vis_kart: "VIS_KART",
    vis_tabell: "VIS_TABELL",
    velg_fylke: "VELG_FYLKE",
    velg_kommune: "VELG_KOMMUNE",
    laster_oversikt_stillinger: "LASTER_OVERSIKT_STILLINGER",
    lastet_oversikt_stillinger: "LASTET_OVERSIKT_STILLINGER",
    feilet_oversikt_stillinger: "FEILET_OVERSIKT_STILLINGER"
};

const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actions.vis_kart:
            return {...state, visKart: true};
        case actions.vis_tabell:
            return {...state, visKart: false};
        case actions.velg_fylke:
            return {...state, valgtFylke: action.payload, valgtKommune: null};
        case actions.velg_kommune:
            return {...state, valgtKommune: action.payload};
        case actions.laster_oversikt_stillinger:
            return {...state, kommunedata: {...state.kommunedata, status: STATUS.laster}};
        case actions.lastet_oversikt_stillinger:
            return {...state, kommunedata: {stillinger: action.payload, status: STATUS.lastet}};
        case actions.feilet_oversikt_stillinger:
            return {...state, kommunedata: {...state.kommunedata, status: STATUS.feilet}};
        default:
            return state;
    }
};

export default reducer;