import {UPDATE_LOCATION} from 'react-router-redux';

export const LUKK_MODAL = 'LUKK_MODAL';
export const APNE_MODAL = 'APNE_MODAL';

const DEFAULT_STATE = {
    apenmodal: null
};

export default function modalReducer(state = DEFAULT_STATE, action) {
    switch (action.type) {
        case LUKK_MODAL:
        case UPDATE_LOCATION:
            return {... state, apenmodal: null};
        case APNE_MODAL:
            return {... state, apenmodal: action.modalid, feilkode: action.feilkode};
        default:
            return state;
    }
}

export function apneModal(modalid) {
    return {type: APNE_MODAL, modalid};
}

export function lukkModal() {
    return {type: LUKK_MODAL};
}
