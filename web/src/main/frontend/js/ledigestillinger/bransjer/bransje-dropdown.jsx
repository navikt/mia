import React from 'react';
import {FormattedMessage, defineMessages, injectIntl} from 'react-intl';
import {ALTERNATIV_ALLE} from "../../felles/konstanter";

const meldinger = defineMessages({
    velgstillingskategori: {
        id: 'ledigestillinger.bransjer.velgstillingskategori',
        defaultMessage: 'Velg stillingskategori'
    },
    alternativAlle: {
        id: 'ledigestillinger.bransjer.alle',
        defaultMessage: 'Alle ({antall})'
    }
});

export const BransjeDropdown = (props) => {
    const formatMessage = props.intl.formatMessage;
    const yrkesomrader = props.yrkesomrader.map(row => (
        <option value={row.id} key={row.id}>
            {row.navn} ({row.antallStillinger})
        </option>
    ));

    return(
        <div className="bransjevalg blokk-s">
            <label htmlFor="select-bransje">
                <FormattedMessage {...meldinger.velgstillingskategori} />
            </label>
            <div className="select-container input-fullbredde">
                <select id="select-bransje" value={props.yrkesomrade}
                        onChange={e => props.onClick(e.target.value)}>
                    <option value={ALTERNATIV_ALLE}>
                        {formatMessage(meldinger.alternativAlle, {antall: props.totaltAntall + ''})}
                    </option>
                    {yrkesomrader}
                </select>
            </div>
        </div>
    );
};

export default injectIntl(BransjeDropdown);
