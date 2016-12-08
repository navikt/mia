import React from 'react';
import {FormattedMessage, defineMessages, injectIntl} from 'react-intl';
import {findTotaltAntallJobber} from './ledigestillinger-bransjer-util';
import {ALTERNATIV_ALLE} from "../../felles/konstanter";

const meldinger = defineMessages({
    velgbransje: {
        id: 'ledigestillinger.bransjer.velgbransje',
        defaultMessage: 'Velg bransje'
    },
    alternativAlle: {
        id: 'ledigestillinger.bransjer.alle',
        defaultMessage: 'Alle ({antall})'
    }
});

export const BransjeDropdown = (props) => {
    const formatMessage = props.intl.formatMessage;

    return(
        <div className="bransjevalg blokk-s">
            <label htmlFor="select-bransje">
                <FormattedMessage {...meldinger.velgbransje} />
            </label>
            <div className="select-container input-fullbredde">
                <select id="select-bransje" value={props.yrkesomrade}
                        onChange={e => props.onClick(e.target.value)}>
                    <option value={ALTERNATIV_ALLE}>
                        {formatMessage(meldinger.alternativAlle, {antall: findTotaltAntallJobber(props.yrkesomrader)})}
                    </option>
                    { props.yrkesomrader.map(row => <option value={row.id} key={row.id}>{row.navn} ({row.antallStillinger})</option> )}
                </select>
            </div>
        </div>
    );
};

export default injectIntl(BransjeDropdown);
