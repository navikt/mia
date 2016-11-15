import React from 'react';
import { render } from 'react-dom';
import { createHistory} from 'history';
import { Router, Route, IndexRoute, useRouterHistory } from "react-router";

import Application from "./application";
import Store from './store';
import Provider from './provider';

const history = useRouterHistory(createHistory)({
    basename: '/mia'
});

const store = Store(history);

document.addEventListener('DOMContentLoaded', () => {
    render((
        <Provider store={store}>
            <Router history={history}>
                <Route path="/" component={Application}>
                </Route>
            </Router>
        </Provider>
    ), document.getElementById('app'));
});