import React from 'react';
import Devtools from './devtools';
import {connect} from 'react-redux';
import DocumentTitle from 'react-document-title';
import {defineMessages, injectIntl} from 'react-intl';

import Innholdslaster from './felles/innholdslaster/innholdslaster';
import {lastTekster} from './felles/tekster/tekster-reducer';
import restActionCreator from './felles/rest/rest-action';
import Hovedmeny from './felles/hovedmeny/hovedmeny';
import Hodefot from './felles/hodefot/hodefot';

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
        this.props.lastOmrader();
    }
    render() {
        return (
            <DocumentTitle title={this.props.intl.formatMessage(meldinger.appTitle)}>
                <div>
                    <Innholdslaster avhengigheter={[this.props.tekster, this.props.omrader]}>
                        <Hodefot />
                        <div className="hovedinnhold side-midtstilt">
                            <Hovedmeny />
                            <div>
                                { this.props.children }
                            </div>
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
    tekster: state.tekster,
    omrader: state.rest.omrader
});

const actionsToProps = {
    lastTekster,
    lastOmrader: () => restActionCreator('omrader', '/omrader')
};

export default connect(stateToProps, actionsToProps)(injectIntl(Application));
