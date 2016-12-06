import React from 'react';
import {FormattedMessage} from 'react-intl';
import {findTotaltAntallJobber} from './ledigestillinger-bransjer-util';

export const BransjeDropdown = (props) => {
    return(
        <div className="bransjevalg blokk-s">
            <label htmlFor="select-bransje">
                <FormattedMessage {...props.meldinger.velgbransje} />
            </label>
            <div className="select-container input-fullbredde">
                <select id="select-bransje" value={props.yrkesomrade}
                        onChange={e => props.onClick(e.target.value)}>
                    <option value="alle">Alle ({findTotaltAntallJobber(props.yrkesomrader)})</option>
                    { props.yrkesomrader.map(row => <option value={row.bransjeid} key={row.bransjeid}>{row.bransjenavn} ({row.antallStillinger})</option> )}
                </select>
            </div>
        </div>
    );
};

export default BransjeDropdown;
