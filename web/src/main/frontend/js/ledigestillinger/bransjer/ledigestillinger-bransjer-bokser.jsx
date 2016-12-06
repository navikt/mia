import React from 'react';
import {FormattedMessage} from 'react-intl';
import BransjeBoks from './ledigestillinger-bransjer-boks';
import {findTotaltAntallJobber} from './ledigestillinger-bransjer-util';

export const Bokser = (props) => {
    const yrkesgrupper = props.yrkesgrupper.slice().sort((a, b) => b.antallStillinger - a.antallStillinger);
    return (
        <div>
            <div className="blokk-s">
                <FormattedMessage {...props.meldinger.boksoverskrift} values={{antall: findTotaltAntallJobber(yrkesgrupper)}}/>
            </div>
            <div className="bokser-container blokk-s">
                { yrkesgrupper.map(row => <BransjeBoks {...row} onClick={props.onClick} erValgt={props.valgteyrkesgrupper.includes(`${row.bransjeid}`)} key={row.bransjeid} /> )}
            </div>
        </div>
    );
};

export default Bokser;
