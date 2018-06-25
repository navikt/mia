import React from 'react';
import {FormattedMessage, defineMessages, injectIntl} from 'react-intl';
import {ALTERNATIV_ALLE} from "../../felles/konstanter";
import { HjelpetekstUnder } from 'nav-frontend-hjelpetekst';
import { Select } from 'nav-frontend-skjema';

const meldinger = defineMessages({
    velgstillingskategori: {
        id: 'ledigestillinger.bransjer.velgstillingskategori',
        defaultMessage: 'Velg stillingskategori'
    },
    alternativAlle: {
        id: 'ledigestillinger.bransjer.alle',
        defaultMessage: 'Alle ({antall})'
    },
    hjelpetekstTekst: {
        id: 'ledigestillinger.bransjer.hjelpetekst.innhold',
        defaultMessage: 'Valgte stillingskategorier og arbeidsområder danner grunnlag for stillingene og statistikken som vises på siden.'
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
        <div>
            <label htmlFor="select-bransje">
                <FormattedMessage {...meldinger.velgstillingskategori} />
            </label>
            <HjelpetekstUnder id="bransje-dropdown-hjelpetekst">
                <FormattedMessage {...meldinger.hjelpetekstTekst}/>
            </HjelpetekstUnder>
        </div>
    );

    return(
        <div className="bransjevalg blokk-s">
            <div>
                <Select id="select-bransje" bredde="fullbredde" label={selectLabel} selected={props.yrkesomrade} value={props.yrkesomrade} onChange={e => props.onClick(e.target.value)}>
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
