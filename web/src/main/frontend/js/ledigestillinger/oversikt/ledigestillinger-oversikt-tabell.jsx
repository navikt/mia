import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import {getValgteKommunerForFylke} from './ledigestillinger-oversikt-utils';
import Modal from '../../felles/modal/modal';
import Modalinnhold from './ledigestillinger-oversikt-modalinnhold';
import KommuneTabell from './ledigestillinger-oversikt-kommunetabell';

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
    tabellIngenValgt: {
    id: 'ledigestillinger.oversikt.tabell.ingenvalgt',
        defaultMessage: 'Ingen fylker eller kommuner er valgt'
    }
});

export const Oversiktstabell = props => {
    const valgteFylker = props.omrader.filter(omrade => props.valgteFylker.includes(omrade.id)) || [];
    const modalId = "velgKommunerOgFylker";

    const komuneTabell = valgteFylker.length !== 0
        ? valgteFylker.map(fylke => <KommuneTabell key={fylke.id} fylke={fylke} kommuner={getValgteKommunerForFylke(fylke.id, props.omrader, props.valgteKommuner)} stillinger={props.oversiktStillinger} />)
        : <em><FormattedMessage {...meldinger.tabellIngenValgt} /></em>;

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
            {komuneTabell}
        </div>
    );
};

export default Oversiktstabell;