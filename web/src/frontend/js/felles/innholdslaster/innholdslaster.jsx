import React from "react";
import {STATUS} from "../../felles/konstanter";
import Spinner from "./innholdslaster-spinner";
import {FormattedMessage, defineMessages} from 'react-intl';

const harStatus = status => element => element.status === status;
const erAlleLastet = avhengigheter => avhengigheter && avhengigheter.every(harStatus(STATUS.lastet));
const erInitialisert = avhengigheter => avhengigheter && avhengigheter.every(harStatus(STATUS.initialisert));
const harFeilet = avhengigheter => avhengigheter && avhengigheter.some(harStatus(STATUS.feilet));

const meldinger = defineMessages({
    tittel: {
        id: 'feilmeldingspanel.tittel',
        defaultMessage: 'Oops... noe gikk galt'
    },
    tekst: {
        id: 'feilmeldingspanel.tekst',
        defaultMessage: 'Noe gikk galt ved henting av data fra våre baksystem. Vi kan derfor ikke vise innholdet for dette elementet. Du kan prøve å oppdatere siden for å laste inn dataene på nytt.'
    },
    feilkode: {
        id: 'feilmeldingspanel.feilkode',
        defaultMessage: 'Feilkode: {feilkode}'
    }
});

const Innholdslaster = ({avhengigheter, children, spinnerForInitialisert = true, feilmelding=meldinger}) => {
    if((!spinnerForInitialisert) && erInitialisert(avhengigheter)) {
        return <div>{children}</div>;
    }
    if(erAlleLastet(avhengigheter)) {
        return <div>{children}</div>;
    }
    if(harFeilet(avhengigheter)) {
        const feil = avhengigheter.filter(harStatus(STATUS.feilet)).find(a => a.data != null);
        const callIdMelding = (feil == null) ? null : <p><FormattedMessage {...meldinger.feilkode} values={{feilkode: feil.data}}/></p>;

        return (
            <div className="panel panel-ramme">
                <h3 className="hode hode-dekorert hode-advarsel hode-undertittel">
                    <FormattedMessage {...feilmelding.tittel} />
                </h3>
                <p>
                    <FormattedMessage {...feilmelding.tekst} />
                </p>
                {callIdMelding}
            </div>
        );
    }
    return <Spinner />;
};

export default Innholdslaster;
