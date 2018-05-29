import React from 'react';
import {connect} from 'react-redux';
import DocumentTitle from 'react-document-title';
import {defineMessages, injectIntl} from 'react-intl';
import Innholdslaster from './felles/innholdslaster/innholdslaster';
import {lastTekster} from './felles/tekster/tekster-reducer';
import restActionCreator from './felles/rest/rest-action';
import LedigeStillinger from './ledigestillinger/ledigestillinger';
import Feilmodal from './feilmodal/feilmodal';

const meldinger = defineMessages({
    appTitle: {
        id: 'applikasjon.tittel',
        defaultMessage: 'Muligheter i arbeidsmarkedet'
    }
});


class Application extends React.Component {
    componentDidMount() {
        this.props.lastTekster();
        this.props.lastOmrader();
        this.props.lastFylkerGeojson();
        this.props.lastKommunerGeojson();
        this.props.lastMiljovariabler();
    }
    render() {
        const avhengigheter = [
            this.props.tekster,
            this.props.omrader,
            this.props.miljovariabler,
            this.props.fylkergeojson,
            this.props.kommunergeojson
        ];

        return (
            <DocumentTitle title={this.props.intl.formatMessage(meldinger.appTitle)}>
                <div>
                    <Innholdslaster avhengigheter={avhengigheter}>
                        <div className="hovedinnhold side-midtstilt">
                            <LedigeStillinger />
                        </div>
                    </Innholdslaster>
                    <Feilmodal />
                </div>
            </DocumentTitle>
        );
    }
}

const stateToProps = state => ({
    tekster: state.tekster,
    omrader: state.rest.omrader,
    miljovariabler: state.rest.miljovariabler,
    fylkergeojson: state.rest.fylkergeojson,
    kommunergeojson: state.rest.kommunergeojson
});

const actionsToProps = {
    lastTekster,
    lastOmrader: () => restActionCreator('omrader', '/omrader'),
    lastMiljovariabler: () => restActionCreator('miljovariabler', '/miljovariabler'),
    lastFylkerGeojson: () => restActionCreator('fylkergeojson', '/../geojson/fylker.json'),
    lastKommunerGeojson: () => restActionCreator('kommunergeojson', '/../geojson/kommuner.json')
};

export default connect(stateToProps, actionsToProps)(injectIntl(Application));
