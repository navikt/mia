import React from 'react';
import Stilling from './ledigestillinger-stillinger-stilling';
import {defineMessages, FormattedMessage} from 'react-intl';

export const meldinger = defineMessages({
    stilling: {
        id: 'ledigestillinger.stillinger.stilling',
        defaultMessage: 'Stilling'
    },
    arbeidsgiver: {
        id: 'ledigestillinger.stillinger.arbeidsgiver',
        defaultMessage: 'Arbeidsgiver'
    },
    soknadsfrist: {
        id: 'ledigestillinger.stillinger.soknadsfrist',
        defaultMessage: 'Søknadsfrist'
    },
    ingenstillinger: {
        id: 'ledigestillinger.stillinger.ingenstillinger',
        defaultMessage: 'Ingen ledige stillinger'
    }
});


export const Stillingsvisning = (props) => {
    const mockstillinger = [{id: 13, stilling: "Mekaniker", arbeidsgiver: "Rett Bemanning", soknadsfrist: "29.10.2016", link: "www.finn.no/jobb/14"},
        {id: 14, stilling: "Mekaniker2", arbeidsgiver: "Rett Bemanning2", soknadsfrist: "30.10.2016", link: "www.finn.no/jobb/14"}];

    return  (
        <div>
            <h1>{props.valgteyrkesgrupper}</h1>
            <div className="panel panel-fremhevet">
                <div className="row">
                    <span className="col-sm-4 typo-etikett-stor"><FormattedMessage {...meldinger.stilling} /></span>
                    <span className="col-sm-4 typo-etikett-stor"><FormattedMessage {...meldinger.arbeidsgiver} /></span>
                    <span className="col-sm-4 typo-etikett-stor"><FormattedMessage {...meldinger.soknadsfrist} /></span>
                </div>
                <ul className="ustilet list-with-hover">
                    {mockstillinger.length > 0 ? mockstillinger.map(stilling => {
                        return <Stilling stilling={stilling} key={stilling.id}/>;
                    }) : <li className="row"><FormattedMessage {...meldinger.ingenstillinger} /></li>}
                </ul>
            </div>
        </div>
    );
};

export default Stillingsvisning;
