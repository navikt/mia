import React from "react";
import {connect} from 'react-redux';
import {actions} from './ledigestillinger-oversikt-reducer';

const Omrade = props => {
    const name = `checkbox_${props.omrade.id}`;
    const underomradeErValgt = props.omrade.underomrader && props.omrade.underomrader.some(omrade => omrade.valgt === true);

    const classes = ["nav-checkbox"];
    if(underomradeErValgt) {
        classes.push("expanded");
    }

    return (
        <li className="blokk-xxs">
            <input type="checkbox" className={classes.join(" ")} id={name} checked={props.omrade.valgt === true} name={name} onChange={props.onChange} />
            <label htmlFor={name}>{props.omrade.navn}</label>
            { props.omrade.valgt && props.omrade.underomrader.length > 1 ? props.children : null}
        </li>
    );
};

export class Modalinnhold extends React.Component {
    componentDidMount() {
        this.props.dispatch({ type: actions.modal_reset });
    }

    getKommunerForFylke(fylke) {
        return fylke.underomrader.map(kommune => (
            <Omrade key={kommune.id} omrade={kommune} onChange={this.toggleKommune.bind(this, kommune.id)} />
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

        const omrader = omraderMedData.map(fylke => (
            <Omrade key={fylke.id} omrade={fylke} onChange={this.toggleFylke.bind(this, fylke.id)}>
                <ul className="liste liste-omrade blokk-xs">
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
