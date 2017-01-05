import React from 'react';
import {connect} from 'react-redux';
import Innholdslaster from './../../felles/innholdslaster/innholdslaster';
import {hentYrkesomrader, hentAntallStillingerForOmrade} from "./ledigestillinger-bransjer-actions";
import BransjerOversikt from "./ledigestillinger-bransjer-oversikt";

export class Bransjer extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentYrkesomrader());
        this.props.dispatch(hentAntallStillingerForOmrade());
    }

    render() {
        return (
            <div>
                <Innholdslaster avhengigheter={[this.props.yrkesomrader]}>
                    <BransjerOversikt yrkesomrader={this.props.yrkesomrader.data} totantallstillinger={this.props.totantallstillinger.data} />
                </Innholdslaster>
            </div>
        );
    }
}

const stateToProps = state => ({
    yrkesomrader: state.rest.yrkesomrader,
    totantallstillinger: state.rest.totantallstillinger
});

export default connect(stateToProps)(Bransjer);