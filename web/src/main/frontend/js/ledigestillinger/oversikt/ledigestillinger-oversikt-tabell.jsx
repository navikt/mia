import React from "react";
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';

import { fylker } from "./ledigestillinger-overiskt-mockdata";

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
            <select id={props.id} name={props.name} value={props.valgt} onChange={() => props.onChange(event.target.value)}>
                { props.alternativer.map(alternativ => (
                    <option key={alternativ} value={alternativ}>{alternativ}</option>
                ))}
            </select>
        </div>
    </div>
);

const Oversiktstabell = props => {
    return (
        <div>
            <form noValidate>
                <SelectElement
                    id="select-fylke"
                    className="blokk-s"
                    name="fylke"
                    value={props.valgtFylke}
                    onChange={props.valgFylke}
                    label={meldinger.velgFylke}
                    alternativer={fylker.map(fylke => fylke.navn)}
                />

                <SelectElement
                    id="select-kommune"
                    name="kommune"
                    value={props.valgtKommune}
                    onChange={props.valgKommune}
                    label={meldinger.velgKommune}
                    alternativer={fylker.find(fylke => fylke.navn === props.valgtFylke).kommuner}
                />
            </form>
        </div>
    );
};

export default injectIntl(Oversiktstabell);