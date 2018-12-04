import { erDev } from './utils/dev';

export const RESTURL = erDev() ? `http://${window.location.hostname}:8800/rest` : '/rest';
export const STATUS = {
  feilet: 'FEILET',
  lastet: 'LASTET',
  laster: 'LASTER',
  initialisert: 'INITIALISERT',
};
export const ALTERNATIV_ALLE = 'alle';

export const EOS_EU = 'EOSEU';
export const RESTEN_AV_VERDEN = 'resten av verden';
