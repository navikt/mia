import { buildUriParams, fetchToJson } from '../../../felles/rest/rest-utils';
import { ALTERNATIV_ALLE } from '../../../felles/konstanter';
import { finnKommuneForKommunenummer, finnFylkeForFylkenummer } from './kart-utils';

export const getPopupMedInnholdslaster = navn => `
            <h3 class="typo-etikett">${navn}</h3>
            <NavFrontendSpinner type="S" />`;

export const getPopupForOmrade = (navn, data) => `
            <div>
                <h3 class="typo-etikett blokk-xxs">${navn}</h3>
                <span>Arbeidsledige: ${data.antallLedige == null ? '<4' : data.antallLedige}</span><br />
                <span>Ledige stillinger: ${data.antallStillinger == null ? 0 : data.antallStillinger}</span>
            </div>`;

export const hentDataForKommune = (id, yrkesomrade, yrkesgrupper) => hentDataForOmrade(id, null, yrkesomrade, yrkesgrupper, '/omrader/kommunedata');

export const hentDataForFylke = (id, yrkesomrade, yrkesgrupper) => hentDataForOmrade(null, id, yrkesomrade, yrkesgrupper, '/omrader/fylkesdata');

const hentDataForOmrade = (kommune, fylke, yrkesomrade, yrkesgrupper, baseUri) => {
  const params = {
    kommuner: kommune != null ? [kommune] : null,
    fylker: fylke != null ? [fylke] : null,
    yrkesomrade: yrkesomrade === ALTERNATIV_ALLE ? null : yrkesomrade,
    yrkesgrupper,
  };
  const uri = `${baseUri}?${buildUriParams(params)}`;
  return fetchToJson(uri);
};

export const visPopupForFylke = (e, props, feature, layer) => {
  const yrkesomrade = props.valgtYrkesomrade;
  const yrkesgrupper = props.valgteYrkesgrupper;
  const fylke = finnFylkeForFylkenummer(feature.properties.fylkesnummer, props.omrader);
  layer.bindPopup(getPopupMedInnholdslaster(fylke.navn)).openPopup();
  feature.properties.harFokus = true;

  hentDataForFylke(fylke.id, yrkesomrade, yrkesgrupper).then((result) => {
    if (feature.properties.harFokus) {
      layer.bindPopup(getPopupForOmrade(fylke.navn, result[0])).openPopup();
    }
  });
};

export const visPopupForKommune = (e, props, feature, layer) => {
  const yrkesomrade = props.valgtYrkesomrade;
  const yrkesgrupper = props.valgteYrkesgrupper;
  feature.properties.harFokus = true;

  const kommune = finnKommuneForKommunenummer(feature.properties.kommunenummer, props.omrader);
  if (kommune == null) {
    layer.bindPopup(getPopupForOmrade(feature.properties.navn, { antallLedige: '-', antallStillinger: '-' })).openPopup();
  } else {
    layer.bindPopup(getPopupMedInnholdslaster(kommune.navn)).openPopup();
    hentDataForKommune(kommune.id, yrkesomrade, yrkesgrupper).then((result) => {
      if (feature.properties.harFokus) {
        layer.bindPopup(getPopupForOmrade(kommune.navn, result[0])).openPopup();
      }
    });
  }
};
