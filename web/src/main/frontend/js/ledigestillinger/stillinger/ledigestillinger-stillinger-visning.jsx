import React from 'react';
import Stilling from './ledigestillinger-stillinger-stilling';

export const Stillingsvisning = (props) => {
    const mockstillinger = [{id: 13, stilling: "Mekaniker", arbeidsgiver: "Rett Bemanning", soknadsfrist: "29.10.2016", link: "www.finn.no/jobb/14"},
        {id: 14, stilling: "Mekaniker2", arbeidsgiver: "Rett Bemanning2", soknadsfrist: "30.10.2016", link: "www.finn.no/jobb/14"}];

    return  (
        <div>
            <h1>{props.valgteyrkesgrupper}</h1>
            <div className="panel panel-fremhevet">
                <div className="row">
                    <span className="col-lg-4 col-sm-4 typo-innholdstittel">Stilling</span>
                    <span className="col-lg-4 col-sm-4 typo-innholdstittel">Arbeidsgiver</span>
                    <span className="col-lg-4 col-sm-4 typo-innholdstittel">Søknadsfrist</span>
                </div>
                <ul className="ustilet">
                    {mockstillinger.length > 0 ? mockstillinger.map(stilling => {
                        return <Stilling stilling={stilling} key={stilling.id}/>;
                    }) : <li className="row">Ingen ledige stillinger</li>}
                </ul>
            </div>
        </div>
    );
};

export default Stillingsvisning;
