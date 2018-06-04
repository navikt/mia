import { expect } from '../../../test/test-helper';

import Reducer, { apneHjelpetekst, lukkHjelpetekster } from './hjelpetekst-reducer';

describe('hjelpetekst reducer', () => {
  it('ingen åpne by default', () => {
    const stateEtter = Reducer(undefined, {});

    expect(stateEtter.apenId).to.eql(null);
  });

  it('skal sette riktig åpen hjelpetekst id', () => {
    const action = apneHjelpetekst('123');
    const stateEtter = Reducer({ apenId: null }, action);

    expect(stateEtter).to.eql({ apenId: '123' });
  });

  it('skal lukke åpen hjelpetekst', () => {
    const action = lukkHjelpetekster();
    const stateEtter = Reducer({ apenId: '123' }, action);

    expect(stateEtter).to.eql({ apenId: null });
  });
});
