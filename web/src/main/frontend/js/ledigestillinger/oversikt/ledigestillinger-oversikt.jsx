import React from "react";
import {connect} from "react-redux";
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';

import {actions} from "./ledigestillinger-oversikt-reducer";
import OversiktKart from "./ledigestillinger-oversikt-kart";
import OversiktTabell from "./ledigestillinger-oversikt-tabell";

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

const Oversiktskart = (props) => {
    const toggleKart = (event) => {
        event.preventDefault();
        props.dispatch({ type: props.visKart ? actions.vis_tabell : actions.vis_kart });
    };

    return (
        <div className="panel panel-fremhevet panel-oversikt">
            {props.visKart ? <OversiktKart/> : <OversiktTabell/>}
            <a href="#" role="button" className="oversikt-toggle" onClick={toggleKart}>
                <FormattedMessage {...(props.visKart ? meldinger.lenkeVisTabell : meldinger.lenkeVisKart)}/>
            </a>
        </div>
    );
};

const stateToProps = state => ({
    visKart: state.ledigestillinger.oversikt.visKart
});

export default connect(stateToProps)(injectIntl(Oversiktskart));