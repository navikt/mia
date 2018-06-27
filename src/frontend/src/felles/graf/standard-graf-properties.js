const formatterTall = tall => tall.toLocaleString('nb', { maximumFractionDigits: 1 });

const maanederTekst = [
  'Jan',
  'Feb',
  'Mar',
  'Apr',
  'Mai',
  'Jun',
  'Jul',
  'Aug',
  'Sep',
  'Okt',
  'Nov',
  'Des',
];

const farger = {
  hovedAkseFarger: '#b7b1a9',
  oransje: '#FF9100',
  svart: '#3e3832',
  gronn: '#119b49',
  hvit: '#fff',
  tekstSvart: '#3e3832',
};

const standardGrafConfig = ({
  tittel = '', undertittel = '', legend = '', yTittel = 'Dager', yEnhet = 'dager', kategorier, serier,
}) => ({
  chart: {
    spacingTop: 40,
    backgroundColor: 'transparent',
  },
  title: {
    text: tittel,
  },
  subtitle: {
    text: undertittel,
  },
  legend: {
    symbolHeight: 24,
    symbolWidth: 24,
    title: {
      text: legend
    },
    itemStyle: {
      fontSize: '0.8125rem',
      fontFamily: 'Arial, sans-serif',
      fontWeight: 400,
      lineHeight: '19px',
      cursor: 'auto',
      width: 500,
    },
    itemHoverStyle: {
      fontWeight: 400,
      color: 'rgb(51, 51, 51)',
      cursor: 'auto',
    },
    useHTML: true,
  },
  credits: {
    enabled: false,
  },
  xAxis: {
    categories: kategorier,
    labels: {
      style: 'fontSize:12px',
      useHTML: true,
    },
    lineColor: farger.hovedAkseFarger,
    lineWidth: 2,
    tickLength: 22,
    tickColor: farger.hovedAkseFarger,
  },
  yAxis: {
    allowDecimals: false,
    labels: {
      style: 'fontSize:12px',
      format: `{value} <span class="yEnhet">${yEnhet}</span>`,
      useHTML: true,
    },
    title: {
      text: yTittel,
      align: 'high',
      offset: 0,
      rotation: 0,
      y: -25,
      x: 10,
      style: {
        fontWeight: 'bold',
        color: farger.tekstSvart,
      },
    },
    lineColor: farger.hovedAkseFarger,
    lineWidth: 2,
    gridLineDashStyle: 'LongDash',
    gridLineColor: farger.hovedAkseFarger,
  },
  tooltip: {
    hideDelay: 10,
    formatter() {
      const index = this.point.index;
      const denneSerie = this.series;
      const verdi = denneSerie.processedYData[index];

      const yAxis = this.series.yAxis;
      const scale = yAxis.height / (yAxis.max - yAxis.min);

      const overlappendeSerier = this.series.chart.series
        .filter(s => s !== denneSerie)
        .filter((s) => {
          const annenVerdi = s.processedYData[index];
          if (annenVerdi === null) { return false; }

          const diff = Math.abs(annenVerdi - verdi);
          return diff * scale < 15;
        });

      return [denneSerie, ...overlappendeSerier].map((s) => {
        const name = s.userOptions.tooltipName || s.name;
        return `
                    <span class="typo-avsnitt">${name}</span>
                    <span>${this.x}: </span>
                    <span>${formatterTall(s.processedYData[index])}
                        <span class="yEnhet">${yEnhet}</span>
                    </span>
                `;
      }).join('<br /><br />');
    },
    snap: '1px',
    useHTML: true,
    borderColor: farger.tekstSvart,
    backgroundColor: farger.tekstSvart,
    borderRadius: 0,
    shadow: false,
    style: {
      color: '#fff',
      padding: '16px',
    },
  },
  series: serier,
});

const serieFarger = [farger.svart, farger.gronn];
const fillFarger = [farger.svart, farger.hvit];

const konfigurerSerie = (navnListe, tooltipListe = [], markers = []) => (serie, idx) => ({
  data: serie,
  name: navnListe[idx],
  tooltipName: tooltipListe[idx],
  showInLegend: true,
  lineWidth: 4,
  color: serieFarger[idx],
  marker: {
    fillColor: fillFarger[idx],
    lineColor: null,
    lineWidth: 3,
    radius: 5,
    symbol: markers[idx],
  },
  events: {
    legendItemClick: () => false,
  },
  stickyTracking: false,
});

const lagTidsserier = (tidsserier) => {
  if (Array.isArray(tidsserier)) {
    return tidsserier;
  }
  const sub = Object.keys(tidsserier);
  return [].concat.apply([], sub.map(cat => tidsserier[cat].map((item, idx) => {
    const klasser = idx === 0 ? 'aar vises' : 'aar';
    return `<span class="yaksetekst"><span>${item}</span><span class="${klasser}"> ${cat}</span></span>`;
  })));
};
const transformerKolonnedataTilAarOgMaaned = header => header.map(kolonne => ({
  aar: kolonne.substring(0, 4),
  maaned: kolonne.substring(4),
}));

const lagManeder = (headerRad) => {
  const maanedsobjekter = transformerKolonnedataTilAarOgMaaned(headerRad);
  const result = {};
  maanedsobjekter.forEach((mndObjekt) => {
    result[mndObjekt.aar] = leggTilMaaned(mndObjekt, result[mndObjekt.aar]);
  });
  return result;
};

const leggTilMaaned = (mndOjekt, maanedListe = []) => maanedListe.concat(maanederTekst[mndOjekt.maaned - 1]);

export {
  standardGrafConfig,
  serieFarger,
  konfigurerSerie,
  lagTidsserier,
  lagManeder,
  maanederTekst
};
