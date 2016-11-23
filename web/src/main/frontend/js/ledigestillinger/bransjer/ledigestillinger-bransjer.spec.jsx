import {expect} from '../../../test/test-helper';

import BransjeReducer, {actions} from './ledigestillinger-bransjer-reducer';

describe('bransje-reducer', () => {
    it('skal sette valgteyrkesgrupper til ikke inneholde 1 når vi kaller action "YRKESGRUPPE_DESELECT med payload 1', () => {
        const newState = BransjeReducer({valgteyrkesgrupper: [1,2,3,4,5,6,7]}, { type: actions.yrkesgruppedeselect, payload: 1 });
        expect(newState.valgteyrkesgrupper).to.not.include(1);
    });

    it('skal sette valgteyrkesgrupper til å inneholde 1 når vi kaller action "YRKESGRUPPE_SELECT med payload 1', () => {
        const newState = BransjeReducer({valgteyrkesgrupper: [2,3,4,5,6,7]}, { type: actions.yrkesgruppeselect, payload: 1 });
        expect(newState.valgteyrkesgrupper).to.include(1);
    });

    it("skal sette valgtyrkesomrade til 1 når vi kaller action YRKESOMRADE_SELECT med payload 1", () => {
        const newState = BransjeReducer({}, { type: actions.yrkesomradeselect, payload:  1});
        expect(newState.valgtyrkesomrade).to.equal(1);
    });
});