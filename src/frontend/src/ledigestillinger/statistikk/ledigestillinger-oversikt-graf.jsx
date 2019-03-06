import React from 'react';
import {defineMessages} from 'react-intl';
import LinjeGraf from '../../felles/graf/linje-graf';
import {
    ValgteFylker,
    ValgteKommuner,
    ValgtStillingskategori,
    ValgteArbeidsomrader,
    ValgtHeleNorge
} from '../../felles/filtervalg/filtervalgVisning';

const tekster = defineMessages({
    tabellOverskrift: {
        id: 'ledigestillinger.overskrift.graf.arbeidsledighet',
        defaultMessage: 'Ledighet siste 13 måneder'
    },
    hjelpetekstTekst: {
        id: 'ledigestillinger.graf.hjelpetekst.innhold',
        defaultMessage: 'I tabellen ser du to grafer som viser utviklingen i arbeidsmarkedet for det området du har valgt. Den sorte grafen viser antall arbeidsledige de siste 13 månedene. Den grønne grafen viser ledige stillinger i samme periode.'
    },
    valgtStillingskategori: {
        id: 'ledigestillinger.oversikt.statistikk.valgtstillingskategori',
        defaultMessage: 'Valgt stillingskategori: '
    },
    valgteArbeidsomrader: {
        id: 'ledigestillinger.oversikt.statistikk.valgtearbeidsomrader',
        defaultMessage: 'Valgte arbeidsområder: '
    },
    visGraf: {
        id: 'grafswitcher.visgraf',
        defaultMessage: 'Vis som graf'
    },
    visTabell: {
        id: 'grafswitcher.vistabell',
        defaultMessage: 'Vis som tabell'
    }
});

const LedigestillingerOversiktGraf = ({tabell, valgteFylker, valgteKommuner, omrader, valgtyrkesomrade, yrkesomrader, valgteyrkesgrupper, yrkesgrupper}) => {
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

    const valgtHeleLandet = !harData(valgteFylker) && !harData(valgteKommuner) ?
        <ValgtHeleNorge valgtOmrade={tekster.valgtOmrade}/> : <noscript/>;

    return (

        <div>
            <div className="ledigestillinger-oversikt-graf">
                <div>
                    {valgtHeleLandet}
                    <ValgteFylker valgteFylker={valgteFylker} omrader={omrader}/>
                    <ValgteKommuner valgteKommuner={valgteKommuner} omrader={omrader}/>
                    <ValgtStillingskategori valgtYrkesomrade={valgtyrkesomrade} yrkesomrader={yrkesomrader}
                                            tekst={tekster.valgtStillingskategori}/>
                    <ValgteArbeidsomrader valgteYrkesgrupper={valgteyrkesgrupper} yrkesgrupper={yrkesgrupper}
                                          tekst={tekster.valgteArbeidsomrader}/>
                </div>
                <LinjeGraf {...grafData} />
            </div>
        </div>
    );
};

export default LedigestillingerOversiktGraf;
