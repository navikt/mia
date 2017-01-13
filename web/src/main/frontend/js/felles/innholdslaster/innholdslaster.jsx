import React from "react";
import {STATUS} from "../../felles/konstanter";
import Spinner from "./innholdslaster-spinner";

const harStatus = status => element => element.status === status;
const erAlleLastet = avhengigheter => avhengigheter && avhengigheter.every(harStatus(STATUS.lastet));
const erInitialisert = avhengigheter => avhengigheter && avhengigheter.every(harStatus(STATUS.initialisert));

const Innholdslaster = ({avhengigheter, children, spinnerForInitialisert = true}) => {
    if((!spinnerForInitialisert) && erInitialisert(avhengigheter)) {
        return <div>{children}</div>;
    }
    if(erAlleLastet(avhengigheter)) {
        return <div>{children}</div>;
    }
    return <Spinner />;
};

export default Innholdslaster;
