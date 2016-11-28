import React from 'react';

export const Stilling = (props) => {
    return (
            <li className="row">
                <a href={props.stilling.link}>
                <span className="col-lg-4 col-sm-4">{props.stilling.stilling}</span><span className="col-lg-4 col-sm-4">{props.stilling.arbeidsgiver}</span><span className="col-lg-4 col-sm-4">{props.stilling.soknadsfrist}</span>
                </a>
            </li>
    );
};

export default Stilling;
