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

    const stillingerFraProps = props.yrkesgruppe.stillinger.sort(compareSoknadsfrister);
    const stillinger = stillingerFraProps.length > 0 ?
        stillingerFraProps.map(stilling => { return <Stilling stilling={stilling} key={stilling.id}/>; }) :
        <li className="row"><FormattedMessage {...meldinger.ingenstillinger} /></li>;

    return  (
        <div className="blokk-m">
            <div className="panel panel-fremhevet">
                <h2 className="blokk-m typo-undertittel">{props.yrkesgruppe.navn} ({props.yrkesgruppe.antallStillinger})</h2>
                <div className="tabell-container">
                    <div className="row blokk-xs">
                        <h3 className="col-sm-6 typo-etikett-stor"><FormattedMessage {...meldinger.stilling} /></h3>
                        <h3 className="col-sm-4 typo-etikett-stor"><FormattedMessage {...meldinger.arbeidsgiver} /></h3>
                        <h3 className="col-sm-2 typo-etikett-stor"><FormattedMessage {...meldinger.soknadsfrist} /></h3>
                    </div>
                    <ul className="ustilet list-with-hover">
                        {stillinger}
                    </ul>
                </div>
            </div>
        </div>
    );
};

export default Stillingsvisning;
