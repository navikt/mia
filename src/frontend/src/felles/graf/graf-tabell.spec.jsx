import {expect, React} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import {GrafTabell}  from './graf-tabell.jsx';

describe('GrafTabell - ', () => {
    const props = {
        overskrifter: {
            2015: ['nov', 'des'],
            2016: ['jan']
        },
        rader: [[2,1,1], [1,1,1]],
        serieNavn: ['Din praksis'],
        tabellId: 'sykefravaerstilfeller',
        tabelloverskrift:  'Sykefraværstilfeller i ditt område'
    };

    it('viser tabell', () => {
        const wrapper = shallow(<GrafTabell {... props}/>);
        expect(wrapper.find('table')).to.have.length(1);
    });

    it('viser vedlagt beskrivelse', () => {
        const wrapper = shallow(<GrafTabell {... props}/>);
        expect(wrapper.find('caption').text()).to.contain(props.tabelloverskrift);
    });

    it('kolonner har unikid', () => {
        const wrapper = shallow(<GrafTabell {... props}/>);
        expect(wrapper.find('#sykefravaerstilfeller_0_nov')).to.have.length(1);
    });
});

