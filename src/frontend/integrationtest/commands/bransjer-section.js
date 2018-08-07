
module.exports = {
    hentStillingskategoriSelektor(nummer){
        const bransjebokser = this.elements.bransjebokser.selector;
        const stillingskategori = this.elements.kategoriBokser.selector;
        return`${stillingskategori} ${bransjebokser}:nth-of-type(${nummer})`;
    },

    hentArbeidsomradeSelektor(nummer){
        const bransjebokser = this.elements.bransjebokser.selector;
        const arbeidsomradeBokser = this.elements.arbeidsomradeBokser.selector;
        return `${arbeidsomradeBokser} ${bransjebokser}:nth-of-type(${nummer})`;
    },

    klikkStillingskategoriBoks(nummer){
        const timeout = this.api.globals.timeout;
        const bransjeboks = this.hentStillingskategoriSelektor(nummer);

        this.expect.element(bransjeboks).to.be.visible.after(timeout);
        this.click(bransjeboks);
        this.waitForElementVisible('@arbeidsomradeBokser', timeout);
    },

    klikkArbeidsomradeBoks(nummer){
        const bransjeboks = this.hentArbeidsomradeSelektor(nummer);
        this.expect.element(bransjeboks).to.be.visible.after(this.api.globals.timeout);
        this.click(bransjeboks);
        this.api.pause(400);

        this.api.getAttribute(bransjeboks, 'aria-checked', checked => {
            this.assert.equal(checked.status, 0);
            this.verify.equal(checked.value, 'true');

        });
    }
};