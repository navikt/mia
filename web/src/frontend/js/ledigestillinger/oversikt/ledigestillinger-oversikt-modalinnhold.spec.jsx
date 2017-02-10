import {expect, React, sinon} from '../../../test/test-helper';
import {shallow} from "enzyme";
import {actions} from './ledigestillinger-oversikt-reducer';
import { Modalinnhold, Omrade } from './ledigestillinger-oversikt-modalinnhold';

const lagOmrade = (id) => ({
    id: id,
    navn: "omrade" + id,
    underomrader: []
});


describe('ledigestillinger modalinnhold', function() {
    it('skal rendre alle fylker', () => {
        const fylke1 = lagOmrade("10");
        const fylke2 = lagOmrade("20");

        const props = {
            omrader: [fylke1, fylke2],
            valgteFylker: [fylke1.id]
        };
        const modalinnhold = shallow(<Modalinnhold {...props}/>);
        expect(modalinnhold.find(Omrade).length).to.eql(2);
    });

    describe('toggleKommune', () => {
        it('skal velge kommune om den ikke allerede er valgt', () => {
            const kommune = lagOmrade(1);
            const props = {
                dispatch: sinon.spy(),
                valgteKommuner: [kommune.id]
            };

            const modalinnhold = new Modalinnhold(props);
            modalinnhold.toggleKommune(kommune.id);
            expect(props.dispatch).to.have.been.calledWith({type: actions.modal_avvelg_kommune, payload: kommune.id});
        });

        it('skal avvelge kommune om den er allerede valgt', () => {
            const kommune = lagOmrade(1);
            const props = {
                dispatch: sinon.spy(),
                valgteKommuner: []
            };

            const modalinnhold = new Modalinnhold(props);
            modalinnhold.toggleKommune(kommune.id);
            expect(props.dispatch).to.have.been.calledWith({type: actions.modal_velg_kommune, payload: kommune.id});
        });
    });

    describe('toggleFylke', () => {
        it('skal velge fylke om den ikke allerede er valgt', () => {
            const fylke = lagOmrade(1);
            const props = {
                dispatch: sinon.spy(),
                valgteFylker: [fylke.id],
                omrader: [fylke]
            };

            const modalinnhold = new Modalinnhold(props);
            modalinnhold.toggleFylke(fylke.id);
            expect(props.dispatch).to.have.been.calledWith({type: actions.modal_avvelg_fylke, payload: fylke.id});
        });

        it('skal avvelge fylke om den er allerede valgt', () => {
            const fylke = lagOmrade(1);
            const props = {
                dispatch: sinon.spy(),
                valgteFylker: [],
                omrader: [fylke]
            };

            const modalinnhold = new Modalinnhold(props);
            modalinnhold.toggleFylke(fylke.id);
            expect(props.dispatch).to.have.been.calledWith({type: actions.modal_velg_fylke, payload: fylke.id});
        });

        it('skal avvelge kommuner i fylket når fylket avvelges', () => {
            const fylke = lagOmrade(1);
            const kommune = lagOmrade(10);
            fylke.underomrader = [kommune];
            const props = {
                dispatch: sinon.spy(),
                valgteFylker: [fylke.id],
                valgteKommuner: [kommune.id],
                omrader: [fylke]
            };

            const modalinnhold = new Modalinnhold(props);
            modalinnhold.toggleFylke(fylke.id);
            expect(props.dispatch).to.have.been.calledWith({type: actions.modal_avvelg_fylke, payload: fylke.id});
            expect(props.dispatch).to.have.been.calledWith({type: actions.modal_avvelg_kommune, payload: kommune.id});
        });
    });
});

