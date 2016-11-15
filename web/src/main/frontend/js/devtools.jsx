import React from 'react';
import {createDevTools} from 'redux-devtools';
import LogMonitor from 'redux-devtools-log-monitor';
import DockMonitor from 'redux-devtools-dock-monitor';
import {erDev} from './felles/utils/dev';


let DevTools;
if (erDev()) {
    DevTools = createDevTools(
        <DockMonitor
            toggleVisibilityKey='ctrl-y'
            changePositionKey='ctrl-q'
            fluid={false}
            defaultSize={300}
            defaultIsVisible={false}>
            <LogMonitor theme="nicinabox" />
        </DockMonitor>
    );

    /* eslint-disable no-console */
    console.log("Kjører i dev-modus, trykk ctrl+y for å åpne DevTools");
    /* eslint-enable no-console */

} else {
    DevTools = () => {
        return <div style={{display: 'none'}} />;
    };
}

export default DevTools;