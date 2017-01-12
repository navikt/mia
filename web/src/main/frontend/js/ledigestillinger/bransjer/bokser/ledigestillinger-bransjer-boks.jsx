import React from 'react';
import classNames from 'classnames';

const BransjeBoks= ({id, navn, antallStillinger, onClick, erValgt, checkbox}) => {

    const checked = checkbox ? erValgt : null;
    const role = checkbox ? "checkbox" : "button";

    return(
        <button
            className={classNames('bransje-boks-container', {'aktiv-bransjeboks': erValgt})}
            id={`bransje${id}`}
            onClick={() => onClick(id)}
            role={role}
            aria-checked={checked}
        >
            <span className="boks-navn typo-element">{navn}</span>
            <span className="boks-antall">{antallStillinger}</span>
        </button>
    );
};

export default BransjeBoks;