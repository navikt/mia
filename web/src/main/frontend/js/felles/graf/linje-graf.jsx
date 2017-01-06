import React from 'react';
import {connect} from 'react-redux';
import {FormattedMessage, injectIntl} from 'react-intl';
import ReactHighcharts from 'react-highcharts';
import GrafTabell from './../../felles/graf/graf-tabell.jsx';
import {standardGrafConfig, konfigurerSerie, lagTidsserier} from './../../felles/graf/standard-graf-properties';
import GrafSwitcher from './../../felles/switcher/grafswitcher.jsx';
import tekster from './graf-tekster';

export const LinjeGraf = ({id, valgteFylker, serieData, serie, tabellOverskrift, yTittel='', yEnhet='', periodetype="Kvartal", intl: { formatMessage }}) => {
    if (!serieData || !serieData.rader) {
        return <noscript/>;
    }

    const fylker = valgteFylker.toString();

    const serienavn = [
        formatMessage(tekster.serieHeleLandet),
        formatMessage(tekster.serieValgtOmradeLang, { omrade: fylker })
    ];
    const serienavnKort = [
        formatMessage(tekster.serieHeleLandet),
        formatMessage(tekster.serieValgtOmrade)
    ];
    const header = serieData.rader[0];
    const katBase = serie(header);
    const kategorier = lagTidsserier(katBase);
    const data = serieData.rader.slice(1, 1 + serienavn.length);
    const rawData = data.map((rad)=> rad.kolonner.map((kolonne)=> kolonne.data ? Number(kolonne.data) : kolonne.data));
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

export default connect((state) => ({
    valgteFylker: state.ledigestillinger.oversikt.valgteFylker
}))(injectIntl(LinjeGraf));

