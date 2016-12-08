import React from 'react';
import {connect} from 'react-redux';
import Stillingstabell from './ledigestillinger-stillinger-tabell';
import Innholdslaster from "../../felles/innholdslaster/innholdslaster";

import {
    getHarValgtYrkesgrupper,
    getValgteYrkesgrupperMedStillinger
} from "./ledigestillinger-stillinger-selectors";

export class Stillinger extends React.Component {
    render() {
        if(!this.props.skalVises) {
            return null;
        }

        return (
            <Innholdslaster avhengigheter={[this.props.stillingerRest]}>
                {this.props.yrkesgrupper.map(yrkesgruppe => <Stillingstabell key={yrkesgruppe.id} yrkesgruppe={yrkesgruppe} />)}
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
