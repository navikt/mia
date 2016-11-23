import React from 'react';
import Devtools from './devtools';
import {connect} from 'react-redux';
import DocumentTitle from 'react-document-title';
import {defineMessages, injectIntl} from 'react-intl';

import Innholdslaster from "./felles/innholdslaster/innholdslaster";
import {lastTekster} from './felles/tekster/tekster-reducer';
import Hovedmeny from "./felles/hovedmeny/hovedmeny";

const meldinger = defineMessages({
    appTitle: {
        id: 'applikasjon.tittel',
        defaultMessage: 'Muligheter i arbeidsmarkedet'
    }
});

class Application extends React.Component {
    componentDidMount() {
        const visCmsKeys = this.props.location.query.vistekster === 'true';
        this.props.lastTekster(visCmsKeys);
    }
    render() {
        return (
            <DocumentTitle title={this.props.intl.formatMessage(meldinger.appTitle)}>
                <div>
                    <Innholdslaster avhengigheter={[this.props.tekster]}>
                        <Hovedmeny />
                        <div className="side-innhold">
                            { this.props.children }
                        </div>
                    </Innholdslaster>
                    <div aria-hidden="true">
                        <Devtools />
                    </div>
                </div>
            </DocumentTitle>
        );

    }
}

const stateToProps = state => ({
    tekster: state.tekster
});

const actionsToProps = {
    lastTekster
};

export default connect(stateToProps, actionsToProps)(injectIntl(Application));
