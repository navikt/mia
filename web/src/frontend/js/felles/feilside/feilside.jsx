import React from 'react';
import {FormattedMessage} from 'react-intl';

const Feilside = (props) => (
    <div className="panel panel-ramme blokk-l">
        <h2 className="hode hode-dekorert hode-feil"><FormattedMessage {...props.tittel}/></h2>
        <p><FormattedMessage {...props.melding}/></p>
    </div>
);

export default Feilside;
