import React, {Component} from 'react';
import {defineMessages, FormattedMessage, injectIntl} from 'react-intl';
import ReactHighcharts from 'react-highcharts';
import GrafTabell from './../../felles/graf/graf-tabell.jsx';
import {standardGrafConfig, konfigurerSerie, lagTidsserier, lagManeder} from './../../felles/graf/standard-graf-properties';
import tekster from './graf-tekster';
import {TabsPure} from "nav-frontend-tabs";
import {connect} from "react-redux";
import {settValg} from "../switcher/switcher-reducer";

const meldinger = defineMessages({
    visGraf: {
        id: 'grafswitcher.visgraf',
        defaultMessage: 'Vis som graf'
    },
    visTabell: {
        id: 'grafswitcher.vistabell',
        defaultMessage: 'Vis som tabell'
    },

});

export class LinjeGraf  extends Component {

    knapptrykket(indeks) {
        this.props.settValg('grafswitcher', indeks);
    }

    render() {

        const {id, tabell, tabellOverskrift, yTittel='', yEnhet='', periodetype="Kvartal", intl, switcher} = this.props;
        console.log(switcher);
        const valgtTab = switcher ? switcher : 0;

        if (tabell == null) {
            return null;
        }

        const serienavn = [
            intl.formatMessage(tekster.serieArbeidsledighet),
            intl.formatMessage(tekster.serieLedigeStillinger)
        ];
        const serienavnKort = [
            intl.formatMessage(tekster.serieArbeidsledighet),
            intl.formatMessage(tekster.serieLedigeStillinger)
        ];

        const aarMaaned = Object.keys(tabell["arbeidsledighet"]);
        const katBase = lagManeder(aarMaaned);

        const kategorier = lagTidsserier(katBase);

        const getValues = object => Object.keys(object).map(key => object[key]);

        const rawData = [
            getValues(tabell["arbeidsledighet"]),
            getValues(tabell["ledigestillinger"])
        ];
        const serier = rawData.map(konfigurerSerie(serienavn, serienavnKort, ["square", "circle"]));
        const conf = standardGrafConfig({kategorier, serier, yEnhet, yTittel});

        const grafTabell = <GrafTabell overskrifter={katBase}
                                       rader={rawData}
                                       periodetype={periodetype}
                                       serieNavn={serienavnKort}
                                       tabelloverskrift={<FormattedMessage {...tabellOverskrift} />}
                                       tabellId={id + '_tabell'}/>;

        const a = valgtTab === 1 ? grafTabell : <ReactHighcharts config={conf}/>;
        return (
            <div>
                <TabsPure
                    kompakt
                    className="grafvisning-tabs"
                    tabs={[
                        {"label": <FormattedMessage {...meldinger.visGraf}/>, 'aktiv': valgtTab === 0},
                        {"label": <FormattedMessage {...meldinger.visTabell} />, 'aktiv': valgtTab === 1}
                    ]}
                    onChange={(a, indeks) => {
                        this.knapptrykket(indeks)
                    }}
                />
                {a}
            </div>
        );
    }
}

export default connect((state) => ({
    switcher: state.ledigestillinger.statistikk.switcher['grafswitcher']
}), {
    settValg
})(injectIntl(LinjeGraf));



