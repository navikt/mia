import React from 'react';
import {FormattedMessage, defineMessages, injectIntl} from 'react-intl';
import {ALTERNATIV_ALLE} from "../../felles/konstanter";
import {Select} from 'nav-frontend-skjema';
import {Undertittel} from 'nav-frontend-typografi';

const meldinger = defineMessages({
    velgstillingskategori: {
        id: 'ledigestillinger.bransjer.velgstillingskategori',
        defaultMessage: 'Velg bransje'
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

    const selectLabel = (
        <div className="bransje-header">
            <Undertittel className="inline">
                <FormattedMessage {...meldinger.velgstillingskategori} />
            </Undertittel>
        </div>
    );

    return (
        <div className="bransjevalg blokk-s">
            <div>
                <Select id="select-bransje" bredde="fullbredde" label={selectLabel} selected={props.yrkesomrade}
                        value={props.yrkesomrade} onChange={e => props.onClick(e.target.value)}>
                    <option value={ALTERNATIV_ALLE}>
                        {formatMessage(meldinger.alternativAlle, {antall: props.totaltAntall + ''})}
                    </option>
                    {yrkesomrader}
                </Select>
            </div>
        </div>
    );
};

export default injectIntl(BransjeDropdown);
