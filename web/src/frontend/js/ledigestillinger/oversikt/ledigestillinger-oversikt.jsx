import React from "react";
import {connect} from "react-redux";
import {defineMessages, FormattedMessage} from 'react-intl';
import {actions} from "./ledigestillinger-oversikt-reducer";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import Oversiktspanel from "./ledigestillinger-oversikt-panel";
import {hentStillinger, hentAntallStillingerForYrkesgruppe} from "../stillinger/ledigestillinger-stillinger-actions";
import {hentYrkesgrupper, hentYrkesomrader, hentAntallStillingerForOmrade} from "../bransjer/ledigestillinger-bransjer-actions";
import {apneModal} from "../../felles/modal/modal-reducer";
import {hentStatistikk} from './../statistikk/ledigestillinger-statistikk-actions';
import SwitcherKnapp from '../../felles/switcher/switcher-knapp';

const meldinger = defineMessages({
    lenkeVisKart: {
        id: 'ledigestillinger.oversikt.viskartlenke',
        defaultMessage: 'Vis som kart'
    },
    lenkeVisTabell: {
        id: 'ledigestillinger.oversikt.vistabelllenke',
        defaultMessage: 'Vis som tabell'
    }
});

export class Oversikt extends React.Component {
    constructor(props) {
        super(props);
        this.resetValg = this.resetValg.bind(this);
        this.velgFylke = this.velgFylke.bind(this);
        this.velgKommune = this.velgKommune.bind(this);
        this.avvelgKommune = this.avvelgKommune.bind(this);
    }

    togglekart() {
        if(!this.props.visKart) {
            this.resetValg();
        }
        this.props.dispatch({ type: this.props.visKart ? actions.vis_tabell : actions.vis_kart });
    }

    visKart() {
        if(!this.props.visKart) {
            this.resetValg();
            this.props.dispatch({ type: actions.vis_kart });
        }
    }

    visTabell() {
        this.props.dispatch({ type: actions.vis_tabell });
    }

    apneModal(modalid) {
        this.props.dispatch(apneModal(modalid));
    }

    lagreModal() {
        this.props.dispatch({ type: actions.modal_lagre });
        this.oppdaterAlleDatagrunnlag();
    }

    resetValg() {
        this.props.dispatch({ type: actions.reset_valg });
        this.oppdaterAlleDatagrunnlag();
    }

    velgFylke(fylkeid) {
        this.props.dispatch({ type: actions.velg_fylke, payload: fylkeid });
        this.oppdaterAlleDatagrunnlag();
    }

    velgKommune(kommuneid) {
        this.props.dispatch({ type: actions.velg_kommune, payload: kommuneid });
        this.oppdaterAlleDatagrunnlag();
    }

    avvelgKommune(kommuneid) {
        this.props.dispatch({ type: actions.avvelg_kommune, payload: kommuneid });
        this.oppdaterAlleDatagrunnlag();
    }

    oppdaterAlleDatagrunnlag() {
        this.props.dispatch(hentYrkesomrader());
        this.props.dispatch(hentYrkesgrupper());
        this.props.dispatch(hentStillinger());
        this.props.dispatch(hentAntallStillingerForOmrade());
        this.props.dispatch(hentAntallStillingerForYrkesgruppe());
        this.props.dispatch(hentStatistikk());
    }

    render() {
        const oversiktProps = {
            valgteFylker: this.props.valgteFylker,
            valgteKommuner: this.props.valgteKommuner,
            oversiktStillinger: this.props.oversiktStillinger,
            totantallstillinger: this.props.totantallstillinger.data,
            omrader: this.props.omrader.data,
            apneModal: this.apneModal.bind(this),
            lagreModal: this.lagreModal.bind(this)
        };

        const kartProps = {
            fylkergeojson: this.props.fylkergeojson.data,
            kommunergeojson: this.props.kommunergeojson.data,
            valgtYrkesomrade: this.props.valgtYrkesomrade,
            valgteYrkesgrupper: this.props.valgteYrkesgrupper,
            resetValg: this.resetValg,
            velgFylke: this.velgFylke,
            velgKommune: this.velgKommune,
            avvelgKommune: this.avvelgKommune
        };

        const innhold = this.props.visKart ? <OversiktKart {...oversiktProps} {...kartProps} /> : <Oversiktspanel {...oversiktProps}/>;

        return (
            <div className="panel-oversikt">
                {innhold}
                <div className="oversikt-toggle blokk-xs">
                    <SwitcherKnapp
                        id="switch_kart"
                        aktiv={this.props.visKart}
                        onClick={() => this.visKart()}
                        tekst={<FormattedMessage {...meldinger.lenkeVisKart} />}
                    />
                    <SwitcherKnapp
                        id="switch_tabell"
                        aktiv={!this.props.visKart}
                        onClick={() => this.visTabell()}
                        tekst={<FormattedMessage {...meldinger.lenkeVisTabell} />}
                    />
                </div>
            </div>
        );
    }
}

const stateToProps = state => ({
    visKart: state.ledigestillinger.oversikt.visKart,
    valgteFylker: state.ledigestillinger.oversikt.valgteFylker,
    valgteKommuner: state.ledigestillinger.oversikt.valgteKommuner,
    valgtYrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade,
    valgteYrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    omrader: state.rest.omrader,
    oversiktStillinger: state.rest.oversiktStillinger,
    totantallstillinger: state.rest.totantallstillinger,
    fylkergeojson: state.rest.fylkergeojson,
    kommunergeojson: state.rest.kommunergeojson
});

export default connect(stateToProps)(Oversikt);