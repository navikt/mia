import React from 'react';
import Innholdslaster from './../../../felles/innholdslaster/innholdslaster';
import Bokser from './ledigestillinger-bransjer-bokser';


export const BokserForYrkesomrader = props => (
    <div>
        <Bokser onClick={id => props.onClick(id)} yrkesgrupper={props.yrkesomrader} testid="stillingskategori-bokser"/>
    </div>
);

export const BokserForYrkesgrupper = ({ yrkesgrupper, onClick, valgteyrkesgrupper}) => (
    <Innholdslaster avhengigheter={[yrkesgrupper]}>
        <Bokser onClick={id => onClick(id)} yrkesgrupper={yrkesgrupper.data}
                valgteyrkesgrupper={valgteyrkesgrupper} testid="arbeidsomrader-bokser"/>
    </Innholdslaster>
);
