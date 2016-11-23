import React from 'react';
import {FormattedMessage} from 'react-intl';
import bransjemock from './ledige-stillinger-bransjer-mockdata';
import BransjeBoks from './ledigestillinger-bransjer-boks';

export const Bokser = (props) => {
    return (
        <div>
            <div className="blokk-s">
                <FormattedMessage {...props.meldinger.boksoverskrift} values={{antall: props.findTotaltAntallJobber(bransjemock)}}/>
            </div>
            <div className="bokser-container blokk-s">
                { bransjemock.map(row => <BransjeBoks {...row} onClick={props.onClick} erValgt={props.valgteyrkesgrupper.includes(`${row.id}`)} key={row.id} /> )}
            </div>
        </div>
    );
};

export default Bokser;
