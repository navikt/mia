import React from 'react';
import {FormattedMessage} from 'react-intl';

const Inputfelt = (props) => {
    return (
        <div className="nav-input blokk-s">
            <label htmlFor={props.labelFor} >
                <FormattedMessage {...props.label} />
            </label>
            <input type={props.type} className={props.className} id={props.labelFor} />
        </div>
    );
};

export default Inputfelt;
