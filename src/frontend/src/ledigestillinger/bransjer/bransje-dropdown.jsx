import React from 'react';
import {FormattedMessage, defineMessages, injectIntl} from 'react-intl';
import { Undertittel } from 'nav-frontend-typografi';
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
    hjelpetekstTittel: {
        id: 'ledigestillinger.bransjer.hjelpetekst.tittel',
        defaultMessage: 'Valgte stillingskategorier og arbeidsområder'
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

    return(
        <div className="bransjevalg blokk-s">
            <div className="blokk-xxs">
                <label htmlFor="select-bransje">
                    <FormattedMessage {...meldinger.velgstillingskategori} />
                </label>
                <HjelpetekstUnder id="bransje-dropdown-hjelpetekst">
                    <div>
                        <Undertittel className="blokk-xxs">
                            <FormattedMessage {...meldinger.hjelpetekstTittel}/>
                        </Undertittel>
                        <FormattedMessage {...meldinger.hjelpetekstTekst}/>
                    </div>
                </HjelpetekstUnder>
            </div>
            <div>
                <Select id="select-bransje" bredde="xxl" selected={props.yrkesomrade} value={props.yrkesomrade} onChange={e => props.onClick(e.target.value)}>
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
