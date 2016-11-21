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

const Oversiktstabell = () => {
    return (
        <div>
            <form noValidate>
                <label htmlFor="select-fylke">
                    <FormattedMessage {...meldinger.velgFylke}/>
                </label>
                <div className="select-container blokk-s">
                    <select id="select-fylke" name="fylke">
                        {fylker.map(fylke => (
                            <option key={fylke.navn} value={fylke.navn}>
                                {fylke.navn}
                            </option>
                        ))}
                    </select>
                </div>

                <label htmlFor="select-kommune">
                    <FormattedMessage {...meldinger.velgKommune}/>
                </label>
                <div className="select-container blokk-s">
                    <select id="select-kommune" name="kommune">
                        {fylker.find(fylke => fylke.navn === 'Kristiansand').kommuner.map(kommune => (
                            <option key={kommune} value={kommune}>
                                {kommune}
                            </option>
                        ))}
                    </select>
                </div>
            </form>
        </div>
    );
};

export default injectIntl(Oversiktstabell);