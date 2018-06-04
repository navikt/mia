import { expect } from '../../../test/test-helper';
import sinon from 'sinon';
import * as utils from './rest-utils';

describe('rest_utils', () => {
  before(() => {
    console.error = () => null; // eslint-disable-line no-console
  });

  describe('sjekkStatusKode', () => {
    it('skal returnere responsen ved status 200', () => {
      const response = { status: 200, data: '123' };
      expect(utils.sjekkStatuskode(response)).to.eql(response);
    });

    it('skal kaste feil ved status 401', () => {
      const response = { status: 401, data: '123', statusText: 'feilfeil' };
      expect(() => utils.sjekkStatuskode(response)).to.throw(Error, 'feilfeil');
    });
  });


  describe('handterFeil', () => {
    it('skal dispatche riktig actiontype om generell feil', () => {
      const dispatch = sinon.spy();
      const feilHandterer = utils.handterFeil(dispatch, 'ACTION_FEIL');

      const error = new Error('feilfeil');

      feilHandterer(error);

      expect(dispatch).to.have.been.calledWith({ type: 'ACTION_FEIL', payload: undefined });
    });

    it('skal dispatche med call_id om det er med i feilen', (done) => {
      const response = { status: 500, json: () => Promise.resolve({ callId: 'callId' }) };

      const dispatch = sinon.spy();
      const feilHandterer = utils.handterFeil(dispatch, 'ACTION_FEIL');

      const error = new Error('feilfeil');
      error.response = response;

      feilHandterer(error);

      setTimeout(() => {
        expect(dispatch).to.have.been.calledWith({
          type: 'ACTION_FEIL',
          payload: 'callId',
        });
        done();
      });
    });
  });
});
