import { combineReducers } from 'redux';
import ledigeStillingerReducer from './ledigestillinger/ledigestillinger-reducer';
import teksterReducer from './felles/tekster/tekster-reducer';
import restReducer from './felles/rest/rest-reducer';
import modalReducer from './felles/modal/modal-reducer';

const reducers = combineReducers({
  ledigestillinger: ledigeStillingerReducer,
  tekster: teksterReducer,
  modal: modalReducer,
  rest: restReducer,
});

export default reducers;
