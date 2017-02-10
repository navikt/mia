import {expect, React, sinon} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import StillingerPanel from './ledigestillinger-stillinger-panel'
import {hentStillinger, hentAntallStillingerForYrkesgruppe} from './ledigestillinger-stillinger-actions';

describe('ledigestillinger-stillinger', () => {
    describe('panel', () => {
        it("Skal vise ingen ledige stillinger dersom antall stillinger er 0", () => {
            const props = { yrkesgruppe: { stillinger: [] } };
            const wrapper = shallow(<StillingerPanel {...props} />);
            expect(wrapper.find('FormattedMessage[id="ledigestillinger.stillinger.ingenstillinger"]').length).to.equal(1);
        });

        it("skal rendre stillingstabell hvis antall stillinger er større enn 0", () => {
            const props = { yrkesgruppe: { stillinger: ["stilling 1"] } };
            const wrapper = shallow(<StillingerPanel {...props} />);
            expect(wrapper.find('StillingTabell').length).to.equal(1);
        });
    });

    describe('actions', function() {
        beforeEach(() => {
            this.fetch = window.fetch;
            global.Headers = class Headers {
                append() {}
            };

            this.getDefaultState = () => ({
                ledigestillinger: {
                    bransje: {
                        valgteyrkesgrupper: [],
                        valgtyrkesomrade: null
                    },
                    oversikt: {
                        valgteKommuner: [],
                        valgteFylker: []
                    }
                }
            });
            global.fetch = () => Promise.resolve({});
            this.fetchSpy = sinon.spy(global, "fetch");
        });

        afterEach(() => {
            window.fetch = this.fetch;
        });

        describe('hentStillinger', () => {
            it('hentStillinger skal hente stillinger fra /mia/rest/bransjer/stillinger', () => {
                const dispatch = sinon.spy();
                const state = this.getDefaultState();
                hentStillinger()(dispatch, () => state);
                expect(this.fetchSpy).to.not.have.been.called.once;
            });

            it('hentStillinger skal hente stillinger fra /mia/rest/bransjer/stillinger', () => {
                const dispatch = sinon.spy();
                const state = this.getDefaultState();
                state.ledigestillinger.bransje.valgteyrkesgrupper = [123];
                hentStillinger()(dispatch, () => state);
                expect(this.fetchSpy).to.have.been.calledWith('/mia/rest/bransjer/stillinger?yrkesgrupper[]=123');
            });
        });
    });
});
