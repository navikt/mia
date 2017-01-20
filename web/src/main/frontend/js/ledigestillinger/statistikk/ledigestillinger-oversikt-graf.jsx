import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import LinjeGraf from '../../felles/graf/linje-graf';

const grafTekster = defineMessages({
    tabellOverskrift: {
        id: 'ledigestillinger.overskrift.graf.arbeidsledighet',
        defaultMessage: 'Ledighet siste 12 måneder'
    }
});

const LedigestillingerOversiktGraf = ({ tabell }) => {
    const grafData = {
        id: 'arbeidsledighet-graf',
        tabellOverskrift: grafTekster.tabellOverskrift,
        tabell,
        periodetype: "Måned",
        yEnhet: '',
        yTittel: ''
    };

    return (
        <div className="ledigestillinger-oversikt-graf">
            <h2><FormattedMessage {...grafTekster.tabellOverskrift} /></h2>
            <LinjeGraf {...grafData} />
        </div>
    );
};

export default LedigestillingerOversiktGraf;
