import React from 'react';
import {connect} from 'react-redux';
import {defineMessages, FormattedMessage} from 'react-intl';
import Innholdslaster from './../../felles/innholdslaster/innholdslaster';
import {hentStatistikk} from './ledigestillinger-statistikk-actions';
import OversiktGraf from './ledigestillinger-oversikt-graf';
import {bareValgtEOSRestenAvVerden} from '../../felles/filtervalg/filtrering-andre-omrader';
import Ekspanderbartpanel from "nav-frontend-ekspanderbartpanel";

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

const tekster = defineMessages( {
    tabellOverskrift: {
        id: 'ledigestillinger.overskrift.graf.arbeidsledighet',
        defaultMessage: 'Utviklingen i arbeidsmarkedet i området'
    },
    visGraf: {
        id: 'grafswitcher.visgraf',
        defaultMessage: 'Vis som graf'
    },
    visTabell: {
        id: 'grafswitcher.vistabell',
        defaultMessage: 'Vis som tabell'
    },
});

export class Statistikk extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentStatistikk());
    }

    render() {
        return bareValgtEOSRestenAvVerden(this.props.oversikt) ? null :
            (
                <Ekspanderbartpanel tittel={<FormattedMessage {...tekster.tabellOverskrift} />} border>
                    <Innholdslaster avhengigheter={[this.props.statistikk]} feilmelding={feilmeldinger}>
                        <OversiktGraf
                            tabell={this.props.statistikk.data}
                            valgteFylker={this.props.oversikt.valgteFylker}
                            valgteKommuner={this.props.oversikt.valgteKommuner}
                            omrader={this.props.omrader}
                            yrkesomrader={this.props.yrkesomrader}
                            yrkesgrupper={this.props.yrkesgrupper}
                            valgtyrkesomrade={this.props.valgtyrkesomrade}
                            valgteyrkesgrupper={this.props.valgteyrkesgrupper}
                        />
                    </Innholdslaster>
                </Ekspanderbartpanel>
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
