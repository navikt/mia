import React from 'react';
import { render } from 'react-dom';
import { createHistory} from 'history';
import { Router, Route, useRouterHistory, IndexRoute } from "react-router";

import Application from "./application";
import Store from './store';
import Provider from './provider';
import LedigeStillinger from "./ledigestillinger/ledigestillinger";
import IkkeFunnet from "./felles/feilside/ikkefunnet";

const history = useRouterHistory(createHistory)({
    basename: '/mia'
});

const store = Store(history);

document.addEventListener('DOMContentLoaded', () => {
    render((
        <Provider store={store}>
            <Router history={history}>
                <Route path="/" component={Application}>
                    <IndexRoute component={LedigeStillinger} />
                    <Route path="ledigestillinger" component={LedigeStillinger} />
                    <Route path="*" component={IkkeFunnet}/>
                </Route>
            </Router>
        </Provider>
    ), document.getElementById('app'));
});