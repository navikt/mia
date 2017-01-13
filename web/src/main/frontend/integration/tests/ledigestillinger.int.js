var WAIT_TIME;
var ledigestillinger;

module.exports = {
    before: (client) => {
        WAIT_TIME = client.globals.test_settings.timeout;
        ledigestillinger = client.init().page.ledigestillinger();
        ledigestillinger.navigate();
    },
    after: (client) => {
        client.end();
    },
    "oversikt, bransjer og graf skal være synlig": function () {
        ledigestillinger.expect.section('@oversikt').to.be.present.after(WAIT_TIME);
        ledigestillinger.expect.section('@bransjer').to.be.present.after(WAIT_TIME);
        ledigestillinger.expect.section('@statistikk').to.be.present.after(WAIT_TIME);
    },
    "klikk på bransjeboks skal velge stillingskatergori og vise arbeidsomrader": function() {
        const selectboks = ledigestillinger.section.bransjer.elements.bransjeselect;
        const bransjeboks1 = ledigestillinger.section.bransjer.elements.bransjeboks1;

        ledigestillinger.expect.element(selectboks.selector).value.to.equal('alle');
        ledigestillinger.api.getText(`${bransjeboks1.selector} span:first-of-type`, result => {
            ledigestillinger.expect.element(selectboks.selector).text.to.contain(result.value).after(WAIT_TIME);
        });
    },
    "skal vise stillingsannonser for valgte arbeidsomrader": function() {
        const bransjebokser = ledigestillinger.section.bransjer.elements.bransjebokser;
        const selectboks = ledigestillinger.section.bransjer.elements.bransjeselect;
        const arbeidsomrade1 = bransjebokser.selector + ':nth-of-type(1)';
        const arbeidsomrade2 = bransjebokser.selector + ':nth-of-type(2)';
        const arbeidsomrade3 = bransjebokser.selector + ':nth-of-type(3)';
        const tabeller = ledigestillinger.section.stillingliste.elements.tabeller;

        ledigestillinger.section.bransjer.expect.element(arbeidsomrade1).to.be.present.after(WAIT_TIME);
        ledigestillinger.section.bransjer.expect.element(arbeidsomrade2).to.be.present.after(WAIT_TIME);
        ledigestillinger.section.bransjer.expect.element(arbeidsomrade3).to.be.present.after(WAIT_TIME);
        ledigestillinger.api.click(arbeidsomrade1).pause(1000);
        ledigestillinger.api.click(arbeidsomrade1).pause(400);
        ledigestillinger.api.click(arbeidsomrade2).pause(400);
        ledigestillinger.api.click(arbeidsomrade3).pause(400);
        ledigestillinger.section.stillingliste.expect.element('@tabell1').to.be.present.after(WAIT_TIME);

        ledigestillinger.api.elements(tabeller.locateStrategy, tabeller.selector, (result)=> {
            ledigestillinger.api.assert.equal(result.value.length, 3, "viser tabell for alle valgte yrkesomrader");
        });
    },
    "skal vise oversikt over stillinger og arbeidsledige for kommuner valgt i modal": function() {
        const lagreknapp = ledigestillinger.section.modal.elements.lagre;
        const modalknapp = ledigestillinger.section.oversikt.elements.knapp;
        const andreFylke = ledigestillinger.section.modal.elements.checkbox2;
        const fylkesHeader = ledigestillinger.section.oversikt.elements.fylkeheader.selector;
        const oversikt = ledigestillinger.section.oversikt.selector;


        ledigestillinger.api.click(modalknapp.selector).pause(100);
        ledigestillinger.expect.section('@modal').to.be.present.after(WAIT_TIME);

        ledigestillinger.api.getText("#modal " + andreFylke.selector, result => {
            ledigestillinger.api.click(andreFylke.selector).pause(100);
            ledigestillinger.api.click(lagreknapp.selector).pause(200);
            ledigestillinger.assert.containsText(`${oversikt} ${fylkesHeader}`, result.value);
        });
    },
    "skal kunne endre hvilke fylker og kommuner som er valgt i modalen": function() {
        const lagreknapp = ledigestillinger.section.modal.elements.lagre;
        const modalknapp = ledigestillinger.section.oversikt.elements.knapp;
        const forsteFylke = ledigestillinger.section.modal.elements.checkbox1;
        const andreFylke = ledigestillinger.section.modal.elements.checkbox2;
        const fylkesHeader = ledigestillinger.section.oversikt.elements.fylkeheader.selector;
        const oversikt = ledigestillinger.section.oversikt.selector;

        ledigestillinger.api.click(modalknapp.selector).pause(100);
        ledigestillinger.expect.section('@modal').to.be.present.after(WAIT_TIME);
        ledigestillinger.api.click(andreFylke.selector).pause(100);
        ledigestillinger.api.click(forsteFylke.selector).pause(100);

        ledigestillinger.api.getText("#modal " + forsteFylke.selector, result => {
            ledigestillinger.api.click(lagreknapp.selector).pause(200);
            ledigestillinger.assert.containsText(`${oversikt} ${fylkesHeader}`, result.value);
        });
    }
};