const bransjecommands = require('../commands/bransjer-section');
const oversiktcommands = require('../commands/oversikt-section');

module.exports = {
    url: function() {
        return this.api.globals.launch_url;
    },
    elements: {},
    sections: {
        oversikt: {
            selector: '.stillinger-oversikt',
            elements: {
                knappKart: '#switch_kart-knapp',
                knappTabell: '#switch_tabell-knapp',
                tabell: '#switch_tabell',
                kart: '#switch_kart',
                knappVelgOmrade: '[data-testid="knappVelgFylkerOgKommuner"]',
                toggle: '.oversikt-toggle',
                fylkeheader: 'h3'
            },
            commands: [oversiktcommands]

        },
        bransjer: {
            selector: '.stillinger-bransjer',
            elements: {
                bransjeselect: '#select-bransje',
                kategoriBokser: '[data-testid="stillingskategori-bokser"]',
                arbeidsomradeBokser: '[data-testid="arbeidsomrader-bokser"]',
                bransjebokser: '.bransje-boks-container',
                bransjeboks1: '.bransje-boks-container:first-of-type span'
            },
            commands:[bransjecommands]
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
            selector: '.modal',
            elements: {
                lagre: '[data-testid="lagre-knapp"]',
                checkbox1: 'li:nth-of-type(1) input[data-testid="fylker"]+label',
                checkbox2: 'li:nth-of-type(2) input[data-testid="fylker"]+label',
            },
            commands:[
                {
                    hentFylkeSelektor(nummer){
                        return `li:nth-of-type(${nummer}) input[data-testid="fylker"]+label`
                    }
                }
            ]
        }
    }
};
