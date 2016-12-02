import React from "react";
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';
import {getStillingerTotalt, getKommuneMedData, getKommunerForValgtFylke} from './ledigestillinger-oversikt-utils';

const meldinger = defineMessages({
    velgFylke: {
        id: 'ledigestillinger.oversikt.tabell.velgfylke',
        defaultMessage: 'Velg fylke/fylker'
    },
    velgKommune: {
        id: 'ledigestillinger.oversikt.tabell.velgkommune',
        defaultMessage: 'Velg kommune/kommuner'
    },
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
        defaultMessage: 'Ledige stillinger ({antall, number})'
    }
});

const SelectElement = props => (
    <div className={props.className}>
        <label htmlFor={props.id}>
            <FormattedMessage {...props.label}/>
        </label>
        <div className="select-container input-fullbredde">
            <select id={props.id} name={props.name} defaultValue={props.valgt} value={props.value} onChange={event => props.onChange(event.target.value)}>
                { props.alternativer.map(alternativ => (
                    <option key={alternativ.value} value={alternativ.value}>
                        {alternativ.navn}
                    </option>
                ))}
            </select>
        </div>
    </div>
);

const KommuneTabellRad = props => (
    <tr key={props.kommune.kommunenummer}>
        <th scope="row">{props.kommune.navn}</th>
        <td className="text-center">{props.kommune.antallLedige}</td>
        <td className="text-center">{props.kommune.antallStillinger}</td>
    </tr>
);

export const Oversiktstabell = props => {
    const kommunerForValgtFylke = getKommunerForValgtFylke(props.valgtFylke, props.fylker);
    const stillingerTotalt = getStillingerTotalt(kommunerForValgtFylke, props.kommunedata);

    return (
        <div>
            <form className="blokk-l" noValidate>
                <SelectElement
                    id="select-fylke"
                    className="blokk-s"
                    name="fylke"
                    value={props.valgtFylke}
                    onChange={props.velgFylke}
                    label={meldinger.velgFylke}
                    alternativer={props.fylker.map(fylke => ({navn: fylke.navn, value: fylke.navn}))}
                />

                <SelectElement
                    id="select-kommune"
                    name="kommune"
                    value={props.valgtKommune}
                    onChange={props.velgKommune}
                    label={meldinger.velgKommune}
                    alternativer={kommunerForValgtFylke.map(kommune => ({navn: kommune.navn, value: kommune.kommunenummer}))}
                />
            </form>

            <h3 className="typo-etikett">{props.valgtFylke}</h3>
            <table className="tabell blokk-s">
                <thead>
                    <tr>
                        <th scope="col">
                            <FormattedMessage {...meldinger.tabellOverskriftKommune}/>
                        </th>
                        <th scope="col" className="text-center">
                            <FormattedMessage {...meldinger.tabellOverskriftLedige} values={{antall: stillingerTotalt.antallLedige}}/>
                        </th>
                        <th scope="col" className="text-center">
                            <FormattedMessage {...meldinger.tabellOverskriftStillinger} values={{antall: stillingerTotalt.antallStillinger}}/>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    {kommunerForValgtFylke.map(kommune => getKommuneMedData(kommune, props.kommunedata)).map(kommune => <KommuneTabellRad key={kommune.kommunenummer} kommune={kommune}/>)}
                </tbody>
            </table>
        </div>
    );
};

export default injectIntl(Oversiktstabell);