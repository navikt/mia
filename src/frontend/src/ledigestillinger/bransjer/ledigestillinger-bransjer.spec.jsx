import React from 'react';
import { shallow } from 'enzyme';

import BransjeReducer, {actions} from './ledigestillinger-bransjer-reducer';
import {getNavnForYrkesomradeId, getNavnForYrkesgruppeId} from './ledigestillinger-bransjer-util';
import DropdownMedIntl, {BransjeDropdown} from './bransje-dropdown';
import {BransjerOversikt} from './ledigestillinger-bransjer-oversikt';

describe('bransjer', () => {
    let yrkedata = {};
    beforeEach(() => {
        yrkedata = {
            data: [{
                antallStillinger: 123,
                id: "1",
                navn: "Handel, kundeservice, restaurant og reiseliv",
                parent: null,
                strukturkode: null
            },
                {
                    antallStillinger: 456,
                    id: "2",
                    navn: "Reiseliv, hotell og overnatting",
                    parent: null,
                    strukturkode: null
                }]
        };
    });
    describe('bransje-reducer', () => {
        it('skal sette valgteyrkesgrupper til ikke inneholde 1 når vi kaller action "YRKESGRUPPE_DESELECT med payload 1', () => {
            const newState = BransjeReducer({valgteyrkesgrupper: [1,2,3,4,5,6,7]}, { type: actions.yrkesgruppedeselect, payload: 1 });
            expect(newState.valgteyrkesgrupper.includes(1)).toBeFalsy();
        });

        it('skal sette valgteyrkesgrupper til å inneholde 1 når vi kaller action "YRKESGRUPPE_SELECT med payload 1', () => {
            const newState = BransjeReducer({valgteyrkesgrupper: [2,3,4,5,6,7]}, { type: actions.yrkesgruppeselect, payload: 1 });
            expect(newState.valgteyrkesgrupper.includes(1)).toBeTruthy();
        });

        it("skal sette valgtyrkesomrade til 1 når vi kaller action YRKESOMRADE_SELECT med payload 1", () => {
            const newState = BransjeReducer({}, { type: actions.yrkesomradeselect, payload:  1});
            expect(newState.valgtyrkesomrade).toEqual(1);
        });

        it("skal sette valgteyrkesgrupper til tom når vi kaller action YRKESOMRADE_SELECT", () => {
            const newState = BransjeReducer({valgteyrkesgrupper: [2,3,4,5,6,7]}, { type: actions.yrkesomradeselect, payload:  1});
            expect(newState.valgteyrkesgrupper).toMatchObject([]);
        });
    });

    describe('bransjer-util', () => {
        it('skal returnere riktig navn for yrkesområde basert på yrkesområdeid', () => {
            const yrkesomradenavnHandel = getNavnForYrkesomradeId("1", yrkedata);
            const yrkesomradenavnReiseliv = getNavnForYrkesomradeId("2", yrkedata);
            const yrkesomradenavnAlle = getNavnForYrkesomradeId("3", yrkedata);
            const yrkesomradenavnNull = getNavnForYrkesomradeId(null, yrkedata);

            expect(yrkesomradenavnHandel).toEqual("Handel, kundeservice, restaurant og reiseliv");
            expect(yrkesomradenavnReiseliv).toEqual("Reiseliv, hotell og overnatting");
            expect(yrkesomradenavnAlle).toEqual("Alle");
            expect(yrkesomradenavnNull).toEqual("Alle");
        });

        it('skal returnere riktig navn for yrkesgruppe basert på yrkesgruppeid', () => {
            const handel = getNavnForYrkesgruppeId("1", yrkedata.data);
            const reiseliv = getNavnForYrkesgruppeId("2", yrkedata.data);
            const ikkeIListen = getNavnForYrkesgruppeId("3", yrkedata.data);
            const nullert = getNavnForYrkesgruppeId(null, yrkedata.data);

            expect(handel[0]).toEqual("Handel, kundeservice, restaurant og reiseliv");
            expect(reiseliv[0]).toEqual("Reiseliv, hotell og overnatting");
            expect(ikkeIListen).toEqual(null);
            expect(nullert).toEqual(null);
        });
    });

    describe('bransje-dropdown', () => {
        it('skal rendre dropdown', () => {
            const formatMessage = (tekst) => tekst.defaultMessage;
            const wrapper = shallow(<BransjeDropdown yrkesomrader={yrkedata.data}
                                                     yrkesomrade={"1"}
                                                     onClick={null}
                                                     totaltAntall={10}
                                                     intl={{formatMessage}}
            />);
            expect(wrapper.is('div')).toBeTruthy();
            expect(wrapper.find('Select').length).toEqual(1);
        });
    });

    describe('bransjer-oversikt', () => {
        it('skal rendre bransjeoversikt med en bransjedropdown', () => {
            const wrapper = shallow(<BransjerOversikt yrkesomrader={yrkedata.data}
                                                      totantallstillinger={11} />);
            expect(wrapper.is('div')).toBeTruthy();
            expect(wrapper.find(DropdownMedIntl).length).toBeGreaterThan(0);
        });

        describe('toggleYrkesgrupper', function() {
            beforeEach(() => {
                this.props = {
                    dispatch: jest.fn(),
                    valgteyrkesgrupper: []
                };
            });

            it('skal deselecte yrkesgruppe', () => {
                const bransjeOversikt = new BransjerOversikt({...this.props, valgteyrkesgrupper: ["1"]});
                bransjeOversikt.toggleYrkesgruppe("1");
                expect(this.props.dispatch).toHaveBeenCalledWith({ payload: "1", type: actions.yrkesgruppedeselect });
            });

            it('skal selecte yrkesgruppe', () => {
                const bransjeOversikt = new BransjerOversikt(this.props);
                bransjeOversikt.toggleYrkesgruppe("1");
                expect(this.props.dispatch).toHaveBeenCalledWith({ payload: "1", type: actions.yrkesgruppeselect });
            });
        });

        describe('velgYrkesomrade', function() {
            beforeEach(() => {
                this.props = {
                    dispatch: jest.fn(),
                    valgtyrkesomrade: ""
                };
            });

           it('skal selecte yrkesområde', () => {
               const bransjeOversikt = new BransjerOversikt(this.props);
               bransjeOversikt.velgYrkesomrade("1");
               expect(this.props.dispatch).toHaveBeenCalledWith({ payload: "1", type: actions.yrkesomradeselect });
           });
        });
    });
});
