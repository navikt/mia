import React from 'react';
import {shallow} from 'enzyme';
import {LinjeGraf} from './linje-graf.jsx';
import tekster from './graf-tekster';

describe('LinjeGraf', () => {
    beforeAll(() => {
        Object.values = o => Object.keys(o).map(a => o[a]);
    });

    it('returnerer ingenting om den ikke har fått data', function () {
        const element = shallow(<LinjeGraf />);

        expect(element.type()).toEqual(null);
    });


    describe('sett opp graf', () => {

        const props = {
            id: 'testgraf',
            periodetype: 'Måned',
            tabell: {
                arbeidsledighet: {
                    201601: 11,
                    201602: 12,
                    201603: 13,
                    201604: 14,
                },
                ledigestillinger: {
                    201601: 111,
                    201602: 112,
                    201603: 113,
                    201604: 114,
                }
            },
            tabellOverskrift: {
                id: 'test',
                defaultMessage: 'test'
            },
            intl: {
                formatMessage: (tekst) => tekst
            }
        };


        const element = shallow(<LinjeGraf {...props} />);
        const switcher = element.find('GrafSwitcher');

        const tabell = switcher.prop('tabell');
        const graf = switcher.prop('graf');

        it('skal lage en grafswitcher med tabell og graf', function () {
            expect(switcher.length).toEqual(1);

            expect(tabell).not.toBeUndefined();
            expect(graf).not.toBeUndefined();
        });

        it('tabellen skal bruke korte serienavn', function () {
            expect(tabell.props.serieNavn).toMatchObject([
                tekster.serieArbeidsledighet,
                tekster.serieLedigeStillinger
            ]);
        });

        it('grafen skal bruke lengre serienavn inneholdende område', function () {
            expect(graf.props.config.series[0].name).toEqual(tekster.serieArbeidsledighet);
            expect(graf.props.config.series[1].name).toEqual(tekster.serieLedigeStillinger);
        });

        it('kutter bort tittelraden og bruker bare de andre til data', function () {
            expect(tabell.props.rader.length).toEqual(2);
        });
    });

});