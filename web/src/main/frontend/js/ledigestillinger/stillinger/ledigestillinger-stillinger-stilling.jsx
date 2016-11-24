import React from 'react';

export const Stilling = (props) => {
    return (
        <tr className="stillingsrad" id={`stilling${props.stilling.id}`} >
            <td className="stillingsrad-stilling">
                <a href={props.stilling.link}>
                    {props.stilling.stilling}
                </a>
            </td>
            <td className="stillingsrad-arbeidsgiver">
                <a href={props.stilling.link}>
                    {props.stilling.arbeidsgiver}
                </a>
            </td>
            <td className="stillingsrad-soknadsfrist">
                <a href={props.stilling.link}>
                    {props.stilling.soknadsfrist}
                </a>
            </td>
        </tr>
    );
};

export default Stilling;