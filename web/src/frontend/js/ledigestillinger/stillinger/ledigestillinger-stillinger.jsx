import React from 'react';
import {connect} from 'react-redux';
import Stillingspanel from './ledigestillinger-stillinger-panel';
import Innholdslaster from "../../felles/innholdslaster/innholdslaster";
import {defineMessages} from 'react-intl';

import {
    getHarValgtYrkesgrupper,
    getValgteYrkesgrupperMedStillinger
} from "./ledigestillinger-stillinger-selectors";

const feilmeldinger = defineMessages({
    tittel: {
        id: 'statistikk.feilmeldingspanel.tittel',
        defaultMessage: 'Feil ved henting av stillingsannonser'
    },
    tekst: {
        id: 'statistikk.feilmeldingspanel.tekst',
        defaultMessage: 'Noe galt ved hentingen av stillingsannonsene. Du kan prøve å oppdatere siden og se om feilen vedvarer.'
    }
});

export class Stillinger extends React.Component {
    render() {
        if(!this.props.skalVises) {
            return null;
        }

        return (
            <Innholdslaster avhengigheter={[this.props.stillingerRest]} feilmelding={feilmeldinger}>
                {this.props.yrkesgrupper.map(yrkesgruppe => <Stillingspanel key={yrkesgruppe.id} yrkesgruppe={yrkesgruppe} />)}
            </Innholdslaster>
        );
    }
}


const stateToProps = state => ({
    skalVises: getHarValgtYrkesgrupper(state),
    yrkesgrupper: getValgteYrkesgrupperMedStillinger(state),
    stillingerRest: state.rest.stillinger
});

export default connect(stateToProps)(Stillinger);
