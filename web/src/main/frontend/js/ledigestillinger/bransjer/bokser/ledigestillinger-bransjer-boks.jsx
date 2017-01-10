import React from 'react';

const BransjeBoks= ({id, navn, antallStillinger, onClick, erValgt}) => {
    return(
        <button className={"bransje-boks-container " + (erValgt ? 'aktiv-bransjeboks' : '')} id={`bransje${id}`} onClick={() => onClick(`${id}`)}>
            <span className="boks-navn typo-element">{navn}</span>
            <span className="boks-antall">{antallStillinger}</span>
        </button>
    );
};

export default BransjeBoks;