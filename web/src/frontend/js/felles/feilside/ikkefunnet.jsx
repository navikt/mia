import React from 'react';
import {defineMessages} from 'react-intl';
import Feilside from './feilside.jsx';

export const meldinger = defineMessages({
   tittel: {
       id: 'feilside.404.tittel',
       defaultMessage: 'Oops, noe gikk galt'
   },
    melding: {
       id: 'feilside.404.melding',
        defaultMessage: 'Beklager, men Mia finner ikke siden du prøver å aksessere.'
    }
});

export const IkkeFunnet = () => (
    <Feilside {...meldinger} />
);

export default IkkeFunnet;
