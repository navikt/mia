import {expect} from '../../../test/test-helper';

import BransjeReducer, {actions} from './ledigestillinger-bransjer-reducer';

describe('bransje-reducer', () => {
    it("skal sette bransjevalg til 1 når vi kaller action BRANSJE_VALGT med payload 1", () => {
        const newState = BransjeReducer({}, { type: actions.bransjevalg, payload:  1});
        expect(newState.bransjevalg).to.equal(1);
    });

    it("skal sette bransjevalg til 'alle' når vi kaller action BRANSJE_VALGT med payload 'alle'", () => {
        const newState = BransjeReducer({}, { type: actions.bransjevalg, payload: 'alle'});
        expect(newState.bransjevalg).to.equal('alle');
    });
});
