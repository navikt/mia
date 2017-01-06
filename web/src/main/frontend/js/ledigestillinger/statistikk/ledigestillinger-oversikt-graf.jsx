import React from 'react';
import {defineMessages} from 'react-intl';
import LinjeGraf from '../../felles/graf/linje-graf';
import {lagManeder} from '../../felles/graf/standard-graf-properties';

const grafTekster = defineMessages({
    tabellOverskrift: {
        id: 'ledigestillinger.overskrift.graf.arbeidsledighet',
        defaultMessage: 'Ledighet siste 12 måneder'
    }

});


const LedigestillingerOversiktGraf = ({ tabell }) => {
    const grafData = {
        id: 'arbeidsledighet-graf',
        serieData: tabell,
        tabellOverskrift: grafTekster.tabellOverskrift,
        serie: lagManeder,
        periodetype: "Måned",
        yEnhet: '',
        yTittel: ''
    };

    return (
        <div className="ledigestillinger-oversikt-graf">
            <LinjeGraf {...grafData} />
        </div>
    );
};

export default LedigestillingerOversiktGraf;
