import React from "react";
import ReactDOM from "react-dom"
import { Router, Route, IndexRoute, useRouterHistory } from "react-router";
import { createHistory } from "history";
import HelloWorld from "./HelloWorld";

const history = useRouterHistory(createHistory)({
    basename: '/mia'
});

const App = () => (
    <Router history={history}>
        <Route path="/">
            <IndexRoute component={ HelloWorld }/>
        </Route>
    </Router>
);

ReactDOM.render(<App />, document.getElementById("app"));
