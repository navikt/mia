import React from 'react';
import Stilling from './ledigestillinger-stillinger-stilling';
import {defineMessages, FormattedMessage} from 'react-intl';

export const meldinger = defineMessages({
    stilling: {
        id: 'ledigestillinger.stillinger.stilling',
        defaultMessage: 'Stilling'
    },
    arbeidsgiver: {
        id: 'ledigestillinger.stillinger.arbeidsgiver',
        defaultMessage: 'Arbeidsgiver'
    },
    soknadsfrist: {
        id: 'ledigestillinger.stillinger.soknadsfrist',
        defaultMessage: 'Søknadsfrist'
    },
    ingenstillinger: {
        id: 'ledigestillinger.stillinger.ingenstillinger',
        defaultMessage: 'Ingen ledige stillinger'
    }
});


export const Stillingsvisning = props => {
    const compareSoknadsfrister = (a, b) => {
        if(a.soknadfrist == null) {
            return 1;
        }
        if(b.soknadfrist == null) {
            return -1;
        }
        return new Date(a.soknadfrist) < new Date(b.soknadfrist);
    };

    const stillinger = props.yrkesgruppe.stillinger.sort(compareSoknadsfrister);

    return  (
        <div className="blokk-m">
            <div className="panel panel-fremhevet">
                <h2 className="blokk-m">{props.yrkesgruppe.navn} ({props.yrkesgruppe.antallStillinger})</h2>
                <div className="row blokk-xs">
                    <h3 className="col-sm-4"><FormattedMessage {...meldinger.stilling} /></h3>
                    <h3 className="col-sm-4"><FormattedMessage {...meldinger.arbeidsgiver} /></h3>
                    <h3 className="col-sm-4"><FormattedMessage {...meldinger.soknadsfrist} /></h3>
                </div>
                <ul className="ustilet list-with-hover">
                    {stillinger.length > 0 ? stillinger.map(stilling => {
                        return <Stilling stilling={stilling} key={stilling.id}/>;
                    }) : <li className="row"><FormattedMessage {...meldinger.ingenstillinger} /></li>}
                </ul>
            </div>
        </div>
    );
};

export default Stillingsvisning;
