import React from 'react';
import Devtools from './devtools';
import DocumentTitle from 'react-document-title';
import {defineMessages, injectIntl} from 'react-intl';

import Hovedmeny from "./felles/hovedmeny/hovedmeny";

const meldinger = defineMessages({
    appTitle: {
        id: 'applikasjon.tittel',
        defaultMessage: 'Muligheter i arbeidsmarkedet'
    }
});

class Application extends React.Component {
    render() {
        return (
            <DocumentTitle title={this.props.intl.formatMessage(meldinger.appTitle)}>
                <div>
                    <Hovedmeny />
                    <div className="side-innhold">
                        { this.props.children }
                    </div>
                    <div aria-hidden="true">
                        <Devtools />
                    </div>
                </div>
            </DocumentTitle>
        );

    }
}

export default injectIntl(Application);
