import { bareValgtEOSRestenAvVerden } from './filtrering-andre-omrader';
import { EOS_EU, RESTEN_AV_VERDEN } from '../konstanter';

describe('filtervalg', () => {
  it('skal returnere true hvis kun EØS er valgt', () => {
    const oversikt = {
      valgteFylker: ['EOSEU'],
      valgteKommuner: [],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeTruthy();
  });

  it('skal returnere true hvis kun Resten av verden er valgt', () => {
    const oversikt = {
      valgteFylker: [RESTEN_AV_VERDEN],
      valgteKommuner: [],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeTruthy();
  });

  it('skal returnere true hvis Resten av verden og EØS er valgt', () => {
    const oversikt = {
      valgteFylker: [RESTEN_AV_VERDEN, EOS_EU],
      valgteKommuner: [],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeTruthy();
  });

  it('skal returnere false hvis EØS og ett fylke er valgt', () => {
    const oversikt = {
      valgteFylker: ['EOSEU', '01'],
      valgteKommuner: [],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeFalsy();
  });

  it('skal returnere false hvis Resten av verden og ett fylke er valgt', () => {
    const oversikt = {
      valgteFylker: [RESTEN_AV_VERDEN, '01'],
      valgteKommuner: [],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeFalsy();
  });

  it('skal returnere false hvis en kommune er valgt', () => {
    const oversikt = {
      valgteFylker: ['EOSEU'],
      valgteKommuner: ['0101'],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeFalsy();
  });

  it('skal returnere false hvis en kommune er valgt med Resten av verden og EØS', () => {
    const oversikt = {
      valgteFylker: ['EOSEU', RESTEN_AV_VERDEN],
      valgteKommuner: ['0101'],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeFalsy();
  });

  it('skal returnere false hvis hverken EØS eller Resten av verden er valgt', () => {
    const oversikt = {
      valgteFylker: ['01'],
      valgteKommuner: ['0101'],
    };

    expect(bareValgtEOSRestenAvVerden(oversikt)).toBeFalsy();
  });
});
