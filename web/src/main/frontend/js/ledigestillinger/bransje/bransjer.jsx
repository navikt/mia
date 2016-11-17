import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import bransjemock from '../../mock/mockdata';

const bransjer = bransjemock;
export const meldinger = defineMessages({
    soketekst: {
        id: 'ledigestillinger.bransjer.soketekst',
        defaultMessage: 'Søk direkte eller klikk videre i kategoriene'
    },
    velgbransje: {
        id: 'ledigestillinger.bransjer.velgbransje',
        defaultMessage: 'Velg bransje'
    },
    boksoverskrift: {
        id: 'ledigestillinger.bransjer.boksoverskrift',
        defaultMessage: 'Ledige jobber totalt {0} fordelt på bransjer'
    }
});

function findTotaltAntallJobber(data) {
    return data.reduce(function(a, b) {return a + b.antall;}, 0);
}

const Bransjer = () => (
    <div className="panel panel-ramme">
        <div className="sokeomrade blokk-s">
            <div>
                <FormattedMessage {...meldinger.soketekst} />
            </div>
            <input type="text" className="bransjeSok" />
        </div>
        <div className="bransjevalg blokk-s">
            <div>
                <FormattedMessage {...meldinger.velgbransje} />
            </div>
            <select id="bransjeDropdown" className="bransjeDropdown">
                <option value="all">Alle ({findTotaltAntallJobber(bransjer)})</option>
                { bransjer.map( row => {
                    return <option value={row.name}  key={row.name}>{row.name} ({row.antall})</option>;
                })}
            </select>
        </div>
    </div>
);

export default Bransjer;