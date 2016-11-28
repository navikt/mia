import React from 'react';
import {Link} from 'react-router';

export const Stilling = (props) => {
    return (
        <li className="row">
            <Link to={props.stilling.link}>
                <span className="col-sm-4">{props.stilling.stilling}</span><span className="col-sm-4">{props.stilling.arbeidsgiver}</span><span className="col-sm-4">{props.stilling.soknadsfrist}</span>
            </Link>
        </li>
    );
};

export default Stilling;
