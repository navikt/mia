import {expect, sinon, React} from '../../../test/test-helper';
import {shallow} from "enzyme";

import OversiktReducer, {actions} from './ledigestillinger-oversikt-reducer';
import {Oversikt} from "./ledigestillinger-oversikt";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import {OversiktTabell} from "./ledigestillinger-oversikt-tabell";
import {getStillingerTotalt, getKommuneMedData, getKommunerForValgtFylke} from './ledigestillinger-oversikt-utils';

describe('ledigestillinger', () => {
    describe('oversikt-reducer', () => {
        it("skal sette visKart til true når vi kaller action VIS_KART", () => {
            const newState = OversiktReducer({}, { type: actions.vis_kart });
            expect(newState.visKart).to.be.true;
        });

        it("skal sette visKart til false når vi kaller action VIS_TABELL", () => {
            const newState = OversiktReducer({}, { type: actions.vis_tabell });
            expect(newState.visKart).to.be.false;
        });

        it("skal sette fylke til valgt fylke når vi kaller VELG_FYLKE", () => {
            const fylkeSomVelges = "ET FYLKE";
            const newState = OversiktReducer({}, { type: actions.velg_fylke, payload: fylkeSomVelges });
            expect(newState.valgtFylke).to.equal(fylkeSomVelges);
        });

        it("skal blanke ut kommune når vi kaller VELG_FYLKE", () => {
            const newState = OversiktReducer({ valgtKommune: "gammel kommune" }, { type: actions.velg_fylke, payload: "et fylke" });
            expect(newState.valgtKommune).to.equal("");
        });

        it("skal sette kommune til valgt kommune når vi kaller VELG_KOMMUNE", () => {
            const kommuneSomVelges = "ET FYLKE";
            const newState = OversiktReducer({}, { type: actions.velg_kommune, payload: kommuneSomVelges });
            expect(newState.valgtKommune).to.equal(kommuneSomVelges);
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
                fylker: [this.fylke1, this.fylke2]
            };
        });

        it("skal velge første fylke ved mount", () => {
            shallow(<Oversikt {...this.defaultProps} />, {lifecycleExperimental: true});
            expect(this.dispatch).to.have.been.calledWith({type: actions.velg_fylke, payload: this.fylke1.navn});
        });

        it("skal vise kart om prop visKart er true", () => {
            const props = {...this.defaultProps, visKart: true};
            const wrapper = shallow(<Oversikt {...props} />);
            expect(wrapper).to.have.descendants(OversiktKart);
        });

        it("skal vise tabell om prop visKart er false", () => {
            const props = {...this.defaultProps, visKart: false};
            const wrapper = shallow(<Oversikt {...props} />);
            expect(wrapper).to.have.descendants(OversiktTabell);
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

        describe('oversikt-tabell', () => {
            beforeEach(() => {
                this.kommune1 = {
                    navn: "kommune1",
                    kommunenummer: "0101"
                };
                this.kommune2 = {
                    navn: "kommune2",
                    kommunenummer: "0202"
                };

                this.kommunedata = {
                    status: "LASTET",
                    stillinger: [
                        { kommunenummer: "0101", antallLedige: 1, antallStillinger: 2 },
                        { kommunenummer: "0202", antallLedige: 3, antallStillinger: 4 }
                    ]
                };
            });

            it('getKommunerForValgtFylke skal returnere [] hvis valgtFylke er tom string', () => {
                const kommuner = getKommunerForValgtFylke("", this.defaultProps.fylker);
                expect(kommuner).to.deep.equal([]);
            });

            it('getKommunerForValgtFylke skal returnere ["Oslo"] hvis valgtFylke er "Oslo"', () => {
                const kommuner = getKommunerForValgtFylke("Oslo", this.defaultProps.fylker);
                expect(kommuner).to.deep.equal(["Oslo"]);
            });

            it('getKommuneMedData skal populere kommunen med stillinger', () => {
                const kommunedata = getKommuneMedData(this.kommune1, this.kommunedata);
                const expected = {
                    ...this.kommune1,
                    ...this.kommunedata.stillinger[0]
                };
                expect(kommunedata).to.deep.equal(expected);
            });

            it("getStillingerTotalt skal returnerer summen av stillinger for alle kommuner i fylket", () => {
                const kommuner = [this.kommune1, this.kommune2];
                const antStillinger = getStillingerTotalt(kommuner, this.kommunedata);
                expect(antStillinger.antallLedige).equal(4);
                expect(antStillinger.antallStillinger).equal(6);
            });
        });
    });
});

