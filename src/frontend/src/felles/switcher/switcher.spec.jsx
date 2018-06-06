import React from 'react';
import {shallow} from 'enzyme';
import {Switcher} from './switcher.jsx';

describe('HovedmenyVisning', () => {

    const lagProps = () => ({
        id: '123',
        switchere: {},
        elementer: [{
            tekst: 'element1',
            element: <div id="element1" />
        }, {
            tekst: 'element2',
            element: <div id="element2"/>
        }, {
            tekst: 'element3',
            element: <div id="element3"/>
        }]
    });

    it('lager en knapp per element', function () {
        let props = lagProps();

        const element = shallow(<Switcher {...props} />);
        expect(element.find('SwitcherKnapp').length).toEqual(3);
    });

    it('viser første element by default', function () {
        let props = lagProps();

        const element = shallow(<Switcher {...props} />);
        expect(element.find('#element1').length).toEqual(1);
    });

    it('viser riktig valgt element', function () {
        let props = lagProps();
        props.switchere = {'123': 2};

        const element = shallow(<Switcher {...props} />);
        expect(element.find('#element3').length).toEqual(1);
    });

    it('kan velge element som skal vises ved å klikke på knapp', function () {
        let settValg = jest.fn();

        let props = lagProps();
        props.settValg = settValg;

        const element = shallow(<Switcher {...props} />);
        element.find('SwitcherKnapp').at(2).simulate('click');

        expect(settValg).toHaveBeenCalledWith('123', 2);
    });

});