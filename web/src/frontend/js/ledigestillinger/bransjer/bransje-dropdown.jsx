import React from 'react';
import {FormattedMessage, defineMessages, injectIntl} from 'react-intl';
import {ALTERNATIV_ALLE} from "../../felles/konstanter";
import Hjelpetekst from '../../felles/hjelpetekst/hjelpetekst';

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
        defaultMessage: 'Stillingskatergorier og arbeidsområder'
    },
    hjelpetekstTekst: {
        id: 'ledigestillinger.bransjer.hjelpetekst.innhold',
        defaultMessage: 'Valgte stillingskategorier og arbeidsområder vil danne grunnlag for statistikken som vises på siden.'
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
            <div className="hjelpetekst-nedover">
                <label className="inline" htmlFor="select-bransje">
                    <FormattedMessage {...meldinger.velgstillingskategori} />
                </label>
                <Hjelpetekst
                    id="bransje-hjelpetekst"
                    tittel={<FormattedMessage {...meldinger.hjelpetekstTittel}/>}
                    tekst={<FormattedMessage {...meldinger.hjelpetekstTekst}/>}
                    inline={true}
                />
            </div>
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
