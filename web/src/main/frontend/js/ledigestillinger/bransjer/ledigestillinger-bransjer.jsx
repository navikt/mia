import React from 'react';
import {connect} from 'react-redux';
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';
import bransjemock from '../../mock/mockdata';
import BransjeBoks from './ledigestillinger-bransjer-boks';
import {actions} from "./ledigestillinger-bransjer-reducer";

const bransjer = bransjemock;
export const meldinger = defineMessages({
    soketekst: {
        id: 'ledigestillinger.bransjer.soketekst',
        defaultMessage: 'Søk direkte eller klikk videre i kategoriene'
    },
    velgbransje: {
        id: 'ledigestillinger.bransjer.velgbransje',
        defaultMessage: 'Velg bransje'
    },
    boksoverskrift: {
        id: 'ledigestillinger.bransjer.boksoverskrift',
        defaultMessage: 'Ledige jobber totalt ({antall}) fordelt på bransjer'
    },
    lenkeallebransjer: {
        id: 'ledigestillinger.bransjer.lenkeallebransjer',
        defaultMessage: 'Vis alle bransjer med ledige stillinger'
    }
});

function findTotaltAntallJobber(data) {
    return data.reduce(function(a, b) {return a + b.antall;}, 0);
}

const Bransjer = (props) => {
    const velgBransje = (bransjeId) => {
        props.dispatch({type: actions.bransjevalg, payload: bransjeId !== props.bransjevalg ? bransjeId : 'alle'});
    };

    return (
        <div className="panel">
            <div className="nav-input blokk-s">
                <label htmlFor="input-sok">
                    <FormattedMessage {...meldinger.soketekst} />
                </label>
                <input type="search" className="input-fullbredde" id="input-sok"/>
            </div>
            <div className="bransjevalg blokk-s">
                <label htmlFor="select-bransje">
                    <FormattedMessage {...meldinger.velgbransje} />
                </label>
                <div className="select-container input-fullbredde">
                    <select id="select-bransje" value={props.bransjevalg}
                            onChange={e => velgBransje(e.target.value)}>
                        <option value="alle">Alle ({findTotaltAntallJobber(bransjer)})</option>
                        { bransjer.map(row => {
                            return <option value={row.id} key={row.id}>{row.navn} ({row.antall})</option>;
                        })}
                    </select>
                </div>
            </div>
            <div className="boksOverskrift blokk-s">
                <FormattedMessage {...meldinger.boksoverskrift} values={{antall: findTotaltAntallJobber(bransjer)}}/>
            </div>
            <div className="bokserContainer blokk-s">
                    { bransjer.map(row => {
                        return <BransjeBoks {...row} onClick={velgBransje} bransjevalg={props.bransjevalg} key={row.id} />;
                    })}
            </div>
            <a href="#">
                <FormattedMessage {...meldinger.lenkeallebransjer} /> >>
            </a>
        </div>
    );
};

const stateToProps = state => ({
   bransjevalg: state.ledigestillinger.bransje.bransjevalg
});

export default connect(stateToProps)(injectIntl(Bransjer));