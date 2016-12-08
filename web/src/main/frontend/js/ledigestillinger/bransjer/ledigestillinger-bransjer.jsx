import React from 'react';
import {connect} from 'react-redux';
import {Link} from 'react-router';
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';
import {actions} from "./ledigestillinger-bransjer-reducer";
import BransjeDropdown from './bransje-dropdown';
import Innholdslaster from '../../felles/innholdslaster/innholdslaster';
import Bokser from './ledigestillinger-bransjer-bokser';
import restActionCreator from "../../felles/rest/rest-action";
import {hentYrkesgrupper} from "./ledigestillinger-bransjer-actions";

const meldinger = defineMessages({
    lenkeallebransjer: {
        id: 'ledigestillinger.bransjer.lenkeallebransjer',
        defaultMessage: 'Vis alle bransjer med ledige stillinger >>'
    }
});

const BokserForYrkesomrader = props => (
    <Bokser onClick={id => props.onClick(id)} yrkesgrupper={props.yrkesomrader.data}/>
);

const BokserForYrkesgrupper = props => (
    <Innholdslaster avhengigheter={[props.yrkesgrupper]}>
        <Bokser onClick={id => props.onClick(id)} yrkesgrupper={props.yrkesgrupper.data} valgteyrkesgrupper={props.valgteyrkesgrupper}/>
    </Innholdslaster>
);

export class Bransjer extends React.Component {
    componentDidMount() {
        this.props.dispatch(restActionCreator("yrkesomrader", "/bransjer/yrkesomrade"));
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
        this.props.dispatch(hentYrkesgrupper());
    }

    render() {
        return (
            <div>
                <Innholdslaster avhengigheter={[this.props.yrkesomrader]}>
                    <BransjeDropdown yrkesomrader={this.props.yrkesomrader.data} yrkesomrade={this.props.valgtyrkesomrade} onClick={id => this.velgYrkesomrade(id)} />
                    { this.props.valgtyrkesomrade === "alle"
                        ? <BokserForYrkesomrader onClick={id => this.velgYrkesomrade(id)} yrkesomrader={this.props.yrkesomrader}/>
                        : <BokserForYrkesgrupper onClick={id => this.toggleYrkesgruppe(id)} yrkesgrupper={this.props.yrkesgrupper} valgteyrkesgrupper={this.props.valgteyrkesgrupper} />
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

export default connect(stateToProps)(injectIntl(Bransjer));