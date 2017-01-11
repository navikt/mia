import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import {getStillingerTotalt, getKommuneMedData, compareOmrader} from './ledigestillinger-oversikt-utils';

const meldinger = defineMessages({
    tabellOverskriftKommune: {
        id: 'ledigestillinger.oversikt.tabell.overskriftkommune',
        defaultMessage: 'Kommune'
    },
    tabellOverskriftLedige: {
        id: 'ledigestillinger.oversikt.tabell.overskriftledige',
        defaultMessage: 'Arbeidsledige ({antall, number})'
    },
    tabellOverskriftStillinger: {
        id: 'ledigestillinger.oversikt.tabell.overskriftstillinger',
        defaultMessage: 'Ledige stillinger'
    }
});

export const KommuneTabellRad = props => (
    <tr key={props.kommune.kommunenummer}>
        <td scope="row">{props.kommune.navn}</td>
        <td className="text-center">{props.kommune.antallLedige}</td>
        <td className="text-center">{props.kommune.antallStillinger}</td>
    </tr>
);

export const KommuneTabell = ({fylke, kommuner, stillinger}) => {
    const stillingerTotalt = getStillingerTotalt(kommuner, stillinger);
    const fylkenavn = fylke != null ? fylke.navn : "";
    const tabellrad = kommuner.sort(compareOmrader)
        .map(kommune => getKommuneMedData(kommune, stillinger))
        .map(kommune => <KommuneTabellRad key={kommune.id} kommune={kommune}/>);

    return (
        <section className="blokk">
            <h2 className="typo-etikett">{fylkenavn}</h2>
            <table className="tabell blokk-s">
                <thead>
                    <tr>
                        <th scope="col" className="typo-etikett-stor kommune-navn">
                            <FormattedMessage {...meldinger.tabellOverskriftKommune}/>
                        </th>
                        <th scope="col" className="text-center typo-etikett-stor">
                            <FormattedMessage {...meldinger.tabellOverskriftLedige} values={{antall: stillingerTotalt.antallLedige}}/>
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
        </section>
    );
};

export default KommuneTabell;