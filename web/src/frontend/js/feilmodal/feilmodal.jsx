import React from 'react';
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
        defaultMessage: 'Noe gikk galt ved henting av innhold fra baksystemene. Dette gjøt at siden ikke vil fungere som forventet. Du kan prøve å oppdatere siden, eller fortsette å bruke den.'
    },
    knappOppdater: {
        id: 'feilmodal.knapp.oppdater',
        defaultMessage: 'Oppdater'
    },
    knappLukk: {
        id: 'feilmodal.knapp.lukk',
        defaultMessage: 'Fortsett'
    }
});

const feilmodal = () => {
    const onClickOppdater = () => location.reload();

    return (
        <Modal id={feilmodalId} tittel={meldinger.feilmeldingTittel} feilmodal={true} onLagre={onClickOppdater}>
            <div>
                <div className="blokk">
                    <FormattedMessage {...meldinger.feilmeldingTekst} />
                </div>
            </div>
        </Modal>
    );
};
export default feilmodal;