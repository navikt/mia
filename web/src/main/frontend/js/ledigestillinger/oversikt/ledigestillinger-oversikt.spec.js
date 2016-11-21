import {expect} from '../../../test/test-helper';

import OversiktReducer, {actions} from './ledigestillinger-oversikt-reducer';

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
