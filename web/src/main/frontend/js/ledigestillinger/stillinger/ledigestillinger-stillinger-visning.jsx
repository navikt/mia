import React from 'react';
import Stilling from './ledigestillinger-stillinger-stilling';

export const Stillingsvisning = (props) => {
    const mockstillinger = [{id: 13, stilling: "Mekaniker", arbeidsgiver: "Rett Bemanning", soknadsfrist: "29.10.2016", link: "www.finn.no/jobb/14"},
        {id: 14, stilling: "Mekaniker2", arbeidsgiver: "Rett Bemanning2", soknadsfrist: "30.10.2016", link: "www.finn.no/jobb/14"}];

    return  (
        <div>
            <h1>{props.valgteyrkesgrupper}</h1>
            <div className="panel panel-fremhevet">
                <table className="tabell tabell-hover">
                    <thead>
                        <tr>
                            <th>Stilling</th>
                            <th>Arbeidsgiver</th>
                            <th>Søknadsfrist</th>
                        </tr>
                    </thead>
                    <tbody>
                        {mockstillinger.map(stilling => {
                            return <Stilling stilling={stilling} key={stilling.id}/>;
                        })}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default Stillingsvisning;
