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

    velgFylke(fylke) {
        this.props.dispatch({ type: actions.velg_fylke, payload: fylke });
        this.oppdaterAlleDatagrunnlag();
    }

    velgKommune(kommune) {
        this.props.dispatch({ type: actions.velg_kommune, payload: kommune });
        this.oppdaterAlleDatagrunnlag();
    }

    oppdaterAlleDatagrunnlag() {
        this.props.dispatch(hentYrkesomrader());
        this.props.dispatch(hentYrkesgrupper());
        this.props.dispatch(hentStillinger());
    }

    render() {
        const oversiktProps = {
            velgFylke: this.velgFylke.bind(this),
            velgKommune: this.velgKommune.bind(this),
            valgtFylke: this.props.valgtFylke,
            valgtKommune: this.props.valgtKommune,
            oversiktStillinger: this.props.oversiktStillinger.data,
            omrader: this.props.omrader.data
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
    valgtFylke: state.ledigestillinger.oversikt.valgtFylke,
    valgtKommune: state.ledigestillinger.oversikt.valgtKommune,
    omrader: state.rest.omrader,
    oversiktStillinger: state.rest.oversiktStillinger
});

export default connect(stateToProps)(Oversikt);