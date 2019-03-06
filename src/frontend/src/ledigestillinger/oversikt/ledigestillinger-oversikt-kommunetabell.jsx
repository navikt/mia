import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import {getStillingerTotalt, getKommuneMedData, compareOmrader} from './ledigestillinger-oversikt-utils';
import {Innholdstittel} from "nav-frontend-typografi";

const meldinger = defineMessages({
    tabellOverskriftKommune: {
        id: 'ledigestillinger.oversikt.tabell.overskriftkommune',
        defaultMessage: 'Kommune'
    },
    tabellOverskriftLedige: {
        id: 'ledigestillinger.oversikt.tabell.overskriftledige',
        defaultMessage: 'Arbeidsledige ({antall})'
    },
    tabellOverskriftStillinger: {
        id: 'ledigestillinger.oversikt.tabell.overskriftstillinger',
        defaultMessage: 'Ledige stillinger'
    }
});

export const KommuneTabellRad = props => (
    <tr key={props.kommune.kommunenummer}>
        <th scope="row">{props.kommune.navn}</th>
        <td className="text-center">{props.kommune.antallLedige == null ? "<4" : props.kommune.antallLedige}</td>
        <td className="text-center">{props.kommune.antallStillinger}</td>
    </tr>
);

export const KommuneTabell = ({fylke, kommuner, stillinger}) => {
    const stillingerTotalt = getStillingerTotalt(kommuner, stillinger);
    const skalHaKrokodilleTegn = stillinger.some(stilling => stilling.antallLedige == null);
    const antallArbeidsledigeTekst = (skalHaKrokodilleTegn ? ">" : "" ) + stillingerTotalt.antallLedige;
    const fylkenavn = fylke != null ? fylke.navn : "";
    const tabellrad = kommuner.sort(compareOmrader)
        .map(kommune => getKommuneMedData(kommune, stillinger))
        .map(kommune => <KommuneTabellRad key={kommune.id} kommune={kommune}/>);

    return (
        <section className="blokk">
            <Innholdstittel tag="h3" >{fylkenavn}</Innholdstittel>
            <div className="tabell-container">
                <table className="tabell blokk-s">
                    <thead>
                        <tr>
                            <th scope="col" className="typo-etikett-stor kommune-navn">
                                <FormattedMessage {...meldinger.tabellOverskriftKommune}/>
                            </th>
                            <th scope="col" className="text-center typo-etikett-stor">
                                <FormattedMessage {...meldinger.tabellOverskriftLedige} values={{antall: antallArbeidsledigeTekst}}/>
                            </th>
                            <th scope="col" className="text-center typo-etikett-stor">
                                <FormattedMessage {...meldinger.tabellOverskriftStillinger} />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        {tabellrad}
                    </tbody>
                </table>
            </div>
        </section>
    );
};

export default KommuneTabell;
