import { datoStigende } from './date-utils';
import { erDevUrl } from './dev';

describe('felles-utils', () => {
  it('skal sortere dato stigende', () => {
    const datoLengesiden = '2011-01-01';
    const datoLengeTil = '2017-12-31';

    expect(datoStigende(datoLengesiden, datoLengeTil)).toEqual(-1);
    expect(datoStigende(datoLengeTil, datoLengesiden)).toEqual(1);
  });

  it('skal finne ut om url en utvilkerUrl', () => {
    const localurl = 'http://a34duvw25920.devillo.no:8486/ledigestillinger';
    const t1url = 'https://modapp-t1.adeo.no/';
    expect(erDevUrl(localurl)).toBeTruthy();
    expect(erDevUrl(t1url)).toBeFalsy();
  });
});
