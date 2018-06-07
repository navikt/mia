import React from 'react';
import ReactDOM from 'react-dom';
import Application from './application';
import createStore from './store';
import Provider from './provider';
import 'whatwg-fetch';

const store = createStore();

ReactDOM.render(
  <Provider store={store}>
    <Application />
  </Provider>,
  document.getElementById('root'),
);
