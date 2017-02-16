import React from 'react';
import { connect } from 'react-redux';
import {defineMessages} from 'react-intl';
import Innholdslaster from './../../felles/innholdslaster/innholdslaster';
import { hentStatistikk } from './ledigestillinger-statistikk-actions';
import OversiktGraf from './ledigestillinger-oversikt-graf';

const feilmeldinger = defineMessages({
    tittel: {
        id: 'statistikk.feilmeldingspanel.tittel',
        defaultMessage: 'Feil ved henting av statistikkdata'
    },
    tekst: {
        id: 'statistikk.feilmeldingspanel.tekst',
        defaultMessage: 'Noe gikk galt ved hentingen av statistikkdata fra våre baksystemer. Du kan prøve å oppdatere siden og se om feilen vedvarer.'
    }
});

export class Statistikk extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentStatistikk());
    }

    render() {
        return (
            <div className="panel panel-fremhevet">
                <Innholdslaster avhengigheter={[this.props.statistikk]} feilmelding={feilmeldinger}>
                    <OversiktGraf tabell={this.props.statistikk.data}
                                  valgteFylker={this.props.valgteFylker}
                                  valgteKommuner={this.props.valgteKommuner}
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
    valgteFylker: state.ledigestillinger.oversikt.valgteFylker,
    valgteKommuner: state.ledigestillinger.oversikt.valgteKommuner
});

export default connect(stateToProps)(Statistikk);