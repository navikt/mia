import {combineReducers} from "redux";
import oversiktReducer from "./oversikt/ledigestillinger-oversikt-reducer";
import bransjeReducer from "./bransjer/ledigestillinger-bransjer-reducer";

const reducers = combineReducers({
    oversikt: oversiktReducer,
    bransje: bransjeReducer
});

export default reducers;