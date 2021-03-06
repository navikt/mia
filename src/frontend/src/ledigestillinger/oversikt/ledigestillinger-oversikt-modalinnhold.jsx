import React from "react";
import {Checkbox} from 'nav-frontend-skjema';
import {connect} from 'react-redux';
import {actions} from './ledigestillinger-oversikt-reducer';
import {compareOmrader} from './ledigestillinger-oversikt-utils';

const Omrade = props => {
    const name = `checkbox_${props.omrade.id}`;
    const underomradeErValgt = props.omrade.underomrader && props.omrade.underomrader.some(omrade => omrade.valgt === true);

    const valgt = underomradeErValgt || props.omrade.valgt === true;

    return (
        <li className="blokk-xxs">
            <Checkbox
                label={props.omrade.navn}
                checked={valgt}
                id={name}
                name={name}
                onChange={props.onChange}
                className={underomradeErValgt ? "checkbox-expanded" : ""}
                data-testid={props.testid}

            />
            { valgt && props.omrade.underomrader.length > 1 ? props.children : null}
        </li>
    );
};

export class Modalinnhold extends React.Component {
    componentDidMount() {
        this.props.dispatch({ type: actions.modal_reset });
    }

    getKommunerForFylke(fylke) {
        return fylke.underomrader.sort(compareOmrader).map(kommune => (
            <Omrade key={kommune.id} omrade={kommune} onChange={this.toggleKommune.bind(this, kommune.id)} testid="kommuner"/>
        ));
    }

    hentKommunerForFylke(fylkeid) {
        const fylkeObjekt = this.props.omrader.filter(fylke => fylke.id === fylkeid);
        return fylkeObjekt[0].underomrader;
    }

    avvelgAlleKommunerForFylke(fylkeid) {
        const kommuner = this.hentKommunerForFylke(fylkeid);
        kommuner.forEach(kommune => this.props.dispatch({
                type: actions.modal_avvelg_kommune,
                payload: kommune.id
            }));
    }

    toggleFylke(fylke) {
        if (this.props.valgteFylker.includes(fylke)) {
            this.avvelgAlleKommunerForFylke(fylke);
        }

        const actionType = this.props.valgteFylker.includes(fylke) ? actions.modal_avvelg_fylke : actions.modal_velg_fylke;
        this.props.dispatch({ type: actionType, payload: fylke });
    }

    toggleKommune(kommune) {
        const actionType = this.props.valgteKommuner.includes(kommune) ? actions.modal_avvelg_kommune : actions.modal_velg_kommune;
        this.props.dispatch({ type: actionType, payload: kommune });
    }

    render() {
        const omraderMedData = this.props.omrader.map(fylke => ({
            ...fylke,
            valgt: this.props.valgteFylker.includes(fylke.id),
            underomrader: fylke.underomrader.map(kommune => ({
                ...kommune,
                valgt: this.props.valgteKommuner.includes(kommune.id)
            }))
        }));

        const omrader = omraderMedData.sort(compareOmrader).map(fylke => (
            <Omrade key={fylke.id} omrade={fylke} onChange={this.toggleFylke.bind(this, fylke.id)} testid="fylker">
                <ul className="liste liste-omrade blokk-xs" >
                    {this.getKommunerForFylke(fylke)}
                </ul>
            </Omrade>
        ));

        return (
            <div className="blokk">
                <ul className="liste liste-omrade">
                    {omrader}
                </ul>
            </div>
        );
    }
}

const stateToProps = state => ({
    valgteFylker: state.ledigestillinger.oversikt.valgteFylkerModal,
    valgteKommuner: state.ledigestillinger.oversikt.valgteKommunerModal,
    omrader: state.rest.omrader.data
});

export default connect(stateToProps)(Modalinnhold);
