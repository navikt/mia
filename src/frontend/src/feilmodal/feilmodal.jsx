import React from 'react';
import {connect} from 'react-redux';
import {feilmodalId} from './feilmodal-actions';
import Modal from '../felles/modal/modal';
import {defineMessages, FormattedMessage} from 'react-intl';

const meldinger = defineMessages({
    feilmeldingTittel: {
        id: 'feilmodal.tittel',
        defaultMessage: 'Ooops... noe gikk galt!'
    },
    feilmeldingTekst: {
        id: 'feilmodal.tekst',
        defaultMessage: 'Noe gikk galt ved henting av innhold fra baksystemene. Dette gjør at siden ikke vil fungere som forventet. Du kan prøve å oppdatere siden, eller fortsette å bruke den.'
    },
    knappOppdater: {
        id: 'feilmodal.knapp.oppdater',
        defaultMessage: 'Oppdater'
    },
    knappLukk: {
        id: 'feilmodal.knapp.lukk',
        defaultMessage: 'Fortsett'
    },
    feilkode: {
        id: 'feilmodal.feilkode',
        defaultMessage: 'Feilkode: {feilkode}'
    }
});

const feilmodal = (props) => {
    const onClickOppdater = () => window.location.reload();
    const callIdMelding = (props.feil == null) ? null : <p><FormattedMessage {...meldinger.feilkode} values={{feilkode: props.feil}}/></p>;

    return (
        <Modal id={feilmodalId} tittel={meldinger.feilmeldingTittel} feilmodal={true} onLagre={onClickOppdater}>
            <div>
                <div className="blokk">
                    <p><FormattedMessage {...meldinger.feilmeldingTekst} /></p>
                    {callIdMelding}
                </div>
            </div>
        </Modal>
    );
};

const stateToProps = state => ({feil: state.modal.feilkode});
export default connect(stateToProps)(feilmodal);