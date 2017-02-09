import React from 'react';
import {getNavnForFylkeId, getNavnForKommuneId} from '../../ledigestillinger/oversikt/kart/kart-utils';
import {getNavnForYrkesgruppeId, getNavnForYrkesomradeId} from '../../ledigestillinger/bransjer/ledigestillinger-bransjer-util';
import {FormattedMessage} from 'react-intl';

export const ValgteFylker = props => (
    <p className={props.class}>
        <span className="typo-element valgte-omrader-tittel">
            <FormattedMessage {...props.tekst}/>
        </span>
        {props.valgteFylker.map(fylkeid => getNavnForFylkeId(fylkeid, props.omrader)).join(', ')}
    </p>
);

export const ValgteKommuner = props => (
    <p className={props.class}>
        <span className="typo-element valgte-omrader-tittel">
            <FormattedMessage {...props.tekst}/>
        </span>
        {props.valgteKommuner.map(kommuneid => getNavnForKommuneId(kommuneid, props.omrader)).join(', ')}
    </p>
);

export const ValgtStillingskategori = props => (
    <p>
        <span className="typo-element valgte-omrader-tittel">
            <FormattedMessage {...props.tekst}/>
        </span>
        {getNavnForYrkesomradeId(props.valgtYrkesomrade, props.yrkesomrader)}
    </p>
);

export const ValgteArbeidsomrader = props => {
    const valgteOmraderListe = props.valgteYrkesgrupper.map(yrkesgruppeid => getNavnForYrkesgruppeId(yrkesgruppeid, props.yrkesgrupper.data)).join(', ');
    return valgteOmraderListe.length === 0 ? <noscript /> :
        <p>
            <span className="typo-element valgte-omrader-tittel">
                <FormattedMessage {...props.tekst}/>
            </span>
            {valgteOmraderListe}
        </p>;
};

export const ValgtHeleNorge = props => (
    <p>
        <span className="typo-element valgte-omrader-tittel">
            <FormattedMessage {...props.valgtOmrade}/>
        </span>
        <FormattedMessage {...props.heleNorge}/>
    </p>
);