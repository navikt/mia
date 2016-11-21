import {combineReducers} from "redux";
import ledigeStillingerReducer from "./ledigestillinger/ledigestillinger-reducer";

const reducers = combineReducers({
    ledigestillinger: ledigeStillingerReducer
});

export default reducers;