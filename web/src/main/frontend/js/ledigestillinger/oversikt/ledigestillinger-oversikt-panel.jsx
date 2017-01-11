import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import Modal from '../../felles/modal/modal';
import Modalinnhold from './ledigestillinger-oversikt-modalinnhold';
import OmradeTabell from './ledigestillinger-oversikt-omradetabell';

const meldinger = defineMessages({
    velgKommuneOgFylkeLabel: {
        id: 'ledigestillinger.oversikt.tabell.velgfylkerogkommuner',
        defaultMessage: 'Velg fylker og kommuner'
    },
    modalTittel: {
        id: 'ledigestillinger.oversikt.tabell.modal.tittel',
        defaultMessage: 'Velg fylker og kommuner'
    },
    tabellIngenValgt: {
    id: 'ledigestillinger.oversikt.tabell.ingenvalgt',
        defaultMessage: 'Ingen fylker eller kommuner er valgt'
    }
});

export const Oversiktspanel = props => {
    const valgteFylker = props.omrader.filter(omrade => props.valgteFylker.includes(omrade.id)) || [];
    const modalId = "velgKommunerOgFylker";

    const panelInnhold = valgteFylker.length !== 0
        ? <OmradeTabell valgteFylker={valgteFylker} omrader={props.omrader} valgteKommuner={props.valgteKommuner} stillinger={props.oversiktStillinger} />
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
            {panelInnhold}
        </div>
    );
};

export default Oversiktspanel;