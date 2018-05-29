import {expect, React} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import {LinjeGraf} from './linje-graf.jsx';
import tekster from './graf-tekster';

describe('LinjeGraf', () => {
    before(() => {
        Object.values = o => Object.keys(o).map(a => o[a]);
    });

    it('returnerer ingenting om den ikke har fått data', function () {
        const element = shallow(<LinjeGraf />);

        expect(element.type()).to.equal(null);
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
            tabellOverskrift: null,
            intl: {
                formatMessage: (tekst) => tekst
            }
        };


        const element = shallow(<LinjeGraf {...props} />);
        const switcher = element.find('GrafSwitcher');

        const tabell = switcher.prop('tabell');
        const graf = switcher.prop('graf');

        it('skal lage en grafswitcher med tabell og graf', function () {
            expect(switcher.length).to.equal(1);

            expect(tabell).to.not.be.undefined;
            expect(graf).to.not.be.undefined;
        });

        it('tabellen skal bruke korte serienavn', function () {
            expect(tabell.props.serieNavn).to.eql([
                tekster.serieArbeidsledighet,
                tekster.serieLedigeStillinger
            ]);
        });

        it('grafen skal bruke lengre serienavn inneholdende område', function () {
            expect(graf.props.config.series[0].name).to.eql(tekster.serieArbeidsledighet);
            expect(graf.props.config.series[1].name).to.eql(tekster.serieLedigeStillinger);
        });

        it('kutter bort tittelraden og bruker bare de andre til data', function () {
            expect(tabell.props.rader.length).to.eql(2);
        });
    });

});