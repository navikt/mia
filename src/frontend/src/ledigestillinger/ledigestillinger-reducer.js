import { combineReducers } from 'redux';
import oversiktReducer from './oversikt/ledigestillinger-oversikt-reducer';
import bransjeReducer from './bransjer/ledigestillinger-bransjer-reducer';
import statistikkReducer from './statistikk/ledigestillinger-statistikk-reducer';

const reducers = combineReducers({
  oversikt: oversiktReducer,
  bransje: bransjeReducer,
  statistikk: statistikkReducer,
});

export default reducers;
