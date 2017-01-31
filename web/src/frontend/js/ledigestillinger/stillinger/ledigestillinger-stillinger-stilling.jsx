import React from 'react';
import {FormattedDate} from 'react-intl';
import {connect} from 'react-redux';

export const Stilling = (props) => {
    const getSoknadsfrist = datestring => {
        if(datestring == null) {
            return "-";
        }

        return <FormattedDate value={new Date(datestring)} format="numeric" />;
    };
    const baseUrl = props.miljovariabler["stillingsok.link.url"];
    const stillingUrl = `${baseUrl}stilling?ID=${props.stilling.id}`;

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

const stateToProps = state => ({
    miljovariabler: state.rest.miljovariabler.data
});

export default connect(stateToProps)(Stilling);