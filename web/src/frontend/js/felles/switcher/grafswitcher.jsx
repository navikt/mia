import React from 'react';
import { defineMessages, FormattedMessage } from 'react-intl';
import Switcher from './switcher.jsx';

const meldinger = defineMessages({
    visGraf: {
        id: 'grafswitcher.visgraf',
        defaultMessage: 'Vis som graf'
    },
    visTabell: {
        id: 'grafswitcher.vistabell',
        defaultMessage: 'Vis som tabell'
    }
});

const GrafSwitcher = ({ id, tabell, graf }) => {
    return (
        <Switcher
            id={id}
            elementer={[
                {
                    tekst: <FormattedMessage {...meldinger.visGraf} />,
                    element: (
                        <div className="switcher-graf">
                            {graf}
                        </div>
                    )
                },
                {
                    tekst: <FormattedMessage {...meldinger.visTabell} />,
                    element: tabell
                }
            ]}
        />
    );
};

export default GrafSwitcher;