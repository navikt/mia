import React from 'react';
import {getValgteKommunerForFylke, compareOmrader} from './ledigestillinger-oversikt-utils';
import KommuneTabell from './ledigestillinger-oversikt-kommunetabell';


export const OmradeTabell = ({ valgteFylker, valgteKommuner, omrader, stillinger }) => {
    const tabell = valgteFylker
        .sort(compareOmrader)
        .map(fylke => <KommuneTabell
            key={fylke.id}
            fylke={fylke}
            kommuner={getValgteKommunerForFylke(fylke.id, omrader, valgteKommuner)}
            stillinger={stillinger}
        />);

    return (
        <div>
            {tabell}
        </div>
    );
};

export default OmradeTabell;