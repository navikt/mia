import React from "react";
import IkkeFerdigPanel from "../felles/ikkeferdig/ikke-ferdig-panel";
import Oversikt from "./oversikt/ledigestillinger-oversikt";
import Bransjer from "./bransjer/ledigestillinger-bransjer";
import Stillinger from "./stillinger/ledigestillinger-stillinger";

const LedigeStillinger = () => (
    <div className="stillinger-container">
        <section className="stillinger-oversikt blokk-m">
            <Oversikt />
        </section>
        <section className="stillinger-bransjer blokk-m">
            <Bransjer />
        </section>
        <section className="stillinger-stillingsliste blokk-m">
            <Stillinger />
        </section>
        <section className="stillinger-statistikk blokk-m">
            <IkkeFerdigPanel />
        </section>
    </div>
);

export default LedigeStillinger;