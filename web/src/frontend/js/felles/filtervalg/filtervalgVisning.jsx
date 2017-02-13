import React from 'react';
import {getNavnForFylkeId, getNavnForKommuneId} from '../../ledigestillinger/oversikt/kart/kart-utils';
import {getNavnForYrkesgruppeId, getNavnForYrkesomradeId} from '../../ledigestillinger/bransjer/ledigestillinger-bransjer-util';
import {FormattedMessage} from 'react-intl';

export const ValgteFylker = props => {
    return props.valgteFylker.length !== 0 ?
        <p className={props.class}>
            <span className="typo-element valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            {props.valgteFylker.map(fylkeid => getNavnForFylkeId(fylkeid, props.omrader)).join(', ')}
        </p> :
        <noscript />;
};

export const ValgteKommuner = props => {
    return props.valgteKommuner.length !== 0 ?
        <p className={props.class}>
            <span className="typo-element valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            {props.valgteKommuner.map(kommuneid => getNavnForKommuneId(kommuneid, props.omrader)).join(', ')}
        </p> :
        <noscript />;
};

export const ValgtStillingskategori = props => {
    return props.yrkesomrader.length !== 0 ?
        <p>
            <span className="typo-element valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            {getNavnForYrkesomradeId(props.valgtYrkesomrade, props.yrkesomrader)}
        </p> :
        <noscript />;
};

export const ValgteArbeidsomrader = props => {
    return props.valgteYrkesgrupper.length !== 0 ?
        <p>
            <span className="typo-element valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            {props.valgteYrkesgrupper.map(yrkesgruppeid => getNavnForYrkesgruppeId(yrkesgruppeid, props.yrkesgrupper.data)).join(', ')}
        </p> :
        <noscript />;
};

export const ValgtHeleNorge = props => (
    <p className={props.class}>
        <span className="typo-element valgte-omrader-tittel">
            <FormattedMessage {...props.valgtOmrade}/>
        </span>
        <FormattedMessage {...props.heleNorge}/>
    </p>
);