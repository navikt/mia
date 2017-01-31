import {expect, React} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import StillingerPanel from './ledigestillinger-stillinger-panel';

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
});
