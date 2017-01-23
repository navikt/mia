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
    togglekart() {
        this.props.dispatch({ type: this.props.visKart ? actions.vis_tabell : actions.vis_kart });
    }

    apneModal(modalid) {
        this.props.dispatch(apneModal(modalid));
    }

    lagreModal() {
        this.props.dispatch({ type: actions.modal_lagre });
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
            kommunergeojson: this.props.kommunergeojson.data
        };

        const innhold = this.props.visKart ? <OversiktKart {...oversiktProps} {...kartProps} /> : <Oversiktspanel {...oversiktProps}/>;

        return (
            <div className="panel-oversikt">
                {innhold}
                <a href="#" role="button" className="oversikt-toggle" onClick={() => this.togglekart()}>
                    <FormattedMessage {...(this.props.visKart ? meldinger.lenkeVisTabell : meldinger.lenkeVisKart)}/>
                </a>
            </div>
        );
    }
}

const stateToProps = state => ({
    visKart: state.ledigestillinger.oversikt.visKart,
    valgteFylker: state.ledigestillinger.oversikt.valgteFylker,
    valgteKommuner: state.ledigestillinger.oversikt.valgteKommuner,
    omrader: state.rest.omrader,
    oversiktStillinger: state.rest.oversiktStillinger,
    totantallstillinger: state.rest.totantallstillinger,
    fylkergeojson: state.rest.fylkergeojson,
    kommunergeojson: state.rest.kommunergeojson
});

export default connect(stateToProps)(Oversikt);