import React from 'react';
import {Link} from 'react-router';
import {FormattedMessage, injectIntl} from 'react-intl';

import meldinger from "./hovedmeny-tekster";

const Menylenke = ({tekst, path}) => (
    <li className="ustilet">
        <Link to={path} activeClassName="active"><FormattedMessage {...tekst} /></Link>
    </li>
);

const Hovedmeny = () => (
    <nav className="hovedmeny blokk-m">
        <ul className="liste liste-ustilet liste-vannrett">
            <Menylenke tekst={meldinger.ledigestillinger} path="ledigestillinger" />
            <Menylenke tekst={meldinger.arbeidsgivere} path="arbeidsgivere" />
            <Menylenke tekst={meldinger.yrker} path="yrker" />
            <Menylenke tekst={meldinger.rapporter} path="rapporter" />
        </ul>
    </nav>
);

export default injectIntl(Hovedmeny);
