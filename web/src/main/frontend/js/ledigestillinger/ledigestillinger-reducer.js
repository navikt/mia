import {combineReducers} from "redux";
import oversiktReducer from "./oversikt/ledigestillinger-oversikt-reducer";
import bransjeReducer from "./bransje/bransje-reducer";

const reducers = combineReducers({
    oversikt: oversiktReducer,
    bransje: bransjeReducer
});

export default reducers;