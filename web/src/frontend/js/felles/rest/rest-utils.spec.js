import {expect} from '../../../test/test-helper';
import sinon from 'sinon';
import * as utils from './rest-utils';

describe('rest_utils', () => {
    before(() => {
        console.error = () => null; // eslint-disable-line no-console
    });

    describe('sjekkStatusKode', () => {
        it('skal returnere responsen ved status 200', () => {
            let response = {status: 200, data: '123'};
            expect(utils.sjekkStatuskode(response)).to.eql(response);
        });

        it('skal kaste feil ved status 401', () => {
            let response = {status: 401, data: '123', statusText: 'feilfeil'};
            expect(() => utils.sjekkStatuskode(response)).to.throw(Error, 'feilfeil');
        });

    });


    describe('handterFeil', () => {
        it('skal dispatche riktig actiontype med feilmeldingen som data om generell feil', () => {
            let dispatch = sinon.spy();
            const feilHandterer = utils.handterFeil(dispatch, 'ACTION_FEIL');

            const error = new Error('feilfeil');

            feilHandterer(error);

            expect(dispatch).to.have.been.calledWith({type: 'ACTION_FEIL', payload: 'Error: feilfeil'});
        });
    });
});