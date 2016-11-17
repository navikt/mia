import React from "react";
import IkkeFerdigPanel from "../felles/ikkeferdig/ikke-ferdig-panel";

const LedigeStillinger = () => (
    <div>
        <section className="stillinger-kart blokk-m">
            <IkkeFerdigPanel />
        </section>
        <section className="stillinger-bransjer blokk-m">
            <IkkeFerdigPanel />
        </section>
        <section className="stillinger-statistikk blokk-m">
            <IkkeFerdigPanel />
        </section>
    </div>
);

export default LedigeStillinger;