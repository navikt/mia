import React from 'react';
import { connect } from 'react-redux';
import Innholdslaster from './../../felles/innholdslaster/innholdslaster';
import { hentStatistikk } from './ledigestillinger-statistikk-actions';
import OversiktGraf from './ledigestillinger-oversikt-graf';

export class Statistikk extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentStatistikk());
    }

    render() {
        return (
            <div>
                <Innholdslaster avhengigheter={[this.props.statistikk]}>
                    <OversiktGraf tabell={this.props.statistikk.data} />
                </Innholdslaster>
            </div>
        );
    }
}

const stateToProps = state => ({
    statistikk: state.rest.statistikk
});

export default connect(stateToProps)(Statistikk);