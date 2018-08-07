
module.exports = {
    visTabell() {
        this.waitForElementVisible('@knappTabell', this.api.globals.timeout);
        this.click('@knappTabell');
        this.waitForElementVisible('@tabell', this.api.globals.timeout);
        return this;
    },

    velgFylkeOgKommune() {
        this.click('@knappVelgOmrade');
        this.waitForElementVisible(this.parent.section.modal.selector, this.api.globals.timeout);
        return this
    },

};