import { lukkModal, apneModal } from '../felles/modal/modal-reducer';

export const feilmodalId = 'FEIL_MODAL';

export const lukk_modal = lukkModal(feilmodalId);
export const apne_modal = apneModal(feilmodalId);
