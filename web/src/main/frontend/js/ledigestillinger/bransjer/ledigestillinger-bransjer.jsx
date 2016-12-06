import React from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router';
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';
import {actions} from "./ledigestillinger-bransjer-reducer";
import Inputfelt from "../../felles/inputfelter/inputfelt";
import BransjeDropdown from './bransje-dropdown';
import Innholdslaster from '../../felles/innholdslaster/innholdslaster';
import { hentYrkesomraderForAlleFylker } from './ledigestillinger-bransjer-actions';
import IkkeFerdigPanel from '../../felles/ikkeferdig/ikke-ferdig-panel';

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

export class Bransjer extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentYrkesomraderForAlleFylker());
    }

    toggleYrkesgruppe(id) {
        if(this.props.valgteyrkesgrupper.includes(id)) {
            this.props.dispatch({type: actions.yrkesgruppedeselect, payload: id});
        } else {
            this.props.dispatch({type: actions.yrkesgruppeselect, payload: id});
        }
    }

    velgYrkesomrade(id) {
        this.props.dispatch({type: actions.yrkesomradeselect, payload: id});
    }

    render() {
        return (
            <div>
                <Inputfelt id="input-sok" label={meldinger.soketekst} type="search" className="input-fullbredde" />
                <Innholdslaster avhengigheter={[this.props.yrkesomrader]}>
                    <BransjeDropdown meldinger={meldinger} yrkesomrader={this.props.yrkesomrader.data} yrkesomrade={this.props.valgtyrkesomrade} onClick={id => this.velgYrkesomrade(id)} />
                        <IkkeFerdigPanel />
                    <Link to="#">
                        <FormattedMessage {...meldinger.lenkeallebransjer} /> >>
                    </Link>
                </Innholdslaster>
            </div>
        );
    }
}

const stateToProps = state => ({
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    valgtyrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade,
    yrkesgrupper: state.ledigestillinger.bransje.yrkesgrupper,
    yrkesomrader: state.ledigestillinger.bransje.yrkesomrader
});

export default connect(stateToProps)(injectIntl(Bransjer));