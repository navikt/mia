import React from 'react';
import {Link} from 'react-router';
import {FormattedDate} from "react-intl";

export const Stilling = (props) => {
    const getSoknadsfrist = datestring => {
        if(datestring == null) {
            return "-";
        }
        return <FormattedDate value={new Date(datestring)}/>;
    };

    return (
        <li className="row">
            <Link to="#">
                <span className="col-sm-4">{props.stilling.stillingstype}</span>
                <span className="col-sm-4">{props.stilling.arbeidsgivernavn}</span>
                <span className="col-sm-4">{getSoknadsfrist(props.stilling.soknadfrist)}</span>
            </Link>
        </li>
    );
};

export default Stilling;
