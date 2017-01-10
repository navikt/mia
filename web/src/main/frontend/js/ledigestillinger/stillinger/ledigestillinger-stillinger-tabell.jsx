import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import Stilling from './ledigestillinger-stillinger-stilling';

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
    tabellcaption: {
        id: 'ledigestillinger.stillinger.tabell.caption',
        defaultMessage: 'Tabell'
    }
});

export const StillingTabell = ({ stillinger }) => {
    const rader = stillinger.map(stilling => <Stilling stilling={stilling} key={stilling.id}/>);

    return (
        <table className="tabell tabell-container">
            <caption><FormattedMessage {...meldinger.tabellcaption} /></caption>
            <thead>
                <tr>
                    <th scope="col" className="typo-etikett-stor"><FormattedMessage {...meldinger.stilling} /></th>
                    <th scope="col" className="stillinger-arbeidsgiver typo-etikett-stor"><FormattedMessage {...meldinger.arbeidsgiver} /></th>
                    <th scope="col" className="stillinger-soknadsfrist typo-etikett-stor"><FormattedMessage {...meldinger.soknadsfrist} /></th>
                </tr>
            </thead>
            <tbody>
                {rader}
            </tbody>
        </table>
    );
};

export default StillingTabell;
