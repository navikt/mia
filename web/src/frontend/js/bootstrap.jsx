import React from 'react';
import { render } from 'react-dom';
import { createHistory} from 'history';
import { Router, Route, useRouterHistory, IndexRedirect } from "react-router";

import Application from "./application";
import Store from './store';
import Provider from './provider';
import Arbeidsgivere from "./arbeidsgivere/arbeidsgivere";
import LedigeStillinger from "./ledigestillinger/ledigestillinger";
import Rapporter from "./rapporter/rapporter";
import Yrker from "./yrker/yrker";
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
                    <IndexRedirect to="ledigestillinger" />
                    <Route path="ledigestillinger" component={LedigeStillinger} />
                    <Route path="yrker" component={Yrker} />
                    <Route path="arbeidsgivere" component={Arbeidsgivere} />
                    <Route path="rapporter" component={Rapporter} />
                    <Route path="*" component={IkkeFunnet}/>
                </Route>
            </Router>
        </Provider>
    ), document.getElementById('app'));
});