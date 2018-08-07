let WAIT_TIME;
let ledigestillinger;

module.exports = {
    before: (client) => {
        WAIT_TIME = client.globals.timeout;
        client.resizeWindow(1024, 1024);

        ledigestillinger = client.useXpath().page.ledigestillinger();
        ledigestillinger.navigate();

    },
    after: (client) => {
        client.end();
    },

    'oversikt, bransjer og graf skal være synlig': function () {

        ledigestillinger.expect.section('@oversikt').to.be.present.after(WAIT_TIME);
        ledigestillinger.expect.section('@bransjer').to.be.present.after(WAIT_TIME);
        ledigestillinger.expect.section('@statistikk').to.be.present.after(WAIT_TIME);
    },

    'første bransjeboks skal finnes i bransje cb': function() {
        const cbKategori = ledigestillinger.section.bransjer.elements.bransjeselect;
        const bransjeboks1 = ledigestillinger.section.bransjer.elements.bransjeboks1;

        ledigestillinger.api.waitForElementVisible(cbKategori.selector, WAIT_TIME);
        ledigestillinger.expect.element(cbKategori.selector).value.to.equal('alle');

        ledigestillinger.api.getText(bransjeboks1.selector, result => {
            ledigestillinger.expect.element(cbKategori.selector).text.to.contain(result.value).after(WAIT_TIME);
        });
    },
    'skal vise stillingsannonser for valgte arbeidsomrader': function() {
        const tabeller = ledigestillinger.section.stillingliste.elements.tabeller;
        const stillingTabeller = `${ledigestillinger.section.stillingliste.selector} ${tabeller.selector}`;

        ledigestillinger.section.bransjer.klikkStillingskategoriBoks(1);
        ledigestillinger.section.bransjer.klikkArbeidsomradeBoks(1);
        ledigestillinger.section.bransjer.klikkArbeidsomradeBoks(2);
        ledigestillinger.section.bransjer.klikkArbeidsomradeBoks(3);

        ledigestillinger.section.stillingliste.expect.element('@tabell1').to.be.present.after(WAIT_TIME);
        ledigestillinger.api.elements(tabeller.locateStrategy, stillingTabeller, (result) => {
            ledigestillinger.api.assert.equal(result.value.length, 3, "viser tabell for alle valgte yrkesomrader");
        });
    },
    'skal kunne bytte fra kartvisning til tabellvisning': function() {
        ledigestillinger.section.oversikt.expect.element('@kart').to.be.visible.after(WAIT_TIME);
        ledigestillinger.section.oversikt.visTabell();

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