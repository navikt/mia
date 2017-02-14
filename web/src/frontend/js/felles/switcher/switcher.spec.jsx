import {expect, React} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import sinon from 'sinon';
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
        expect(element.find('SwitcherKnapp').length).to.eql(3);
    });

    it('viser første element by default', function () {
        let props = lagProps();

        const element = shallow(<Switcher {...props} />);
        expect(element.find('#element1').length).to.eql(1);
    });

    it('viser riktig valgt element', function () {
        let props = lagProps();
        props.switchere = {'123': 2};

        const element = shallow(<Switcher {...props} />);
        expect(element.find('#element3').length).to.eql(1);
    });

    it('kan velge element som skal vises ved å klikke på knapp', function () {
        let settValg = sinon.spy();

        let props = lagProps();
        props.settValg = settValg;

        const element = shallow(<Switcher {...props} />);
        element.find('SwitcherKnapp').at(2).simulate('click');

        expect(settValg).to.have.been.calledWith('123', 2);
    });

});