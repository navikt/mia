import React from "react";
import {connect} from "react-redux";
import {defineMessages, FormattedMessage} from 'react-intl';

import Innholdslaster from "../../felles/innholdslaster/innholdslaster";
import {actions} from "./ledigestillinger-oversikt-reducer";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import OversiktTabell from "./ledigestillinger-oversikt-tabell";
import restActionCreator from "../../felles/rest/rest-action";
import {hentStillinger} from "../stillinger/ledigestillinger-stillinger-actions";
import {hentYrkesgrupper, hentYrkesomrader} from "../bransjer/ledigestillinger-bransjer-actions";
import {apneModal} from "../../felles/modal/modal-reducer";

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
    componentDidMount() {
        this.props.dispatch(restActionCreator('oversikt_stillinger', '/stillinger/oversiktAlleKommuner'));
    }

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
    }

    render() {
        const oversiktProps = {
            valgteFylker: this.props.valgteFylker,
            valgteKommuner: this.props.valgteKommuner,
            oversiktStillinger: this.props.oversiktStillinger.data,
            omrader: this.props.omrader.data,
            apneModal: this.apneModal.bind(this),
            lagreModal: this.lagreModal.bind(this)
        };

        return (
            <div className="panel panel-fremhevet panel-oversikt">
                <Innholdslaster avhengigheter={[this.props.oversiktStillinger]}>
                    {this.props.visKart ? <OversiktKart {...oversiktProps}/> : <OversiktTabell {...oversiktProps}/>}
                    <a href="#" role="button" className="oversikt-toggle" onClick={() => this.togglekart()}>
                        <FormattedMessage {...(this.props.visKart ? meldinger.lenkeVisTabell : meldinger.lenkeVisKart)}/>
                    </a>
                </Innholdslaster>
            </div>
        );
    }
}

const stateToProps = state => ({
    visKart: state.ledigestillinger.oversikt.visKart,
    valgteFylker: state.ledigestillinger.oversikt.valgteFylker,
    valgteKommuner: state.ledigestillinger.oversikt.valgteKommuner,
    omrader: state.rest.omrader,
    oversiktStillinger: state.rest.oversiktStillinger
});

export default connect(stateToProps)(Oversikt);