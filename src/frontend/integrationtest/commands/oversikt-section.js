
module.exports = {
    visTabell() {
        this.waitForElementVisible('@knappTabell', this.api.globals.test_settings.timeout);
        this.click('@knappTabell');
        this.waitForElementVisible('@tabell', this.api.globals.test_settings.timeout);
        return this;
    },

    velgFylkeOgKommune() {
        this.click('@knappVelgOmrade');
        this.waitForElementVisible(this.parent.section.modal.selector, this.api.globals.test_settings.timeout);
        return this
    },

};