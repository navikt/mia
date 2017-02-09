import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import LinjeGraf from '../../felles/graf/linje-graf';
import Hjelpetekst from '../../felles/hjelpetekst/hjelpetekst';
import {ValgteFylker, ValgteKommuner, ValgtStillingskategori, ValgteArbeidsomrader, ValgtHeleNorge} from '../../felles/filtervalg/filtervalgVisning';

const tekster = defineMessages({
    tabellOverskrift: {
        id: 'ledigestillinger.overskrift.graf.arbeidsledighet',
        defaultMessage: 'Ledighet siste 12 måneder'
    },
    hjelpetekstTittel: {
        id: 'ledigestillinger.graf.hjelpetekst.tittel',
        defaultMessage: 'Historikk over arbeidsledige og ledigestillinger'
    },
    hjelpetekstTekst: {
        id: 'ledigestillinger.graf.hjelpetekst.innhold',
        defaultMessage: 'Grafen viser oversikt over antallet arbeidsledige og ledige stillinger siste 13 måneder. Grafen tar hensyn til valgte yrker og områder.'
    },
    valgteKommuner: {
        id: 'ledigestillinger.oversikt.statistikk.valgtekommuner',
        defaultMessage: 'Valgte kommuner:'
    },
    valgteFylker: {
        id: 'ledigestillinger.oversikt.statistikk.valgtefylker',
        defaultMessage: 'Valgte fylker:'
    },
    valgtStillingskategori: {
        id: 'ledigestillinger.oversikt.statistikk.valgtstillingskategori',
        defaultMessage: 'Valgt stillingskategori:'
    },
    valgteArbeidsomrader: {
        id: 'ledigestillinger.oversikt.statistikk.valgtearbeidsomrader',
        defaultMessage: 'Valgte arbeidsområder:'
    },
    valgtOmrade: {
        id: 'ledigestillinger.oversikt.statistikk.valgtomrade',
        defaultMessage: 'Valgt område:'
    },
    heleNorge: {
        id: 'ledigestillinger.oversikt.statistikk.helenorge',
        defaultMessage: 'Hele Norge'
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

    const valgteFylkerVisning = harData(valgteFylker) ? <ValgteFylker valgteFylker={valgteFylker} tekst={tekster.valgteFylker} omrader={omrader} /> : <noscript />;
    const valgteKommunerVisning = harData(valgteKommuner) ? <ValgteKommuner valgteKommuner={valgteKommuner} tekst={tekster.valgteKommuner} omrader={omrader} /> : <noscript />;
    const valgteYrkesomradeVisning = harData(valgtyrkesomrade) ? <ValgtStillingskategori valgtYrkesomrade={valgtyrkesomrade} yrkesomrader={yrkesomrader} tekst={tekster.valgtStillingskategori} /> : <noscript />;
    const valgteYrkesgrupperVisning = harData(valgteyrkesgrupper) ? <ValgteArbeidsomrader valgteYrkesgrupper={valgteyrkesgrupper} yrkesgrupper={yrkesgrupper} tekst={tekster.valgteArbeidsomrader} /> : <noscript />;
    const valgtHeleLandet = !harData(valgteFylker) && !harData(valgteKommuner) ? <ValgtHeleNorge valgtOmrade={tekster.valgtOmrade} heleNorge={tekster.heleNorge} />: <noscript />;

    return (
        <div className="ledigestillinger-oversikt-graf">
            <div className="blokk-s hjelpetekst-overskrift">
                <h2 className="typo-innholdstittel">
                    <FormattedMessage {...tekster.tabellOverskrift} />
                </h2>
                <Hjelpetekst
                    id="graf-hjelpetekst"
                    tittel={<FormattedMessage {...tekster.hjelpetekstTittel}/>}
                    tekst={<FormattedMessage {...tekster.hjelpetekstTekst}/>}
                    inline={true}
                />
            </div>
            <div>
                {valgtHeleLandet}
                {valgteFylkerVisning}
                {valgteKommunerVisning}
                {valgteYrkesomradeVisning}
                {valgteYrkesgrupperVisning}
            </div>
            <LinjeGraf {...grafData} />
        </div>
    );
};

export default LedigestillingerOversiktGraf;
