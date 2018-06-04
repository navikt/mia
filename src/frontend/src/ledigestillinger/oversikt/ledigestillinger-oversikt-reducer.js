export const initialState = {
  visKart: true,
  valgteFylker: [],
  valgteKommuner: [],
  valgteFylkerModal: [],
  valgteKommunerModal: [],
};

export const actions = {
  vis_kart: 'VIS_KART',
  vis_tabell: 'VIS_TABELL',
  reset_valg: 'RESET_VALG',
  velg_kommune: 'VELG_KOMMUNE',
  avvelg_kommune: 'AVVELG_KOMMUNE',
  velg_fylke: 'VELG_FYLKE',
  modal_velg_kommune: 'MODAL_OMRADE_VELG_KOMMUNE',
  modal_avvelg_kommune: 'MODAL_OMRADE_AVVELG_KOMMUNE',
  modal_velg_fylke: 'MODAL_OMRADE_VELG_FYLKE',
  modal_avvelg_fylke: 'MODAL_OMRADE_AVVELG_FYLKE',
  modal_reset: 'MODAL_OMRADE_RESET',
  modal_lagre: 'MODAL_OMRADE_LAGRE',
};

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case actions.vis_kart:
      return {
        ...state, visKart: true, valgteKommuner: [], valgteFylker: [],
      };
    case actions.vis_tabell:
      return { ...state, visKart: false };
    case actions.modal_reset:
      return { ...state, valgteFylkerModal: state.valgteFylker, valgteKommunerModal: state.valgteKommuner };
    case actions.modal_lagre:
      return { ...state, valgteFylker: state.valgteFylkerModal, valgteKommuner: state.valgteKommunerModal };
    case actions.modal_velg_fylke:
      return { ...state, valgteFylkerModal: state.valgteFylkerModal.concat(action.payload) };
    case actions.modal_avvelg_fylke:
      return { ...state, valgteFylkerModal: state.valgteFylkerModal.filter(fylke => fylke !== action.payload) };
    case actions.modal_velg_kommune:
      return { ...state, valgteKommunerModal: state.valgteKommunerModal.concat(action.payload) };
    case actions.modal_avvelg_kommune:
      return { ...state, valgteKommunerModal: state.valgteKommunerModal.filter(kommune => kommune !== action.payload) };
    case actions.reset_valg:
      return { ...state, valgteFylker: [], valgteKommuner: [] };
    case actions.velg_kommune:
      return { ...state, valgteFylker: [], valgteKommuner: state.valgteKommuner.concat(action.payload) };
    case actions.avvelg_kommune:
      return { ...state, valgteKommuner: state.valgteKommuner.filter(kommuneid => kommuneid !== action.payload) };
    case actions.velg_fylke:
      return { ...state, valgteKommuner: [], valgteFylker: [action.payload] };
    default:
      return state;
  }
};

export default reducer;
