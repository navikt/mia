import React from 'react';
import {FormattedMessage, defineMessages} from 'react-intl';
import {findTotaltAntallJobber} from './ledigestillinger-bransjer-util';

const meldinger = defineMessages({
    velgbransje: {
        id: 'ledigestillinger.bransjer.velgbransje',
        defaultMessage: 'Velg bransje'
    }
});

export const BransjeDropdown = (props) => {
    return(
        <div className="bransjevalg blokk-s">
            <label htmlFor="select-bransje">
                <FormattedMessage {...meldinger.velgbransje} />
            </label>
            <div className="select-container input-fullbredde">
                <select id="select-bransje" value={props.yrkesomrade}
                        onChange={e => props.onClick(e.target.value)}>
                    <option value="alle">Alle ({findTotaltAntallJobber(props.yrkesomrader)})</option>
                    { props.yrkesomrader.map(row => <option value={row.id} key={row.id}>{row.navn} ({row.antallStillinger})</option> )}
                </select>
            </div>
        </div>
    );
};

export default BransjeDropdown;
