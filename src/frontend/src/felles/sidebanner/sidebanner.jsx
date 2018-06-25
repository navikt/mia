import React from 'react';
import { Sidetittel } from 'nav-frontend-typografi';
import { FormattedMessage, defineMessages } from 'react-intl';

const tekster = defineMessages({
    tittel: {
        id: 'hovedside.tittel',
        defaultMessage: 'Muligheter i arbeidsmarkedet'
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
                </div>
            </div>
        </div>
    );
}

export default Sidebanner;