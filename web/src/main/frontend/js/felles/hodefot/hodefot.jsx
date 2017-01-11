import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';

const meldinger = defineMessages({
    hodefotTittel: {
        id: 'hodefot.tittel',
        defaultMessage: 'Muligheter i arbeidsmarkedet'
    }
});


export const Hodefot = () => {
    return (
        <div className="header">
            <div className="header-container">
                <img src="/mia/img/nav_logo.svg" alt="nav logo" className="header-logo"/>
                <h1 className="typo-normal header-appnavn"><FormattedMessage {...meldinger.hodefotTittel}/></h1>
            </div>
        </div>
    );
};

export default Hodefot;
