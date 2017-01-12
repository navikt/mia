import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import Modal from '../../felles/modal/modal';
import Modalinnhold from './ledigestillinger-oversikt-modalinnhold';
import OmradeTabell from './ledigestillinger-oversikt-omradetabell';
import Spinner from './../../felles/innholdslaster/innholdslaster-spinner';
import {STATUS} from './../../felles/konstanter';

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

export class Oversiktspanel extends React.Component {
    render() {
        const props = this.props;
        if (props.oversiktStillinger.status === STATUS.laster) {
            return <Spinner />;
        }

        const valgteFylker = props.omrader.filter(omrade => props.valgteFylker.includes(omrade.id)) || [];
        const modalId = "velgKommunerOgFylker";

        return (
            <div>
                <div className="text-center blokk">
                    <button className="knapp knapp-hoved" onClick={() => props.apneModal(modalId)} ref="modalknapp">
                        <FormattedMessage {...meldinger.velgKommuneOgFylkeLabel}/>
                    </button>
                </div>
                <Modal id={modalId} tittel={meldinger.modalTittel} onLagre={() => props.lagreModal()}
                       onLukk={() => this.refs.modalknapp.focus()}>
                    <Modalinnhold />
                </Modal>
                {props.oversiktStillinger.status === STATUS.laster ? <noscript/> :
                    (valgteFylker.length !== 0
                        ? <OmradeTabell valgteFylker={valgteFylker} omrader={props.omrader} valgteKommuner={props.valgteKommuner} stillinger={props.oversiktStillinger.data} />
                        : <em><FormattedMessage {...meldinger.tabellIngenValgt} /></em>)}
            </div>
        );
    }
}

export default Oversiktspanel;