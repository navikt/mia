import React from "react";
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';

const meldinger = defineMessages({
    velgFylke: {
        id: 'ledigestillinger.oversikt.tabell.velgfylke',
        defaultMessage: 'Velg fylke/fylker'
    },
    velgKommune: {
        id: 'ledigestillinger.oversikt.tabell.velgkommune',
        defaultMessage: 'Velg kommune/kommuner'
    }
});

const SelectElement = props => (
    <div className={props.className}>
        <label htmlFor={props.id}>
            <FormattedMessage {...props.label}/>
        </label>
        <div className="select-container input-fullbredde">
            <select id={props.id} name={props.name} value={props.valgt} onChange={event => props.onChange(event.target.value)}>
                { props.alternativer.map(alternativ => (
                    <option key={alternativ} value={alternativ}>{alternativ}</option>
                ))}
            </select>
        </div>
    </div>
);

const Oversiktstabell = props => {
    const getKommunerForValgtFylke = () => props.valgtFylke === null ? [] : props.fylker.find(fylke => fylke.navn === props.valgtFylke).kommuner;

    return (
        <div>
            <form noValidate>
                <SelectElement
                    id="select-fylke"
                    className="blokk-s"
                    name="fylke"
                    value={props.valgtFylke}
                    onChange={props.velgFylke}
                    label={meldinger.velgFylke}
                    alternativer={props.fylker.map(fylke => fylke.navn)}
                />

                <SelectElement
                    id="select-kommune"
                    name="kommune"
                    value={props.valgtKommune}
                    onChange={props.velgKommune}
                    label={meldinger.velgKommune}
                    alternativer={getKommunerForValgtFylke().map(kommune => kommune.navn)}
                />
            </form>
        </div>
    );
};

export default injectIntl(Oversiktstabell);