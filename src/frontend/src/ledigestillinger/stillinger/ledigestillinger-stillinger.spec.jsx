import React from 'react';
import {shallow} from 'enzyme';
import StillingerPanel from './ledigestillinger-stillinger-panel';
import StillingTabell from './ledigestillinger-stillinger-tabell';

describe('ledigestillinger-stillinger', () => {
    describe('panel', () => {
        it("Skal vise ingen ledige stillinger dersom antall stillinger er 0", () => {
            const props = { yrkesgruppe: { stillinger: [] } };
            const wrapper = shallow(<StillingerPanel {...props} />);
            expect(wrapper.find('FormattedMessage[id="ledigestillinger.stillinger.ingenstillinger"]').length).toEqual(1);
        });

        it("skal rendre stillingstabell hvis antall stillinger er større enn 0", () => {
            const props = { yrkesgruppe: { stillinger: [{navn: 'stilling1', id: 'stilling1'}] } };
            const wrapper = shallow(<StillingerPanel {...props} />);
            expect(wrapper).toContainReact(<StillingTabell stillinger={props.yrkesgruppe.stillinger} />);
        });
    });
});
