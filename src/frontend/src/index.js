import './polyfills';

import React from 'react';
import ReactDOM from 'react-dom';
import Application from './application';
import createStore from './store';
import Provider from './provider';


if (!global._babelPolyfill) {
    require('babel-polyfill')
}

const store = createStore();

ReactDOM.render(
  <Provider store={store}>
    <Application />
  </Provider>,
  document.getElementById('root'),
);
