import {combineReducers} from "redux";
import fylkerReducer from './kodeverk-fylker-reducer';

const KodeverkReducer = combineReducers({
    fylker: fylkerReducer
});

export default KodeverkReducer;