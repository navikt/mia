import React from 'react';
import {getNavnForFylkeId, getNavnForKommuneId} from '../../ledigestillinger/oversikt/kart/kart-utils';
import {getNavnForYrkesgruppeId, getNavnForYrkesomradeId} from '../../ledigestillinger/bransjer/ledigestillinger-bransjer-util';
import {FormattedMessage, defineMessages} from 'react-intl';
import {Normaltekst} from "nav-frontend-typografi";

const meldinger = defineMessages({
    valgtOmrade: {
        id: 'ledigestillinger.valgtomrade',
        defaultMessage: '{antall, plural, one {Valgt område} other {Valgte områder}}: '
    },
    heleNorge: {
        id: 'ledigestillinger.helenorge',
        defaultMessage: 'Hele Norge, EØS, resten av verden'
    }
});

export const ValgteFylker = props => {
    return props.valgteFylker.length !== 0 ?
        <Normaltekst className={props.className}>
            <span className="valgte-omrader-tittel">
                <FormattedMessage {...meldinger.valgtOmrade} values={{antall: props.valgteFylker.length}}/>
            </span>
            <span className="valgte-omrader-liste">
            {props.valgteFylker.map(fylkeid => getNavnForFylkeId(fylkeid, props.omrader)).join(', ')}
            </span>
        </Normaltekst> :
        <noscript />;
};

export const ValgteKommuner = props => {
    return props.valgteKommuner.length !== 0 ?
        <Normaltekst className={props.className}>
            <span className="valgte-omrader-tittel">
                <FormattedMessage {...meldinger.valgtOmrade} values={{antall: props.valgteKommuner.length}}/>
            </span>
            <span className="valgte-omrader-liste">
            {props.valgteKommuner.map(kommuneid => getNavnForKommuneId(kommuneid, props.omrader)).join(', ')}
            </span>
        </Normaltekst> :
        <noscript />;
};

export const ValgtStillingskategori = props => {
    return props.yrkesomrader.length !== 0 ?
        <Normaltekst>
            <span className="valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            <span className="valgte-omrader-liste">
                {getNavnForYrkesomradeId(props.valgtYrkesomrade, props.yrkesomrader)}
            </span>
        </Normaltekst> :
        <noscript />;
};

export const ValgteArbeidsomrader = props => {
    return props.valgteYrkesgrupper.length !== 0 ?
        <Normaltekst>
            <span className="valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            <span  className="valgte-omrader-liste">
            {props.valgteYrkesgrupper.map(yrkesgruppeid => getNavnForYrkesgruppeId(yrkesgruppeid, props.yrkesgrupper.data)).join(', ')}
            </span>
        </Normaltekst> :
        <noscript />;
};

export const ValgtHeleNorge = props => (
    <Normaltekst className={props.className}>
        <span className="valgte-omrader-tittel">
            <FormattedMessage {...meldinger.valgtOmrade} values={{antall: 3}}/>
        </span>
        <span className="valgte-omrader-liste">
            <FormattedMessage {...meldinger.heleNorge}/>
        </span>
    </Normaltekst>
);
