import {expect} from '../../../test/test-helper';

import {datoStigende} from './date-utils';
import {erDevUrl} from './dev';

describe('felles-utils', () => {
    it('skal sortere dato stigende', () => {
        const datoLengesiden = "2011-01-01";
        const datoLengeTil = "2017-12-31";

        expect(datoStigende(datoLengesiden, datoLengeTil)).to.equal(-1);
        expect(datoStigende(datoLengeTil, datoLengesiden)).to.equal(1);
    });

    it('skal finne ut om url en utvilkerUrl', () => {
        const localurl = "http://a34duvw25920.devillo.no:8486/mia/ledigestillinger";
        const t1url = "https://modapp-t1.adeo.no/mia";
        expect(erDevUrl(localurl)).to.be.true;
        expect(erDevUrl(t1url)).to.be.false;
    });
});
