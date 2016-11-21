import React from 'react';

const BransjeBoks= ({id, navn, antall, onClick, bransjevalg}) => {
    return(
        <button className={"bransje-boks-container " + (bransjevalg === `${id}` ? 'aktiv-bransjeboks' : '')} id={`bransje${id}`} onClick={() => onClick(`${id}`)}>
            <div className="boks-navn typo-element">{navn}</div>
            <div className="boks-antall">{antall}</div>
        </button>
    );
};

export default BransjeBoks;