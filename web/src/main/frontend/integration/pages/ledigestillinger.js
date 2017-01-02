module.exports = {
    url: function() {
        return this.api.launchUrl + '/';
    },
    elements: {},
    sections: {
        oversikt: {
            selector: '.stillinger-oversikt',
            elements: {
                kart: '.oversikt-kart'
            }
        },
        bransjer: {
            selector: '.stillinger-bransjer'
        },
        statistikk: {
            selector: '.stillinger-statistikk'
        }
    }
};