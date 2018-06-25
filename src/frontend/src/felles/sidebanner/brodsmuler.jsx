import React from 'react';
import personSvg from './person.svg';
import Brodsmule from './brodsmule';

const DITTNAVN_PATH = '/dittnav/';
const VEIENTILARBEID_PATH = '/veientilarbeid/';

function Brodsmuler() {
    return (
        <div className="brodsmuler">
            <img
                src={personSvg}
                alt="person-illustrasjon"
                className="brodsmuler__illustrasjon"
            />
            <ol className="brodsmuler__list">
                <Brodsmule tekst="Ditt NAV" path={DITTNAVN_PATH} />
                <Brodsmule
                    tekst="Veien til arbeid"
                    path={VEIENTILARBEID_PATH}
                />
                <Brodsmule tekst="Muligheter i arbeidsmarkedet" />
            </ol>
        </div>
    );
}

export default Brodsmuler;