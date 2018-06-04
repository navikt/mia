const SWITCHER_SETT_VALG = 'SWITCHER_SETT_VALG';

const defaultState = {};

export default function grafswitchReducer(state = defaultState, action) {
  switch (action.type) {
    case SWITCHER_SETT_VALG:
      return { ...state, [action.switcherId]: action.valgNr };
    default:
      return state;
  }
}

export function settValg(switcherId, valgNr) {
  return {
    type: SWITCHER_SETT_VALG,
    switcherId,
    valgNr,
  };
}
