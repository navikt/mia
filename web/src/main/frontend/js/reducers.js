import {combineReducers} from "redux";
import ledigeStillingerReducer from "./ledigestillinger/ledigestillinger-reducer";
import teksterReducer from "./felles/tekster/tekster-reducer";
import kodeverkReducer from "./felles/kodeverk/kodeverk-reducer";

const reducers = combineReducers({
    ledigestillinger: ledigeStillingerReducer,
    kodeverk: kodeverkReducer,
    tekster: teksterReducer
});

export default reducers;