import React from 'react';
import BransjeBoks from './ledigestillinger-bransjer-boks';

export const Bokser = (props) => {
    const yrkesgrupper = props.yrkesgrupper.slice().sort((a, b) => b.antallStillinger - a.antallStillinger);
    const erValgt = row => props.valgteyrkesgrupper != null && props.valgteyrkesgrupper.includes(`${row.id}`);

    return (
        <div className="bokser-container blokk-s">
            { yrkesgrupper.map(row => <BransjeBoks {...row} onClick={props.onClick} erValgt={erValgt(row)} key={row.id} checkbox={row.parent != null}/> )}
        </div>
    );
};

export default Bokser;
