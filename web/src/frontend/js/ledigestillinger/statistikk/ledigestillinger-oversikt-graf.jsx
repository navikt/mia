import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import LinjeGraf from '../../felles/graf/linje-graf';
import Hjelpetekst from '../../felles/hjelpetekst/hjelpetekst';

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
    }
});

const LedigestillingerOversiktGraf = ({ tabell }) => {
    const grafData = {
        id: 'arbeidsledighet-graf',
        tabellOverskrift: tekster.tabellOverskrift,
        tabell,
        periodetype: "Måned",
        yEnhet: '',
        yTittel: 'Antall'
    };

    return (
        <div className="ledigestillinger-oversikt-graf">
            <div className="blokk-s hjelpetekst-overskrift">
                <h2 className="typo-innholdstittel">
                    <FormattedMessage {...tekster.tabellOverskrift} />
                </h2>
                <Hjelpetekst
                    id="bransje-hjelpetekst"
                    tittel={<FormattedMessage {...tekster.hjelpetekstTittel}/>}
                    tekst={<FormattedMessage {...tekster.hjelpetekstTekst}/>}
                    inline={true}
                />
            </div>
            <LinjeGraf {...grafData} />
        </div>
    );
};

export default LedigestillingerOversiktGraf;
