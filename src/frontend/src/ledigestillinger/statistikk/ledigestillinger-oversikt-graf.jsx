import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import Hjelpetekst from 'nav-frontend-hjelpetekst';
import LinjeGraf from '../../felles/graf/linje-graf';
import {ValgteFylker, ValgteKommuner, ValgtStillingskategori, ValgteArbeidsomrader, ValgtHeleNorge} from '../../felles/filtervalg/filtervalgVisning';

const tekster = defineMessages({
    tabellOverskrift: {
        id: 'ledigestillinger.overskrift.graf.arbeidsledighet',
        defaultMessage: 'Ledighet siste 13 måneder'
    },
    hjelpetekstTekst: {
        id: 'ledigestillinger.graf.hjelpetekst.innhold',
        defaultMessage: 'Grafen/tabellen viser utviklingen i antall helt arbeidsledige og tilgang på ledige stillinger.'
    },
    valgtStillingskategori: {
        id: 'ledigestillinger.oversikt.statistikk.valgtstillingskategori',
        defaultMessage: 'Valgt stillingskategori:'
    },
    valgteArbeidsomrader: {
        id: 'ledigestillinger.oversikt.statistikk.valgtearbeidsomrader',
        defaultMessage: 'Valgte arbeidsområder:'
    }
});

const LedigestillingerOversiktGraf = ({ tabell, valgteFylker, valgteKommuner, omrader, valgtyrkesomrade, yrkesomrader, valgteyrkesgrupper, yrkesgrupper }) => {
    const grafData = {
        id: 'arbeidsledighet-graf',
        tabellOverskrift: tekster.tabellOverskrift,
        tabell,
        periodetype: "Måned",
        yEnhet: '',
        yTittel: 'Antall'
    };

    const harData = valgtData => {
        return valgtData.length !== 0;
    };

    const valgtHeleLandet = !harData(valgteFylker) && !harData(valgteKommuner) ? <ValgtHeleNorge valgtOmrade={tekster.valgtOmrade} />: <noscript />;

    return (
        <div className="ledigestillinger-oversikt-graf">
            <div className="blokk-s">
                <h2 className="typo-innholdstittel hjelpetekst-overskrift">
                    <FormattedMessage {...tekster.tabellOverskrift} />
                </h2>
                <Hjelpetekst id="hjelpetekst.bransjer">
                    <FormattedMessage {...tekster.hjelpetekstTekst} />
                </Hjelpetekst>

            </div>
            <div>
                {valgtHeleLandet}
                <ValgteFylker valgteFylker={valgteFylker} omrader={omrader} />
                <ValgteKommuner valgteKommuner={valgteKommuner} omrader={omrader} />
                <ValgtStillingskategori valgtYrkesomrade={valgtyrkesomrade} yrkesomrader={yrkesomrader} tekst={tekster.valgtStillingskategori} />
                <ValgteArbeidsomrader valgteYrkesgrupper={valgteyrkesgrupper} yrkesgrupper={yrkesgrupper} tekst={tekster.valgteArbeidsomrader} />
            </div>
            <LinjeGraf {...grafData} />
        </div>
    );
};

export default LedigestillingerOversiktGraf;
