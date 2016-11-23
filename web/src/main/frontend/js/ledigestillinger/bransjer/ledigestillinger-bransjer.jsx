import React from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router';
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';
import {actions} from "./ledigestillinger-bransjer-reducer";
import Inputfelt from "../../felles/inputfelter/inputfelt";
import BransjeDropdown from './bransje-dropdown';
import Bokser from './ledige-stillinger-bransjer-bokser';

export const meldinger = defineMessages({
    soketekst: {
        id: 'ledigestillinger.bransjer.soketekst',
        defaultMessage: 'Søk direkte eller klikk videre i kategoriene'
    },
    velgbransje: {
        id: 'ledigestillinger.bransjer.velgbransje',
        defaultMessage: 'Velg bransje'
    },
    boksoverskrift: {
        id: 'ledigestillinger.bransjer.boksoverskrift',
        defaultMessage: 'Ledige jobber totalt ({antall}) fordelt på bransjer'
    },
    lenkeallebransjer: {
        id: 'ledigestillinger.bransjer.lenkeallebransjer',
        defaultMessage: 'Vis alle bransjer med ledige stillinger'
    }
});

function findTotaltAntallJobber(data) {
    return data.reduce(function(a, b) {return a + b.antall;}, 0);
}

export const Bransjer = (props) => {
    const toggleYrkesgruppe = (id) => {
        if(props.valgteyrkesgrupper.includes(id)) {
            props.dispatch({type: actions.yrkesgruppedeselect, payload: id});
        } else {
            props.dispatch({type: actions.yrkesgruppeselect, payload: id});
        }
    };

    const velgYrkesomrade = (id) => {
        props.dispatch({type: actions.yrkesomradeselect, payload: id});
    };

    return (
        <div className="panel">
            <Inputfelt id="input-sok" label={meldinger.soketekst} type="search" className="input-fullbredde" />
            <BransjeDropdown meldinger={meldinger} yrkesomrade={props.valgtyrkesomrade} onClick={velgYrkesomrade} findTotaltAntallJobber={findTotaltAntallJobber}/>
            <Bokser meldinger={meldinger} findTotaltAntallJobber={findTotaltAntallJobber} onClick={toggleYrkesgruppe} valgteyrkesgrupper={props.valgteyrkesgrupper}/>
            <Link to="#">
                <FormattedMessage {...meldinger.lenkeallebransjer} /> >>
            </Link>
        </div>
    );
};

const stateToProps = state => ({
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    valgtyrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade
});

export default connect(stateToProps)(injectIntl(Bransjer));