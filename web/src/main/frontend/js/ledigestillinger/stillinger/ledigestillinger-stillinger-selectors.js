import {STATUS} from "../../felles/konstanter";

export const getValgteYrkesgrupperId = state => state.ledigestillinger.bransje.valgteyrkesgrupper;

export const getValgtYrkesomradeId = state => state.ledigestillinger.bransje.valgtyrkesomrade;

export const getHarValgtYrkesgrupper = state => getValgteYrkesgrupperId(state).length > 0;

export const getYrkesgruppeById = (id, state) => {
    if(state.rest.yrkesgrupper.status === STATUS.lastet) {
        return state.rest.yrkesgrupper.data.find(gruppe => gruppe.id === id);
    }
    return null;
};

export const getValgteYrkesgrupper = state => {
    if(state.rest.yrkesgrupper.status === STATUS.lastet) {
        return getValgteYrkesgrupperId(state).map(gruppeid => getYrkesgruppeById(gruppeid, state));
    }
    return [];
};

export const getStillingerForYrkesgruppe = (id, state) => {
    if(state.rest.stillinger.status === STATUS.lastet) {
        return state.rest.stillinger.data.filter(stilling => stilling.yrkesgrupper.includes(id));
    }
    return [];
};

export const getValgteYrkesgrupperMedStillinger = state => {
    return getValgteYrkesgrupper(state).map(yrkesgruppe => ({...yrkesgruppe, stillinger: getStillingerForYrkesgruppe(yrkesgruppe.id, state)}));
};
