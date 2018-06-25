import React from 'react';

function Brodsmule(props) {
    const tekstEllerLink = props.path
        ? <a href={props.path} className="lenke">
            {`${props.tekst}`}
        </a>
        : props.tekst;

    return (
        <li className="brodsmuler__item typo-normal">
            {tekstEllerLink}
        </li>
    );
}

export default Brodsmule;
