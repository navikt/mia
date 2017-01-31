import {createStore, applyMiddleware, compose} from 'redux';
import {persistState} from 'redux-devtools';
import thunkMiddleware from 'redux-thunk';
import {routerMiddleware} from 'react-router-redux';
import DevTools from './devtools.jsx';
import Reducers from './reducers.js';
import {erDev} from "./felles/utils/dev";

function getDevStoreCompose(history) {
    return compose(
        applyMiddleware(thunkMiddleware, routerMiddleware(history)),
        DevTools.instrument(),
        persistState(getDebugSessionKey())
    );
}

function getDebugSessionKey() {
    const matches = window.location.href.match(/[?&]debug_session=([^&]+)\b/);
    return (matches && matches.length > 0) ? matches[1] : null;
}

function getStoreCompose(history) {
    return compose(
        applyMiddleware(thunkMiddleware, routerMiddleware(history))
    );
}

export default function create(history) {
    let composed = erDev() ? getDevStoreCompose(history) : getStoreCompose(history);
    return composed(createStore)(Reducers, {});
}