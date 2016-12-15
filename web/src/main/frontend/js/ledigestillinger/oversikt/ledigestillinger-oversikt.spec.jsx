import {expect, sinon, React} from '../../../test/test-helper';
import {shallow} from "enzyme";

import OversiktReducer, {actions, initialState} from './ledigestillinger-oversikt-reducer';
import {Oversikt} from "./ledigestillinger-oversikt";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import Oversiktstabell from "./ledigestillinger-oversikt-tabell";
import {getStillingerTotalt, getKommuneMedData} from './ledigestillinger-oversikt-utils';

describe('ledigestillinger', () => {
    describe('oversikt-reducer', () => {
        it("skal sette visKart til true når vi kaller action VIS_KART", () => {
            const newState = OversiktReducer(initialState, { type: actions.vis_kart });
            expect(newState.visKart).to.be.true;
        });

        it("skal sette visKart til false når vi kaller action VIS_TABELL", () => {
            const newState = OversiktReducer(initialState, { type: actions.vis_tabell });
            expect(newState.visKart).to.be.false;
        });

        describe('modal', () => {
            it("skal sette fylke til valgt fylke når vi kaller MODAL_VELG_FYLKE", () => {
                const fylkeSomVelges = "ET FYLKE";
                const newState = OversiktReducer(initialState, { type: actions.modal_velg_fylke, payload: fylkeSomVelges });
                expect(newState.valgteFylkerModal[0]).to.equal(fylkeSomVelges);
            });

            it("skal sette kommune til valgt kommune når vi kaller MODALVELG_KOMMUNE", () => {
                const kommuneSomVelges = "ET FYLKE";
                const newState = OversiktReducer(initialState, { type: actions.modal_velg_kommune, payload: kommuneSomVelges });
                expect(newState.valgteKommunerModal[0]).to.equal(kommuneSomVelges);
            });

            it("skal kopiere verdier til valgte_fylker og velgte_kommunr når vi kaller MODAL_LAGRE", () => {
                const state = {
                    ...initialState,
                    valgteFylkerModal: ["FYLKE"],
                    valgteKommunerModal: ["KOMMUNE"]
                };

                const newState = OversiktReducer(state, { type: actions.modal_lagre });
                expect(newState.valgteKommuner.length).to.equal(1);
                expect(newState.valgteFylker[0]).to.equal("FYLKE");
            });
        });
    });

    describe('oversikt-container', function() {
        beforeEach(() => {
            this.dispatch = sinon.spy();

            this.fylke1 = {
                navn: "Oslo",
                kommuner: []
            };
            this.fylke2 = {
                navn: "Troms",
                kommuner: []
            };

            this.defaultProps = {
                visKart: true,
                dispatch: this.dispatch,
                valgtFylke: "",
                valgtKommune: "",
                omrader: {data: [this.fylke1, this.fylke2] },
                oversiktStillinger: { data: {} }
            };
        });

        it("skal vise kart om prop visKart er true", () => {
            const props = {...this.defaultProps, visKart: true};
            const wrapper = shallow(<Oversikt {...props} />);
            expect(wrapper).to.have.descendants(OversiktKart);
        });

        it("skal vise tabell om prop visKart er false", () => {
            const props = {...this.defaultProps, visKart: false};
            const wrapper = shallow(<Oversikt {...props} />);
            expect(wrapper).to.have.descendants(Oversiktstabell);
        });

        it("toggleKart skal kalle action VIS_KART om visKart er false", () => {
            const props = {...this.defaultProps, visKart: false};
            const wrapper = shallow(<Oversikt {...props} />);
            wrapper.instance().togglekart();
            expect(this.dispatch).to.have.been.calledWith({type: actions.vis_kart});
        });

        it("toggleKart skal kalle action VIS_TABELL om visKart er true", () => {
            const props = {...this.defaultProps, visKart: true};
            const wrapper = shallow(<Oversikt {...props} />);
            wrapper.instance().togglekart();
            expect(this.dispatch).to.have.been.calledWith({type: actions.vis_tabell});
        });
    });

    describe('oversikt-tabell', function() {
        beforeEach(() => {
            this.kommune1 = {
                navn: "kommune1",
                id: "0101"
            };
            this.kommune2 = {
                navn: "kommune2",
                id: "0202"
            };

            this.fylke1 = {
                underomrader: [this.kommune1],
                navn: "fylke1",
                id: "f1"
            };

            this.fylke2 = {
                underomrader: [this.kommune2],
                navn: "fylke2",
                id: "f2"
            };

            this.omrader = {
                status: "LASTET",
                data: [this.fylke1, this.fylke2]
            };

            this.defaultProps = {
                visKart: true,
                valgtFylke: "",
                valgtKommune: "",
                omrader: [this.fylke1, this.fylke2]
            };

            this.oversiktStillinger = {
                status: "LASTET",
                data: [
                    { kommuneid: "0101", antallLedige: 1, antallStillinger: 2 },
                    { kommuneid: "0202", antallLedige: 3, antallStillinger: 4 }
                ]
            };
        });

        it('getKommuneMedData skal populere kommunen med stillinger', () => {
            const oversiktStillinger = getKommuneMedData(this.kommune1, this.oversiktStillinger.data);
            const expected = {
                antallLedige: 1,
                antallStillinger: 2,
                id: "0101",
                navn: "kommune1"
            };
            expect(oversiktStillinger).to.deep.equal(expected);
        });

        it("getStillingerTotalt skal returnerer summen av stillinger for alle kommuner i fylket", () => {
            const kommuner = [this.kommune1, this.kommune2];
            const antStillinger = getStillingerTotalt(kommuner, this.oversiktStillinger.data);
            expect(antStillinger.antallLedige).equal(4);
            expect(antStillinger.antallStillinger).equal(6);
        });
    });
});

