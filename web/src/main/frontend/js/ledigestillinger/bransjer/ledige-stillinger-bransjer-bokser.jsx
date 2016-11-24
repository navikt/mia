import React from 'react';
import {FormattedMessage} from 'react-intl';
import bransjemock from './ledige-stillinger-bransjer-mockdata';
import BransjeBoks from './ledigestillinger-bransjer-boks';

const yrker = bransjemock.slice().sort((a, b) => b.antall - a.antall);
export const Bokser = (props) => {
    return (
        <div>
            <div className="blokk-s">
                <FormattedMessage {...props.meldinger.boksoverskrift} values={{antall: props.findTotaltAntallJobber(yrker)}}/>
            </div>
            <div className="bokser-container blokk-s">
                { yrker.map(row => <BransjeBoks {...row} onClick={props.onClick} erValgt={props.valgteyrkesgrupper.includes(`${row.id}`)} key={row.id} /> )}
            </div>
        </div>
    );
};

export default Bokser;
