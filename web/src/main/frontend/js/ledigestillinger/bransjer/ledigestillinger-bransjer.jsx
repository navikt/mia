import React from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router';
import {defineMessages, FormattedMessage} from 'react-intl';
import {actions} from "./ledigestillinger-bransjer-reducer";
import BransjeDropdown from './bransje-dropdown';
import Innholdslaster from '../../felles/innholdslaster/innholdslaster';
import Bokser from './ledigestillinger-bransjer-bokser';
import {findTotaltAntallJobber} from './ledigestillinger-bransjer-util';
import {hentYrkesgrupper, hentYrkesomrader} from "./ledigestillinger-bransjer-actions";
import {hentStillinger} from "../stillinger/ledigestillinger-stillinger-actions";
import {STATUS, ALTERNATIV_ALLE} from "../../felles/konstanter";

const meldinger = defineMessages({
    lenkeallebransjer: {
        id: 'ledigestillinger.bransjer.lenkeallebransjer',
        defaultMessage: 'Vis alle bransjer med ledige stillinger >>'
    },
    boksoverskrift: {
        id: 'ledigestillinger.bransjer.boksoverskrift',
        defaultMessage: 'Ledige jobber totalt ({antall}) fordelt på bransjer'
    }
});

const BokserForYrkesomrader = props => (
    <div>
        <div className="blokk-xxs">
            <FormattedMessage {...meldinger.boksoverskrift} values={{antall: props.totaltAntall}}/>
        </div>
        <Bokser onClick={id => props.onClick(id)} yrkesgrupper={props.yrkesomrader.data}/>
    </div>
);

const BokserForYrkesgrupper = props => (
    <Innholdslaster avhengigheter={[props.yrkesgrupper]}>
        <div className="blokk-xxs">
            <FormattedMessage {...meldinger.boksoverskrift} values={{antall: props.totaltAntall}}/>
        </div>
        <Bokser onClick={id => props.onClick(id)} yrkesgrupper={props.yrkesgrupper.data} valgteyrkesgrupper={props.valgteyrkesgrupper}/>
    </Innholdslaster>
);

export class Bransjer extends React.Component {
    componentDidMount() {
        this.props.dispatch(hentYrkesomrader());
    }

    toggleYrkesgruppe(id) {
        if(this.props.valgteyrkesgrupper.includes(id)) {
            this.props.dispatch({type: actions.yrkesgruppedeselect, payload: id});
        } else {
            this.props.dispatch({type: actions.yrkesgruppeselect, payload: id});
        }
        this.props.dispatch(hentStillinger());
    }

    velgYrkesomrade(id) {
        this.props.dispatch({type: actions.yrkesomradeselect, payload: id});
        this.props.dispatch(hentYrkesgrupper());
    }

    getTotaltAntallJobber() {
        if(this.props.yrkesomrader.status !== STATUS.lastet) {
            return 0;
        } else if(this.props.valgtyrkesomrade === ALTERNATIV_ALLE) {
            return findTotaltAntallJobber(this.props.yrkesomrader.data);
        }

        const valgtOmrade =  this.props.yrkesomrader.data.find(yrkesomrade => yrkesomrade.id === this.props.valgtyrkesomrade);
        return valgtOmrade != null ? valgtOmrade.antallStillinger : 0;
    }

    render() {
        return (
            <div>
                <Innholdslaster avhengigheter={[this.props.yrkesomrader]}>
                    <BransjeDropdown yrkesomrader={this.props.yrkesomrader.data} yrkesomrade={this.props.valgtyrkesomrade} onClick={id => this.velgYrkesomrade(id)} />
                    { this.props.valgtyrkesomrade === ALTERNATIV_ALLE
                        ? <BokserForYrkesomrader onClick={id => this.velgYrkesomrade(id)} yrkesomrader={this.props.yrkesomrader} totaltAntall={this.getTotaltAntallJobber()}/>
                        : <BokserForYrkesgrupper onClick={id => this.toggleYrkesgruppe(id)} yrkesgrupper={this.props.yrkesgrupper} valgteyrkesgrupper={this.props.valgteyrkesgrupper} totaltAntall={this.getTotaltAntallJobber()}/>
                    }
                    <Link to="#">
                        <FormattedMessage {...meldinger.lenkeallebransjer} />
                    </Link>
                </Innholdslaster>
            </div>
        );
    }
}

const stateToProps = state => ({
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    valgtyrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade,
    yrkesgrupper: state.rest.yrkesgrupper,
    yrkesomrader: state.rest.yrkesomrader
});

export default connect(stateToProps)(Bransjer);