import {createStore, applyMiddleware, compose} from 'redux';
import thunkMiddleware from 'redux-thunk';
import Reducers from './reducers.js';

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

function getStoreCompose() {
    return composeEnhancers(
        applyMiddleware(thunkMiddleware)
    );
}

export default function create() {
    let composed = getStoreCompose();
    return composed(createStore)(Reducers, {});
}
