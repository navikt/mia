import { EOS_EU, RESTEN_AV_VERDEN } from '../konstanter';

export const bareValgtEOSRestenAvVerden = (oversikt) => {
  const fylker = oversikt.valgteFylker;
  const kommuner = oversikt.valgteKommuner;

  return fylker.length !== 0 &&
            kommuner.length === 0 &&
            fylker.filter(fylke => fylke !== EOS_EU)
              .filter(fylke => fylke !== RESTEN_AV_VERDEN)
              .length === 0;
};
