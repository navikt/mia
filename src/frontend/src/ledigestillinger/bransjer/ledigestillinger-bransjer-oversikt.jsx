import React from 'react';
import {connect} from 'react-redux';
import {actions} from './ledigestillinger-bransjer-reducer';
import BransjeDropdown from './bransje-dropdown';
import {hentYrkesgrupper} from './ledigestillinger-bransjer-actions';
import {hentAntallStillingerForYrkesgruppe} from '../stillinger/ledigestillinger-stillinger-actions';
import {ALTERNATIV_ALLE} from '../../felles/konstanter';
import {BokserForYrkesomrader, BokserForYrkesgrupper} from './bokser/bokser-for-yrke';
import {hentStatistikk} from './../statistikk/ledigestillinger-statistikk-actions';
import PamLenke from "../../pamLenke";

export class BransjerOversikt extends React.Component {
    toggleYrkesgruppe(id) {
        if (this.props.valgteyrkesgrupper.includes(id)) {
            this.props.dispatch({type: actions.yrkesgruppedeselect, payload: id});
        } else {
            this.props.dispatch({type: actions.yrkesgruppeselect, payload: id});
        }
        this.props.dispatch(hentAntallStillingerForYrkesgruppe());
        this.props.dispatch(hentStatistikk());
    }

    velgYrkesomrade(id) {
        this.props.dispatch({type: actions.yrkesomradeselect, payload: id});
        this.props.dispatch(hentYrkesgrupper());
        this.props.dispatch(hentAntallStillingerForYrkesgruppe());
        this.props.dispatch(hentStatistikk());
    }

    render() {
        const {yrkesomrader, yrkesgrupper, totantallstillinger, valgtyrkesomrade, valgteyrkesgrupper} = this.props;

        const boksForYrkesomrader = <BokserForYrkesomrader onClick={id => this.velgYrkesomrade(id)}
                                                           yrkesomrader={yrkesomrader}
                                                           totaltAntall={totantallstillinger}/>;

        const boksForYrkesgrupper = <BokserForYrkesgrupper onClick={id => this.toggleYrkesgruppe(id)}
                                                           yrkesgrupper={yrkesgrupper}
                                                           valgteyrkesgrupper={valgteyrkesgrupper}
                                                           totaltAntall={totantallstillinger}/>;

        const bransjeBokser = valgtyrkesomrade === ALTERNATIV_ALLE ? boksForYrkesomrader : boksForYrkesgrupper;

        return (
            <div>
                <BransjeDropdown yrkesomrader={yrkesomrader}
                                 yrkesomrade={valgtyrkesomrade}
                                 onClick={id => this.velgYrkesomrade(id)}
                                 totaltAntall={totantallstillinger}
                />
                {bransjeBokser}
                <PamLenke/>
            </div>
        );
    }
}

const stateToProps = state => ({
    yrkesgrupper: state.rest.yrkesgrupper,
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    valgtyrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade,
});

export default connect(stateToProps)(BransjerOversikt);
