import {expect, React} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import {Hjelpetekst} from './hjelpetekst.jsx';

describe('Hjelpetekst - ', () => {
    var props;
    beforeEach(() => {
        props = {
            id: "testid",
            tekster: {
                tittel: {}
            },
            hjelpetekst: {apenId: "testid"},
            tekst: "Hjelpeteksten er her"
        };
    });

    it('Skal vise hjelpetekst når åpen', () => {
        const wrapper = shallow(<Hjelpetekst {... props}/>);

        expect(wrapper.html().indexOf("Hjelpeteksten er her") !== -1).to.equal(true);
    });

    it('Skal ikke vise hjelpetekst når lukket', () => {
        props.hjelpetekst.apenId = "annenId";

        const wrapper = shallow(<Hjelpetekst {... props}/>);

        expect(wrapper.html().indexOf("Hjelpeteksten er her") !== -1).to.equal(false);
    });
});

