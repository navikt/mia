import React from "react";
import {defineMessages, FormattedMessage} from 'react-intl';
import {getStillingerTotalt, getKommuneMedData, getValgteKommunerForFylke, getStillingerTotaltForKommuneIFylke} from './ledigestillinger-oversikt-utils';
import Modal from "../../felles/modal/modal";
import Modalinnhold from "./ledigestillinger-oversikt-modalinnhold";

const meldinger = defineMessages({
    velgKommuneOgFylkeLabel: {
        id: 'ledigestillinger.oversikt.tabell.velgfylkerogkommuner',
        defaultMessage: 'Velg fylker og kommuner'
    },
    modalTittel: {
        id: 'ledigestillinger.oversikt.tabell.modal.tittel',
        defaultMessage: 'Velg fylker og kommuner'
    },
    velgKommune: {
        id: 'ledigestillinger.oversikt.tabell.velgkommune',
        defaultMessage: 'Velg kommune/kommuner'
    },
    tabellOverskriftKommune: {
        id: 'ledigestillinger.oversikt.tabell.overskriftkommune',
        defaultMessage: 'Kommune'
    },
    tabellOverskriftLedige: {
        id: 'ledigestillinger.oversikt.tabell.overskriftledige',
        defaultMessage: 'Arbeidsledige ({antall, number})'
    },
    tabellOverskriftStillinger: {
        id: 'ledigestillinger.oversikt.tabell.overskriftstillinger',
        defaultMessage: 'Ledige stillinger ({antall, number})'
    },
    tabellIngenValgt: {
    id: 'ledigestillinger.oversikt.tabell.ingenvalgt',
        defaultMessage: 'Ingen fylker eller kommuner er valgt'
}
});

const KommuneTabellRad = props => (
    <tr key={props.kommune.kommunenummer}>
        <th scope="row">{props.kommune.navn}</th>
        <td className="text-center">{props.kommune.antallLedige}</td>
        <td className="text-center">{props.kommune.antallStillinger}</td>
    </tr>
);

const KommuneTabell = ({fylke, kommuner, stillinger, totaltAntall}) => {
    const stillingerTotalt = getStillingerTotalt(kommuner, stillinger);
    const fylkenavn = fylke != null ? fylke.navn : "";

    return (
        <div className="blokk">
            <h3 className="typo-etikett">{fylkenavn}</h3>
            <table className="tabell blokk-s">
                <thead>
                <tr>
                    <th scope="col">
                        <FormattedMessage {...meldinger.tabellOverskriftKommune}/>
                    </th>
                    <th scope="col" className="text-center">
                        <FormattedMessage {...meldinger.tabellOverskriftLedige} values={{antall: stillingerTotalt.antallLedige}}/>
                    </th>
                    <th scope="col" className="text-center">
                        <FormattedMessage {...meldinger.tabellOverskriftStillinger} values={{antall: totaltAntall.antallStillinger}}/>
                    </th>
                </tr>
                </thead>
                <tbody>
                    {kommuner.map(kommune => getKommuneMedData(kommune, stillinger)).map(kommune => <KommuneTabellRad key={kommune.id} kommune={kommune}/>)}
                </tbody>
            </table>
        </div>
    );
};

export const Oversiktstabell = props => {
    const valgteFylker = props.omrader.filter(omrade => props.valgteFylker.includes(omrade.id)) || [];
    const modalId = "velgKommunerOgFylker";

    return (
        <div>
            <div className="text-center blokk">
                <button className="knapp knapp-hoved" onClick={() => props.apneModal(modalId)}>
                    <FormattedMessage {...meldinger.velgKommuneOgFylkeLabel}/>
                </button>
            </div>
            <Modal id={modalId} tittel={meldinger.modalTittel} onLagre={() => props.lagreModal()}>
                <Modalinnhold />
            </Modal>
            { valgteFylker.length !== 0
                ? valgteFylker.map(fylke => <KommuneTabell key={fylke.id} fylke={fylke} kommuner={getValgteKommunerForFylke(fylke.id, props.omrader, props.valgteKommuner)} stillinger={props.oversiktStillinger} totaltAntall={getStillingerTotaltForKommuneIFylke(fylke.id, props.omrader, props.valgteKommuner, props.oversiktStillinger)}/>)
                : <em><FormattedMessage {...meldinger.tabellIngenValgt} /></em> }
        </div>
    );
};

export default Oversiktstabell;