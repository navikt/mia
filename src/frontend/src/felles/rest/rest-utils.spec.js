import React from 'react';
import * as utils from './rest-utils';

describe('rest_utils', () => {
  beforeAll(() => {
    console.error = () => null; // eslint-disable-line no-console
  });

  describe('sjekkStatusKode', () => {
    it('skal returnere responsen ved status 200', () => {
      const response = { status: 200, data: '123' };
      expect(utils.sjekkStatuskode(response)).toEqual(response);
    });

    it('skal kaste feil ved status 401', () => {
      const response = { status: 401, data: '123', statusText: 'feilfeil' };
      expect(() => utils.sjekkStatuskode(response)).toThrow();
    });
  });


  describe('handterFeil', () => {
    it('skal dispatche riktig actiontype om generell feil', () => {
      const dispatch = jest.fn();
      const feilHandterer = utils.handterFeil(dispatch, 'ACTION_FEIL');

      const error = new Error('feilfeil');

      feilHandterer(error);

      expect(dispatch).toHaveBeenCalledWith({ type: 'ACTION_FEIL', payload: undefined });
    });

    it('skal dispatche med call_id om det er med i feilen', (done) => {
      const response = { status: 500, json: () => Promise.resolve({ callId: 'callId' }) };

      const dispatch = jest.fn();
      const feilHandterer = utils.handterFeil(dispatch, 'ACTION_FEIL');

      const error = new Error('feilfeil');
      error.response = response;

      feilHandterer(error);

      setTimeout(() => {
        expect(dispatch).toHaveBeenCalledWith({
          type: 'ACTION_FEIL',
          payload: 'callId',
        });
        done();
      });
    });
  });
});
