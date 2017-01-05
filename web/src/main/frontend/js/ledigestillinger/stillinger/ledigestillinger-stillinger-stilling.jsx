import React from 'react';
import {FormattedDate} from "react-intl";

export const Stilling = (props) => {
    const getSoknadsfrist = datestring => {
        if(datestring == null) {
            return "-";
        }
        return <FormattedDate value={new Date(datestring)}/>;
    };
    const stillingUrl = "https://tjenester-q1.nav.no/stillinger/stilling?ID="+props.stilling.id;

    return (
        <li className="row">
            <a href={stillingUrl} target="_blank">
                <span className="col-sm-4">{props.stilling.tittel}</span>
                <span className="col-sm-4">{props.stilling.arbeidsgivernavn}</span>
                <span className="col-sm-4">{getSoknadsfrist(props.stilling.soknadfrist)}</span>
            </a>
        </li>
    );
};

export default Stilling;
