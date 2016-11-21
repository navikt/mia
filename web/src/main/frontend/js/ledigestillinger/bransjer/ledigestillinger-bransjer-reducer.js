const initialState = {
    bransjevalg: 'alle'
};

export const actions = {
    bransjevalg: "BRANSJE_VALGT"
};

const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actions.bransjevalg:
            return {...state, bransjevalg: action.payload};
        default:
            return state;
    }
};

export default reducer;