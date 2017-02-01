const APNE_HJELPETEKST = 'APNE_HJELPETEKST';
const LUKK_APEN_HJELPETEKST = 'LUKK_APEN_HJELPETEKST';

const defaultState = {
    apenId: null
};

export default function hjelpetekstReducer(state = defaultState, action) {
    switch (action.type) {
        case APNE_HJELPETEKST:
            return {apenId: action.id};
        case LUKK_APEN_HJELPETEKST:
            return {apenId: null};
        default:
            return state;
    }
}


export function apneHjelpetekst(hjelpetekstId) {
    return {
        type: APNE_HJELPETEKST,
        id: hjelpetekstId
    };
}

export function lukkHjelpetekster() {
    return {
        type: LUKK_APEN_HJELPETEKST
    };
}