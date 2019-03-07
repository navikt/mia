import React from 'react';
import {Normaltekst, Sidetittel} from 'nav-frontend-typografi';
import { FormattedMessage, defineMessages } from 'react-intl';

const tekster = defineMessages({
    tittel: {
        id: 'hovedside.tittel',
        defaultMessage: 'Arbeidsmarkedet'
    },
    beskrivelse: {
        id: 'hovedside.beskrivelse',
        defaultMessage: 'Her kan du få oversikt over arbeidsmarkedet og utforske jobbmulighetene i ulike deler av landet og i ulike bransjer'
    }
});

function Sidebanner() {
    return (
        <div className="sidebanner-container">
            <div className="sidebanner-grid">
                <div className="sidebanner blokk-s">
                    <Sidetittel className="sidebanner__tittel">
                        <FormattedMessage {...tekster.tittel} />
                    </Sidetittel>
                    <Normaltekst className="sidebanner__text hidden-xs hidden-sm hidden-md">
                        <FormattedMessage {...tekster.beskrivelse}/>
                    </Normaltekst>
                </div>
            </div>
        </div>
    );
}

export default Sidebanner;
