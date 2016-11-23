import React from "react";

import {STATUS} from "../../felles/konstanter";
import Spinner from "./innholdslaster-spinner";

const harStatus = status => element => element.status === status;
const erAlleLastet = avhengigheter => avhengigheter && avhengigheter.every(harStatus(STATUS.lastet));

const Innholdslaster = props => {
    if(erAlleLastet(props.avhengigheter)) {
        return <div>{props.children}</div>;
    }
    return <Spinner />;
};

export default Innholdslaster;
