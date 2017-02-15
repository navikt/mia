import React from "react";
import Oversikt from "./oversikt/ledigestillinger-oversikt";
import Bransjer from "./bransjer/ledigestillinger-bransjer";
import Stillinger from "./stillinger/ledigestillinger-stillinger";
import Statistikk from "./statistikk/ledigestillinger-statistikk";

const LedigeStillinger = () => (
    <div className="stillinger-container">
        <div className="stillinger-oversikt blokk-l">
            <Oversikt />
        </div>
        <div className="stillinger-bransjer blokk-m">
            <Bransjer />
        </div>
        <div className="stillinger-stillingsliste blokk-m">
            <Stillinger />
        </div>
        <div className="stillinger-statistikk blokk-m">
            <Statistikk />
        </div>
    </div>
);

export default LedigeStillinger;