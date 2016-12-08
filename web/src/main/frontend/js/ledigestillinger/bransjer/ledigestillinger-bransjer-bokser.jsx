import React from 'react';
import {FormattedMessage, defineMessages} from 'react-intl';
import BransjeBoks from './ledigestillinger-bransjer-boks';
import {findTotaltAntallJobber} from './ledigestillinger-bransjer-util';

const meldinger = defineMessages({
    boksoverskrift: {
        id: 'ledigestillinger.bransjer.boksoverskrift',
        defaultMessage: 'Ledige jobber totalt ({antall}) fordelt på bransjer'
    }
});

export const Bokser = (props) => {
    const yrkesgrupper = props.yrkesgrupper.slice().sort((a, b) => b.antallStillinger - a.antallStillinger);
    const erValgt = row => props.valgteyrkesgrupper != null && props.valgteyrkesgrupper.includes(`${row.id}`);

    return (
        <div>
            <div className="blokk-s">
                <FormattedMessage {...meldinger.boksoverskrift} values={{antall: findTotaltAntallJobber(yrkesgrupper)}}/>
            </div>
            <div className="bokser-container blokk-s">
                { yrkesgrupper.map(row => <BransjeBoks {...row} onClick={props.onClick} erValgt={erValgt(row)} key={row.id} /> )}
            </div>
        </div>
    );
};

export default Bokser;
