import React from 'react';
import {FormattedMessage} from 'react-intl';
import bransjemock from './ledige-stillinger-bransjer-mockdata';

export const BransjeDropdown = (props) => {
    return(
        <div className="bransjevalg blokk-s">
            <label htmlFor="select-bransje">
                <FormattedMessage {...props.meldinger.velgbransje} />
            </label>
            <div className="select-container input-fullbredde">
                <select id="select-bransje" value={props.yrkesomrade}
                        onChange={e => props.onClick(e.target.value)}>
                    <option value="alle">Alle ({props.findTotaltAntallJobber(bransjemock)})</option>
                    { bransjemock.map(row => <option value={row.id} key={row.id}>{row.navn} ({row.antall})</option> )}
                </select>
            </div>
        </div>
    );
};

export default BransjeDropdown;
