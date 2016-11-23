import React from 'react';
import {FormattedMessage} from 'react-intl';

const Inputfelt = (props) => {
    return (
        <div className="nav-input blokk-s">
            <label htmlFor={props.id} >
                <FormattedMessage {...props.label} />
            </label>
            <input type={props.type} className={props.className} id={props.id} />
        </div>
    );
};

export default Inputfelt;
