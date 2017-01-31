const formatterTall = (tall) => tall.toLocaleString('nb', {maximumFractionDigits: 1});

const maanederTekst = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "Mai",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Okt",
    "Nov",
    "Des"
];
const kvartalTekst = {
    k01: '1. kvartal',
    k02: '2. kvartal',
    k03: '3. kvartal',
    k04: '4. kvartal',
    '01': '1. kvartal',
    '02': '2. kvartal',
    '03': '3. kvartal',
    '04': '4. kvartal',
    k1: '1. kvartal',
    k2: '2. kvartal',
    k3: '3. kvartal',
    k4: '4. kvartal'
};

const farger = {
    hovedAkseFarger: '#b7b1a9',
    oransje: '#FF9100',
    svart: '#3e3832',
    gronn: '#119b49',
    tekstSvart: '#3e3832'
};

const fjernSirklerILegendSymbolet = (event) => {
    if (!('remove' in Element.prototype)) {
        Element.prototype.remove = function () {
            if (this.parentNode) {
                this.parentNode.removeChild(this);
            }
        };
    }

    let paths = event.target.container.querySelectorAll(".highcharts-legend-item path:nth-child(2)");
    for (let i = 0; i < paths.length; i++) {
        paths[i].remove();
    }
};

const standardGrafConfig = ({tittel = '', undertittel = '', legend = '', yTittel='Dager', yEnhet='dager', kategorier, serier}) => ({
    chart: {
        spacingTop: 40,
        backgroundColor: 'transparent',
        events: {
            load: fjernSirklerILegendSymbolet,
            redraw: fjernSirklerILegendSymbolet
        }
    },
    title: {
        text: tittel
    },
    subtitle: {
        text: undertittel
    },
    legend: {
        title: {
            text: legend
        },
        itemStyle: {
            fontSize: '0.8125rem',
            fontFamily: 'Arial, sans-serif',
            fontWeight: 400,
            cursor: 'auto',
            width: 500
        },
        itemHoverStyle: {
            fontWeight: 400,
            color: 'rgb(51, 51, 51)',
            cursor: 'auto'
        },
        useHTML: true
    },
    credits: {
        enabled: false
    },
    xAxis: {
        categories: kategorier,
        labels: {
            style: 'fontSize:12px',
            useHTML: true
        },
        lineColor: farger.hovedAkseFarger,
        lineWidth: 2,
        tickLength: 22,
        tickColor: farger.hovedAkseFarger
    },
    yAxis: {
        allowDecimals: false,
        labels: {
            style: 'fontSize:12px',
            format: '{value} <span class="yEnhet">' + yEnhet + '</span>',
            useHTML: true
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
                color: farger.tekstSvart
            }
        },
        lineColor: farger.hovedAkseFarger,
        lineWidth: 2,
        gridLineDashStyle: 'LongDash',
        gridLineColor: farger.hovedAkseFarger
    },
    tooltip: {
        hideDelay: 10,
        formatter: function () {
            let index = this.point.index;
            let denneSerie = this.series;
            let verdi = denneSerie.processedYData[index];

            let yAxis = this.series.yAxis;
            let scale =  yAxis.height / (yAxis.max - yAxis.min);

            let overlappendeSerier = this.series.chart.series
                .filter((s) => s !== denneSerie)
                .filter((s) => {
                    let annenVerdi = s.processedYData[index];
                    if (annenVerdi === null) {return false;}
                    
                    let diff = Math.abs(annenVerdi - verdi);
                    return diff * scale < 15;
                });

            return [denneSerie, ...overlappendeSerier].map((s) => {
                const name = s.userOptions.tooltipName || s.name;
                return `
                    <span class="typo-etikett-liten">${name}</span>
                    <span>${this.x}: </span>
                    <span>${formatterTall(s.processedYData[index])}
                        <span class="yEnhet">${yEnhet}</span>
                    </span>
                `;
            }).join('<br /><br />');
        },
        snap: "1px",
        useHTML: true,
        borderColor: farger.tekstSvart,
        backgroundColor: farger.tekstSvart,
        borderRadius: 0,
        shadow: false,
        style: {
            color: '#fff',
            padding: '16px'
        }
    },
    series: serier
});

const serieFarger = [farger.svart, farger.gronn, farger.oransje];
const konfigurerSerie = (navnListe, tooltipListe = []) => (serie, idx) => {
    return {
        data: serie,
        name: navnListe[idx],
        tooltipName: tooltipListe[idx],
        showInLegend: true,
        lineWidth: 4,
        color: serieFarger[idx],
        marker: {
            fillColor: '#fff',
            lineColor: null,
            lineWidth: 3,
            radius: 5,
            symbol: 'circle'
        },
        events: {
            legendItemClick: () => false
        },
        stickyTracking: false
    };
};

const lagTidsserier = (tidsserier) => {
    if (Array.isArray(tidsserier)) {
        return tidsserier;
    } else {
        const sub = Object.keys(tidsserier);
        return [].concat.apply([], sub.map((cat) => tidsserier[cat].map((item, idx) => {
            const klasser = idx === 0 ? "aar vises" : "aar";
            return '<span class="yaksetekst"><span>' + item + '</span><span class="' + klasser + '"> ' + cat + '</span></span>';
        })));
    }
};
const transformerKolonnedataTilAarOgMaaned = (header) => header.map((kolonne) => ({
    aar: kolonne.substring(0, 4),
    maaned: kolonne.substring(4)
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
    maanederTekst,
    kvartalTekst
};