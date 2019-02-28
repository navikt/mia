let WAIT_TIME;
let ledigestillinger;
let isMobile;

module.exports = {
    before: (client) => {
        WAIT_TIME = client.globals.timeout;
        ledigestillinger = client.useXpath().page.ledigestillinger();
        ledigestillinger.navigate();

        const capabilities = client.options.desiredCapabilities;
        isMobile = capabilities.device !== undefined;
        if(!isMobile) client.resizeWindow(1024, 768);
        client.useCss();
    },
    after: (client) => {
        client.end();
    },

    'oversikt, bransjer og graf skal være synlig': function () {
        let selektor = isMobile?'@tabell':'@kart';
        let oversikt = ledigestillinger.section.oversikt;
        ledigestillinger.expect.section('@bransjer').to.be.visible.after(WAIT_TIME);
        ledigestillinger.expect.section('@oversikt').to.be.visible.after(WAIT_TIME);
        ledigestillinger.expect.section('@statistikk').to.be.visible.after(WAIT_TIME);
        oversikt.expect.element(selektor).to.be.visible.after(WAIT_TIME);

    },

    'skal vise arbeidsområder etter valg av stillingskategoriboks': function() {
        ledigestillinger.section.bransjer.klikkStillingskategoriBoks(1);
    },
    'skal kunne bytte fra kartvisning til tabellvisning': function() {
        if(!isMobile) {
            ledigestillinger.section.oversikt.expect.element('@kart').to.be.visible.after(WAIT_TIME);
            ledigestillinger.section.oversikt.visTabell();
        }
    },
    'skal vise oversikt over stillinger og arbeidsledige for kommuner valgt i modal': function() {
        const oversikt = ledigestillinger.section.oversikt;

        const modal = ledigestillinger.section.modal;
        const lagreknapp = modal.elements.lagre;
        const andreFylke = modal.elements.checkbox2.selector;

        const fylkesHeader = oversikt.elements.fylkeheader.selector;
        ledigestillinger.section.oversikt.velgFylkeOgKommune();

        ledigestillinger.api.getText(andreFylke, result => {
            ledigestillinger.api.click(andreFylke).pause(300);
            ledigestillinger.api.click(lagreknapp.selector).pause(300);
            ledigestillinger.expect.section('@oversikt').to.be.visible.after(WAIT_TIME);
            ledigestillinger.section.oversikt.expect.element('@fylkeheader').to.be.visible.after(WAIT_TIME+1000);

            ledigestillinger.assert.containsText(`${oversikt.selector} ${fylkesHeader}`, result.value);
        });
    },
    'skal kunne endre hvilke fylker og kommuner som er valgt i modalen': function() {
        const modal = ledigestillinger.section.modal;
        const lagreknapp = modal.elements.lagre.selector;
        const forsteFylke = modal.hentFylkeSelektor(1);
        const andreFylke = modal.hentFylkeSelektor(2);
        const fylkesHeader = ledigestillinger.section.oversikt.elements.fylkeheader.selector;
        const oversikt = ledigestillinger.section.oversikt.selector;

        ledigestillinger.section.oversikt.velgFylkeOgKommune();

        ledigestillinger.api.click(andreFylke).pause(100);
        ledigestillinger.api.click(forsteFylke).pause(100);

        ledigestillinger.api.getText(forsteFylke, result => {
            ledigestillinger.api.click(lagreknapp).pause(200);
            ledigestillinger.section.oversikt.expect.element('@fylkeheader').to.be.visible.after(WAIT_TIME+1000);
            ledigestillinger.assert.containsText(`${oversikt} ${fylkesHeader}`, result.value);
        });
    }
};
