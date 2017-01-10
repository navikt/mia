import React from 'react';
import {FormattedDate} from "react-intl";

export const Stilling = (props) => {
    const getSoknadsfrist = datestring => {
        if(datestring == null) {
            return "-";
        }

        return <FormattedDate value={new Date(datestring)} format="numeric" />;
    };
    const stillingUrl = "https://tjenester-q1.nav.no/stillinger/stilling?ID="+props.stilling.id;

    return (
        <tr>
            <td>
                <a href={stillingUrl} target="_blank">{props.stilling.tittel}</a>
            </td>
            <td>
                <span>{props.stilling.arbeidsgivernavn}</span>
            </td>
            <td>
                <span>{getSoknadsfrist(props.stilling.soknadfrist)}</span>
            </td>
        </tr>
    );
};

export default Stilling;
