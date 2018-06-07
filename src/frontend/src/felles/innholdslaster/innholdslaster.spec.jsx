import React from 'react';
import {shallow} from 'enzyme';
import Innholdslaster from './innholdslaster';
import {STATUS} from '../../felles/konstanter';

describe('Innholdslaster', () => {
    it('skal vise laster om noen laster', () => {
        const laster1 = {status: STATUS.laster, data: {}};
        const laster2 = {status: STATUS.laster, data: {}};
        const lastet = {status: STATUS.lastet, data: {}};

        const wrapper = shallow(<Innholdslaster avhengigheter={[laster1, laster2, lastet]}/>);
        expect(wrapper.find('NavFrontendSpinner').length).toBe(1);
    });

    it('skal vise child om alt gikk greit', () => {
        const lastet = {status: STATUS.lastet, data: {}};
        const lastet2 = {status: STATUS.lastet, data: {}};

        const wrapper = shallow((
            <Innholdslaster avhengigheter={[lastet, lastet2]}>
                DETTE SKAL VISES
            </Innholdslaster>)
        );
        expect(wrapper.text()).toEqual("DETTE SKAL VISES");
    });
});
