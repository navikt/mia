import React from 'react';
import StillingTabell from './ledigestillinger-stillinger-tabell';
import {defineMessages, FormattedMessage} from 'react-intl';
import {datoStigende} from '../../felles/utils/date-utils';

export const meldinger = defineMessages({
    ingenstillinger: {
        id: 'ledigestillinger.stillinger.ingenstillinger',
        defaultMessage: 'Ingen ledige stillinger'
    }
});

const StillingerPanel = (props) => {
    const stillingerFraProps = props.yrkesgruppe.stillinger.sort((a, b) => datoStigende(a.soknadfrist, b.soknadfrist));
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
