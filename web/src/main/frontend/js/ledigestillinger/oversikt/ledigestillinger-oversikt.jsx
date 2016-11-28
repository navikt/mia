import React from "react";
import {connect} from "react-redux";
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';

import Innholdslaster from "../../felles/innholdslaster/innholdslaster";
import {actions} from "./ledigestillinger-oversikt-reducer";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import OversiktTabell from "./ledigestillinger-oversikt-tabell";
import {lastOversiktAlleKommuner} from "./ledigestillinger-oversikt-actions";

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
        this.props.dispatch(lastOversiktAlleKommuner());
        if(this.props.fylker.length > 0) {
            this.velgFylke(this.props.fylker[0].navn);
        }
    }

    togglekart() {
        this.props.dispatch({ type: this.props.visKart ? actions.vis_tabell : actions.vis_kart });
    }

    velgFylke(fylke) {
        this.props.dispatch({ type: actions.velg_fylke, payload: fylke });
    }

    velgKommune(kommune) {
        this.props.dispatch({ type: actions.velg_kommune, payload: kommune });
    }

    render() {
        const oversiktProps = {
            velgFylke: this.velgFylke.bind(this),
            velgKommune: this.velgKommune.bind(this),
            valgtFylke: this.props.valgtFylke,
            valgtKommune: this.props.valgtKommune,
            kommunedata: this.props.kommunedata,
            fylker: this.props.fylker
        };

        return (
            <div className="panel panel-fremhevet panel-oversikt">
                <Innholdslaster avhengigheter={[this.props.kommunedata]}>
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
    fylker: state.kodeverk.fylker.fylker,
    kommunedata: state.ledigestillinger.oversikt.kommunedata
});

export default connect(stateToProps)(injectIntl(Oversikt));