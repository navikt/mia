import React from 'react';
import {expect, sinon} from '../../../test/test-helper';
import {shallow} from 'enzyme';

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
            expect(newState.valgteyrkesgrupper).to.not.include(1);
        });

        it('skal sette valgteyrkesgrupper til å inneholde 1 når vi kaller action "YRKESGRUPPE_SELECT med payload 1', () => {
            const newState = BransjeReducer({valgteyrkesgrupper: [2,3,4,5,6,7]}, { type: actions.yrkesgruppeselect, payload: 1 });
            expect(newState.valgteyrkesgrupper).to.include(1);
        });

        it("skal sette valgtyrkesomrade til 1 når vi kaller action YRKESOMRADE_SELECT med payload 1", () => {
            const newState = BransjeReducer({}, { type: actions.yrkesomradeselect, payload:  1});
            expect(newState.valgtyrkesomrade).to.equal(1);
        });

        it("skal sette valgteyrkesgrupper til tom når vi kaller action YRKESOMRADE_SELECT", () => {
            const newState = BransjeReducer({valgteyrkesgrupper: [2,3,4,5,6,7]}, { type: actions.yrkesomradeselect, payload:  1});
            expect(newState.valgteyrkesgrupper).to.be.empty;
        });
    });

    describe('bransjer-util', () => {
        it('skal returnere riktig navn for yrkesområde basert på yrkesområdeid', () => {
            const yrkesomradenavnHandel = getNavnForYrkesomradeId("1", yrkedata);
            const yrkesomradenavnReiseliv = getNavnForYrkesomradeId("2", yrkedata);
            const yrkesomradenavnAlle = getNavnForYrkesomradeId("3", yrkedata);
            const yrkesomradenavnNull = getNavnForYrkesomradeId(null, yrkedata);

            expect(yrkesomradenavnHandel).to.equal("Handel, kundeservice, restaurant og reiseliv");
            expect(yrkesomradenavnReiseliv).to.equal("Reiseliv, hotell og overnatting");
            expect(yrkesomradenavnAlle).to.equal("Alle");
            expect(yrkesomradenavnNull).to.equal("Alle");
        });

        it('skal returnere riktig navn for yrkesgruppe basert på yrkesgruppeid', () => {
            const handel = getNavnForYrkesgruppeId("1", yrkedata.data);
            const reiseliv = getNavnForYrkesgruppeId("2", yrkedata.data);
            const ikkeIListen = getNavnForYrkesgruppeId("3", yrkedata.data);
            const nullert = getNavnForYrkesgruppeId(null, yrkedata.data);

            expect(handel[0]).to.equal("Handel, kundeservice, restaurant og reiseliv");
            expect(reiseliv[0]).to.equal("Reiseliv, hotell og overnatting");
            expect(ikkeIListen).to.be.null;
            expect(nullert).to.be.null;
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
            expect(wrapper.is('div')).to.be.true;
            expect(wrapper.find('select').length).to.equal(1);
        });
    });

    describe('bransjer-oversikt', () => {
        it('skal rendre bransjeoversikt med en bransjedropdown', () => {
            const wrapper = shallow(<BransjerOversikt yrkesomrader={yrkedata.data}
                                                      totantallstillinger={11} />);
            expect(wrapper.is('div')).to.be.true;
            expect(wrapper).to.have.descendants(DropdownMedIntl);
        });

        describe('toggleYrkesgrupper', function() {
            beforeEach(() => {
                this.props = {
                    dispatch: sinon.spy(),
                    valgteyrkesgrupper: []
                };
            });

            it('skal deselecte yrkesgruppe', () => {
                const bransjeOversikt = new BransjerOversikt({...this.props, valgteyrkesgrupper: ["1"]});
                bransjeOversikt.toggleYrkesgruppe("1");
                expect(this.props.dispatch).to.have.been.calledWith({ payload: "1", type: actions.yrkesgruppedeselect });
            });

            it('skal selecte yrkesgruppe', () => {
                const bransjeOversikt = new BransjerOversikt(this.props);
                bransjeOversikt.toggleYrkesgruppe("1");
                expect(this.props.dispatch).to.have.been.calledWith({ payload: "1", type: actions.yrkesgruppeselect });
            });
        });

        describe('velgYrkesomrade', function() {
            beforeEach(() => {
                this.props = {
                    dispatch: sinon.spy(),
                    valgtyrkesomrade: ""
                };
            });

           it('skal selecte yrkesområde', () => {
               const bransjeOversikt = new BransjerOversikt(this.props);
               bransjeOversikt.velgYrkesomrade("1");
               expect(this.props.dispatch).to.have.been.calledWith({ payload: "1", type: actions.yrkesomradeselect });
           });
        });
    });
});
