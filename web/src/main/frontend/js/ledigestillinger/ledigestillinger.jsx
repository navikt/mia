import React from "react";
import IkkeFerdigPanel from "../felles/ikkeferdig/ikke-ferdig-panel";
import Oversikt from "./oversikt/ledigestillinger-oversikt";
import Bransjer from "./bransje/bransjer";

const LedigeStillinger = () => (
    <div>
        <section className="stillinger-oversikt blokk-m">
            <Oversikt />
        </section>
        <section className="stillinger-bransjer blokk-m">
            <Bransjer />
        </section>
        <section className="stillinger-statistikk blokk-m">
            <IkkeFerdigPanel />
        </section>
    </div>
);

export default LedigeStillinger;