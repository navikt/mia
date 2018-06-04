import 'whatwg-fetch';
import 'core-js/shim';

if (!global.Intl) {
  require('intl');
  require('intl/locale-data/jsonp/nb-NO.js');
}
