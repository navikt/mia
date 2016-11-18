import {combineReducers} from "redux";
import oversiktReducer from "./oversikt/ledigestillinger-oversikt-reducer";

const reducers = combineReducers({
    oversikt: oversiktReducer
});

export default reducers;