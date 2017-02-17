import React from 'react';
import { connect } from 'react-redux';
import Innholdslaster from './../../felles/innholdslaster/innholdslaster';
import { hentStatistikk } from './ledigestillinger-statistikk-actions';
import OversiktGraf from './ledigestillinger-oversikt-graf';
import {bareValgtEOSRestenAvVerden} from '../../felles/filtervalg/filtrering-andre-omrader';

export class Statistikk extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentStatistikk());
    }

    render() {
        return bareValgtEOSRestenAvVerden(this.props.oversikt) ? null :
        (
            <div className="panel panel-fremhevet">
                <Innholdslaster avhengigheter={[this.props.statistikk]}>
                    <OversiktGraf tabell={this.props.statistikk.data}
                                  valgteFylker={this.props.oversikt.valgteFylker}
                                  valgteKommuner={this.props.oversikt.valgteKommuner}
                                  omrader={this.props.omrader}
                                  yrkesomrader={this.props.yrkesomrader}
                                  yrkesgrupper={this.props.yrkesgrupper}
                                  valgtyrkesomrade={this.props.valgtyrkesomrade}
                                  valgteyrkesgrupper={this.props.valgteyrkesgrupper}
                    />
                </Innholdslaster>
            </div>
        );
    }
}

const stateToProps = state => ({
    statistikk: state.rest.statistikk,
    omrader: state.rest.omrader.data,
    valgtyrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade,
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    yrkesomrader: state.rest.yrkesomrader,
    yrkesgrupper: state.rest.yrkesgrupper,
    oversikt: state.ledigestillinger.oversikt
});

export default connect(stateToProps)(Statistikk);