import React from "react";
import Oversikt from "./oversikt/ledigestillinger-oversikt";
import Bransjer from "./bransjer/ledigestillinger-bransjer";
import Statistikk from "./statistikk/ledigestillinger-statistikk";

const LedigeStillinger = () => (
    <div className="stillinger-container">
        <section className="stillinger-bransjer blokk-m">
            <Bransjer />
        </section>
        <section className="stillinger-oversikt blokk-l">
            <Oversikt />
        </section>
        <section className="stillinger-statistikk blokk-m">
            <Statistikk />
        </section>
    </div>
);

export default LedigeStillinger;
