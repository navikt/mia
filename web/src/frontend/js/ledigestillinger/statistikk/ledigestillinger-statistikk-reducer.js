import {combineReducers} from 'redux';
import switcherReducer from './../../felles/switcher/switcher-reducer';

const reducers = combineReducers({
    'switcher': switcherReducer
});

export default reducers;
