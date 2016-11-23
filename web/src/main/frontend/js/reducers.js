import {combineReducers} from "redux";
import ledigeStillingerReducer from "./ledigestillinger/ledigestillinger-reducer";
import teksterReducer from './felles/tekster/tekster-reducer';

const reducers = combineReducers({
    ledigestillinger: ledigeStillingerReducer,
    tekster: teksterReducer
});

export default reducers;