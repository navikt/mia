import {createStore, applyMiddleware, compose} from 'redux';
import thunkMiddleware from 'redux-thunk';
import Reducers from './reducers.js';

function getStoreCompose() {
    return compose(
        applyMiddleware(thunkMiddleware)
    );
}

export default function create() {
    let composed = getStoreCompose();
    return composed(createStore)(Reducers, {});
}