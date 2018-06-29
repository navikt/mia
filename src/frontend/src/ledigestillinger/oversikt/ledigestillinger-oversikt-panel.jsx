import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import {Hovedknapp} from 'nav-frontend-knapper';
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

        const harKommuneSomErValgt = fylke => fylke.underomrader.some(kommune => props.valgteKommuner.includes(kommune.id));
        const fylkeErAlleredeLagtTil = fylke => valgteFylker.some(f => fylke.id === f.id);

        const fylkerSomSkalVises = valgteFylker.concat(
            props.omrader.filter(fylke => !fylkeErAlleredeLagtTil(fylke) && harKommuneSomErValgt(fylke))
        );

        const modalId = "velgKommunerOgFylker";

        return (
            <div className={`panel panel-fremhevet ${this.props.className}`} id='switch_tabell'>
                <div className="velg-omrader text-center blokk">
                    <Hovedknapp onClick={() => props.apneModal(modalId)} data-testid="knappVelgFylkerOgKommuner">
                        <FormattedMessage {...meldinger.velgKommuneOgFylkeLabel}/>
                    </Hovedknapp>
                </div>
                <Modal id={modalId} tittel={meldinger.modalTittel} onLagre={() => props.lagreModal()}>
                    <Modalinnhold />
                </Modal>
                <Innholdslaster spinnerForInitialisert={false} avhengigheter={[props.oversiktStillinger]}>
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