import React from "react";
import {Element, Normaltekst, Undertittel} from "nav-frontend-typografi";
import {HoyreChevron} from 'nav-frontend-chevron';
import {ReactComponent as FlytteLogo} from './flytting.svg'
import {ReactComponent as JobbEULogo} from './JobbeEU.svg';
import {defineMessages, FormattedMessage, injectIntl} from "react-intl";

const meldinger = defineMessages({
    flytteHeader: {
        id: 'linker.flytteheader',
        defaultMessage: 'Støtte til reise og flytting'
    },
    flytteTekst: {
        id: 'linker.flytteTekst',
        defaultMessage: 'Som arbeidsøker kan du få støtte til\n å dekke utgifter for å komme i arbeid'
    },
    lesMuligheter: {
        id: 'lenker.lesMuligheter',
        defaultMessage: 'Les mer om mulighetene'
    },
    euHeader: {
        id: 'linker.flytteheader',
        defaultMessage: 'Jobbe i EU/EØS og Sveits?'
    },
    euTekst: {
        id: 'linker.flytteTekst',
        defaultMessage: 'hvis du vil søke jobb i EU/EØS-landende og Sveits,\n kan NAV hjelpe deg gjennom EURES-samarbeidet.'
    },
});

const Lenker = (intl) => (
    <div className='lenkeWraper'>
        <a className='lenkeboks'
           href='https://tjenester.nav.no/veiledearbeidssoker/mistet-jobben/okonomi-annenstotte?sprak=nb'>
            <Undertittel className='header'>
                <FormattedMessage {...meldinger.flytteHeader} />
            </Undertittel>
            <Normaltekst>
                <FormattedMessage {...meldinger.flytteTekst}/>
            </Normaltekst>
            <div className='lenkestylBoks'>
                <Element className='lenkesyle'>
                    <FormattedMessage {...meldinger.lesMuligheter}/>
                </Element>
                <HoyreChevron className='indikator'/>
            </div>
            <FlytteLogo className='logo'/>
        </a>
        <a className='lenkeboks'
           href='https://www.nav.no/no/Person/Flere+tema/Arbeid+og+opphold+i+utlandet/nav-hjelper-arbeidssøkere-med-å-finne-jobb-i-eu-eøs-og-sveits'>
            <Undertittel className='header'>
                <FormattedMessage {...meldinger.euHeader} />
            </Undertittel>
            <Normaltekst>
                <FormattedMessage {...meldinger.euTekst}/>
            </Normaltekst>
            <div className='lenkestylBoks'>
                <Element className='lenkesyle'>
                    <FormattedMessage {...meldinger.lesMuligheter}/>
                </Element>
                <HoyreChevron className='indikator'/>
            </div>
            <JobbEULogo className='logo'/>
        </a>
    </div>
);


export default injectIntl(Lenker)
