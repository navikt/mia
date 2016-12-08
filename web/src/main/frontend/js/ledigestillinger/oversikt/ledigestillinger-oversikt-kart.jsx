import React from "react";
import ISvg from "react-inlinesvg";
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';

const meldinger = defineMessages({
    kartplaceholder: {
        id: 'ledigestillinger.oversikt.kartplaceholder',
        defaultMessage: 'Kart over {fylke}-fylke. Det er {stillinger} ledige stillinger.'
    }
});

const Oversiktskart = () => {
    return (
        <div>
            <h2 className="typo-innholdstittel blokk-s">Nordland, ledige stillinger totalt: 612</h2>
            <div className="oversikt-kart">
                <ISvg src="./img/fylker/nordland.svg">
                    <FormattedMessage {...meldinger.kartplaceholder} fylke="nordland" stillinger="682"/>
                </ISvg>
            </div>
        </div>
    );
};

export default Oversiktskart;