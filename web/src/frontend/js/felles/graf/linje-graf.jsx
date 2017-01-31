import React from 'react';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactHighcharts from 'react-highcharts';
import GrafTabell from './../../felles/graf/graf-tabell.jsx';
import {standardGrafConfig, konfigurerSerie, lagTidsserier, lagManeder} from './../../felles/graf/standard-graf-properties';
import GrafSwitcher from './../../felles/switcher/grafswitcher.jsx';
import tekster from './graf-tekster';

export const LinjeGraf = ({id, tabell, tabellOverskrift, yTittel='', yEnhet='', periodetype="Kvartal", intl: { formatMessage }}) => {
    const serienavn = [
        formatMessage(tekster.serieArbeidsledighet),
        formatMessage(tekster.serieLedigeStillinger)
    ];
    const serienavnKort = [
        formatMessage(tekster.serieArbeidsledighet),
        formatMessage(tekster.serieLedigeStillinger)
    ];

    const aarMaaned = Object.keys(tabell["arbeidsledighet"]);
    const katBase = lagManeder(aarMaaned);

    const kategorier = lagTidsserier(katBase);

    const rawData = [
        Object.values(tabell["arbeidsledighet"]),
        Object.values(tabell["ledigestillinger"])
    ];
    const serier = rawData.map(konfigurerSerie(serienavn, serienavnKort));
    const conf = standardGrafConfig({ kategorier, serier, yEnhet, yTittel });

    const grafTabell = <GrafTabell overskrifter={katBase}
                                   rader={rawData}
                                   periodetype={periodetype}
                                   serieNavn={serienavnKort}
                                   tabelloverskrift={<FormattedMessage {...tabellOverskrift} />}
                                   tabellId={id + '_tabell'}/>;
    return (
        <div>
            <GrafSwitcher
                id={id}
                tabell={grafTabell}
                graf={<ReactHighcharts config={conf}/>}/>
        </div>
    );
};

export default injectIntl(LinjeGraf);

