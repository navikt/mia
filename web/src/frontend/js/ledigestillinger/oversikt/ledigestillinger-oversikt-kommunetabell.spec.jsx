import {expect, React} from '../../../test/test-helper';
import {shallow} from "enzyme";

import { KommuneTabell, KommuneTabellRad } from './ledigestillinger-oversikt-kommunetabell';

const kommune1 = {
    id: "1",
    navn: "kommune1"
};

const kommune2 = {
    id: "2",
    navn: "kommune2"
};

const stillingerKommune1 = {
    id: "1",
    antallLedige: null,
    antallStillinger: 5
};

const stillingerKommune2 = {
    id: 2,
    antallLedige: 210,
    antallStillinger: 212
};

describe('ledigestillinger oversikt tabell', function() {
    it('skal lage en rad per kommune', () => {
        const props = {
            fylke: "et fylke",
            kommuner: [kommune1, kommune2],
            stillinger: [stillingerKommune1, stillingerKommune2]
        };

        const wrapper = shallow(<KommuneTabell {...props}/>);
        expect(wrapper.find(KommuneTabellRad).length).to.eql(2);
    });

    describe('KommuneTabellRad', () => {
        it('skal vise null som <4 (sensurert data)', () => {
            const kommune = {...kommune1, ...stillingerKommune1};
            const tabellRad = shallow(<KommuneTabellRad  kommune={kommune}/>);
            expect(tabellRad).to.contain("<4");
        });

        it('skal vise antall stillinger og antall ledige', () => {
            const kommune = {...kommune2, ...stillingerKommune2};
            const tabellRad = shallow(<KommuneTabellRad  kommune={kommune}/>);
            expect(tabellRad).to.contain(stillingerKommune2.antallLedige);
            expect(tabellRad).to.contain(stillingerKommune2.antallStillinger);
        });
    });
});

