import React from 'react';
import {defineMessages, FormattedMessage} from 'react-intl';
import Innholdslaster from './../../../felles/innholdslaster/innholdslaster';
import Bokser from './ledigestillinger-bransjer-bokser';

const meldinger = defineMessages({
    boksoverskrift_stillingskategori: {
        id: 'ledigestillinger.bransjer.boksoverskrift.stillingskategori',
        defaultMessage: 'Ledige stillinger fordelt på stillingskategorier'
    },
    boksoverskrift_arbeidsomrader: {
        id: 'ledigestillinger.bransjer.boksoverskrift.arbeidsomrade',
        defaultMessage: 'Ledige stillinger fordelt på arbeidsområder'
    }
});

export const BokserForYrkesomrader = props => (
    <div>
        <div className="blokk-s">
            <FormattedMessage {...meldinger.boksoverskrift_stillingskategori} />
        </div>
        <Bokser onClick={id => props.onClick(id)} yrkesgrupper={props.yrkesomrader}/>
    </div>
);

export const BokserForYrkesgrupper = ({ yrkesgrupper, onClick, valgteyrkesgrupper}) => (
    <Innholdslaster avhengigheter={[yrkesgrupper]}>
        <div className="blokk-s">
            <FormattedMessage {...meldinger.boksoverskrift_arbeidsomrader} />
        </div>
        <Bokser onClick={id => onClick(id)} yrkesgrupper={yrkesgrupper.data}
                valgteyrkesgrupper={valgteyrkesgrupper}/>
    </Innholdslaster>
);