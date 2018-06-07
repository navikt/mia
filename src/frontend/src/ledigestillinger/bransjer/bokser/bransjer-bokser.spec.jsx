import React from 'react';
import { shallow } from 'enzyme';
import { Bokser } from './ledigestillinger-bransjer-bokser';
import BransjeBoks from './ledigestillinger-bransjer-boks';

it('bransjer skal rendre 2 bokser', () => {
    const yrkesgrupper = [{
        antallStillinger: 123,
        id: "1",
        navn: "Handel, kundeservice, restaurant og reiseliv",
        parent: null,
        strukturkode: null
    },
        {
            antallStillinger: 456,
            id: "2",
            navn: "Reiseliv, hotell og overnatting",
            parent: null,
            strukturkode: null
        }
    ];

    const valgteYrkesgrupper = ["1", "2"];

    const wrapper = shallow(<Bokser onClick={null} yrkesgrupper={yrkesgrupper} valgteyrkesgrupper={valgteYrkesgrupper} />);
    expect(wrapper.find(BransjeBoks).length).toEqual(2);
});