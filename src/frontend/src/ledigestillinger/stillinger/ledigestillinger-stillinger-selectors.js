
export const getValgteYrkesgrupperId = state => state.ledigestillinger.bransje.valgteyrkesgrupper;

export const getValgtYrkesomradeId = state => state.ledigestillinger.bransje.valgtyrkesomrade;


export const getHarValgtOmrade = state => state.ledigestillinger.oversikt.valgteFylker.length > 0 || state.ledigestillinger.oversikt.valgteKommuner.length > 0;
