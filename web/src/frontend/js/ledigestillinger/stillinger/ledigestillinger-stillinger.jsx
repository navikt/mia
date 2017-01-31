import React from 'react';
import {connect} from 'react-redux';
import Stillingspanel from './ledigestillinger-stillinger-panel';
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
