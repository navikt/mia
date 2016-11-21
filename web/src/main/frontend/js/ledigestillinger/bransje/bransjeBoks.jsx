import React from 'react';

const BransjeBoks= ({id, navn, antall, onClick, bransjevalg}) => {
    return(
        <button className={"bransjeBoksContainer " + (bransjevalg === `${id}` ? 'aktivBransjeBoks' : '')} id={`bransje${id}`} onClick={() => onClick(`${id}`)}>
            <div className="boksNavn typo-element">{navn}</div>
            <div className="boksAntall">{antall}</div>
        </button>
    );
};

export default BransjeBoks;