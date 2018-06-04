import { expect } from '../../../test/test-helper';

import Reducer, { settValg } from './switcher-reducer';

describe('switcher reducer', () => {
  it('skal legge til at en ting er i valg2', () => {
    const action = settValg('switcher1', 2);
    const stateEtter = Reducer(undefined, action);

    expect(stateEtter).to.eql({ switcher1: 2 });
  });


  it('skal legge til at enda en graf har visning satt', () => {
    const stateFor = {
      switcher1: 2,
    };

    const action = settValg('switcher2', 3);
    const stateEtter = Reducer(stateFor, action);

    const forventet = {
      switcher1: 2,
      switcher2: 3,
    };

    expect(stateEtter).to.eql(forventet);
  });


  it('skal oppdatere valg', () => {
    const stateFor = {
      switcher1: 2,
      switcher2: 3,
    };

    const action = settValg('switcher1', 1);
    const stateEtter = Reducer(stateFor, action);

    const forventet = {
      switcher1: 1,
      switcher2: 3,
    };

    expect(stateEtter).to.eql(forventet);
  });
});
