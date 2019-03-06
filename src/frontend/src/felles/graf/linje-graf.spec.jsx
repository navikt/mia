import React from 'react';
import {shallow} from 'enzyme';
import {LinjeGraf} from './linje-graf.jsx';

describe('LinjeGraf', () => {
    beforeAll(() => {
        Object.values = o => Object.keys(o).map(a => o[a]);
    });

    it('returnerer ingenting om den ikke har fått data', function () {
        const element = shallow(<LinjeGraf />);

        expect(element.type()).toEqual(null);
    });
});
