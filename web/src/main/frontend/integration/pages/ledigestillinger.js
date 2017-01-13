module.exports = {
    url: function() {
        return this.api.launchUrl + '/';
    },
    elements: {},
    sections: {
        oversikt: {
            selector: '.stillinger-oversikt',
            elements: {
                kart: '.oversikt-kart',
                knapp: 'button',
                fylkeheader: 'h2'
            }
        },
        bransjer: {
            selector: '.stillinger-bransjer',
            elements: {
                bransjeselect: 'select',
                bransjebokser: '.bransje-boks-container',
                bransjeboks1: '.bransje-boks-container:first-of-type'
            }
        },
        stillingliste: {
            selector: '.stillinger-stillingsliste',
            elements: {
                tabeller: 'table',
                tabell1: 'table:nth-of-type(1)',
                tabell2: 'table:nth-of-type(2)'
            }
        },
        statistikk: {
            selector: '.stillinger-statistikk'
        },
        modal: {
            selector: '#modal .container',
            elements: {
                lagre: '.js-test-lagre',
                checkbox1: 'li:nth-of-type(1) > input[type="checkbox"]+label',
                checkbox2: 'li:nth-of-type(2) > input[type="checkbox"]+label'
            }
        }
    }
};