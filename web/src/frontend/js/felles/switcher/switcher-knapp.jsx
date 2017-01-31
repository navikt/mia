import React from 'react';
import classnames from 'classnames';

const SwitcherKnapp = ({ id, aktiv, onClick, tekst }) => {
    return(
        <button
            role="tab"
            aria-selected={aktiv ? 'true' : 'false'}
            aria-controls={id}
            id={id + '-knapp'}
            className={classnames('knapp-switcher', { 'er-aktiv': aktiv })}
            onClick={onClick}>
            {tekst}
        </button>
    );
};

export default SwitcherKnapp;
