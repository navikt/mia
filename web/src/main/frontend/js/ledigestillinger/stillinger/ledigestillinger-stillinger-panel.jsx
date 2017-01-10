import React from 'react';
import StillingTabell from './ledigestillinger-stillinger-tabell';
import {defineMessages, FormattedMessage} from 'react-intl';

export const meldinger = defineMessages({
    ingenstillinger: {
        id: 'ledigestillinger.stillinger.ingenstillinger',
        defaultMessage: 'Ingen ledige stillinger'
    }
});

const StillingerPanel = (props) => {
    const compareSoknadsfrister = (a, b) => {
        if(a.soknadfrist == null) {
            return 1;
        }
        if(b.soknadfrist == null) {
            return -1;
        }
        return new Date(a.soknadfrist) < new Date(b.soknadfrist) ? -1 : 1;
    };

    const stillingerFraProps = props.yrkesgruppe.stillinger.sort(compareSoknadsfrister);
    const stillinger = stillingerFraProps.length > 0 ?
        <StillingTabell stillinger={stillingerFraProps} /> :
        <FormattedMessage {...meldinger.ingenstillinger} />;

    return (
        <div className="blokk-m panel panel-fremhevet">
            <h2 className="blokk-m typo-undertittel">{props.yrkesgruppe.navn} ({props.yrkesgruppe.antallStillinger})</h2>
            {stillinger}
        </div>
    );

};

export default StillingerPanel;
