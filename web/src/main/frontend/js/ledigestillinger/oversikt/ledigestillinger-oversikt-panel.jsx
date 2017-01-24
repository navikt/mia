import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import Modal from './../../felles/modal/modal';
import Modalinnhold from './ledigestillinger-oversikt-modalinnhold';
import OmradeTabell from './ledigestillinger-oversikt-omradetabell';
import Innholdslaster from './../../felles/innholdslaster/innholdslaster';

const meldinger = defineMessages({
    velgKommuneOgFylkeLabel: {
        id: 'ledigestillinger.oversikt.tabell.velgfylkerogkommuner',
        defaultMessage: 'Velg fylker og kommuner'
    },
    modalTittel: {
        id: 'ledigestillinger.oversikt.tabell.modal.tittel',
        defaultMessage: 'Velg fylker og kommuner'
    }
});

export class Oversiktspanel extends React.Component {
    render() {
        const props = this.props;
        const valgteFylker = props.omrader.filter(omrade => props.valgteFylker.includes(omrade.id)) || [];
        const fylkerSomSkalVises = valgteFylker.concat(
            props.omrader.filter(fylke => !valgteFylker.includes(fylke.id) && fylke.underomrader.some(kommune => props.valgteKommuner.includes(kommune.id)))
        );

        const modalId = "velgKommunerOgFylker";

        return (
            <div className="panel panel-fremhevet">
                <div className="text-center blokk">
                    <button className="knapp knapp-hoved" onClick={() => props.apneModal(modalId)} ref="modalknapp">
                        <FormattedMessage {...meldinger.velgKommuneOgFylkeLabel}/>
                    </button>
                </div>
                <Modal id={modalId} tittel={meldinger.modalTittel} onLagre={() => props.lagreModal()}
                       onLukk={() => this.refs.modalknapp.focus()}>
                    <Modalinnhold />
                </Modal>
                <Innholdslaster spinnerForInitialisert={false} avhengigheter={[props.oversiktStillinger, props.oversiktArbeidsledighet]}>
                    {fylkerSomSkalVises.length !== 0
                        ? <OmradeTabell valgteFylker={fylkerSomSkalVises} omrader={props.omrader}
                                        valgteKommuner={props.valgteKommuner}
                                        stillinger={props.oversiktStillinger.data}
                    />
                        : <noscript/>}
                </Innholdslaster>
            </div>
        );
    }
}

export default Oversiktspanel;