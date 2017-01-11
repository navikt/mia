import React from "react";
import Oversikt from "./oversikt/ledigestillinger-oversikt";
import Bransjer from "./bransjer/ledigestillinger-bransjer";
import Stillinger from "./stillinger/ledigestillinger-stillinger";

const LedigeStillinger = () => (
    <div className="stillinger-container">
        <div className="stillinger-oversikt blokk-m">
            <Oversikt />
        </div>
        <div className="stillinger-bransjer blokk-m">
            <Bransjer />
        </div>
        <div className="stillinger-stillingsliste blokk-m">
            <Stillinger />
        </div>
        <div className="stillinger-statistikk blokk-m" />
    </div>
);

export default LedigeStillinger;