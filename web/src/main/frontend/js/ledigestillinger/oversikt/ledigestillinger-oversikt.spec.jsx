import {expect, sinon, React} from '../../../test/test-helper';
import {shallow, mount} from "enzyme";

import OversiktReducer, {actions} from './ledigestillinger-oversikt-reducer';
import {Oversikt} from "./ledigestillinger-oversikt";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import OversiktTabell from "./ledigestillinger-oversikt-tabell";

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
            expect(newState.valgtKommune).to.be.null;
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
                valgtFylke: null,
                valgtKommune: null,
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
    });
});

