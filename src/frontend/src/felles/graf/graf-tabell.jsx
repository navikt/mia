import React from 'react';
import PT from 'prop-types';

export const GrafTabell = ({overskrifter, rader, serieNavn, tabelloverskrift, tabellId, periodetype})=> {
    const lagUnikId = (tekst, id) => {
        const unikId = `${tabellId}_${id}_${tekst}`;
        return unikId.replace(/ /g, '_');
    };

    const aar = Object.keys(overskrifter).map((aar, idx)=> {
        return (<th key={idx} id={lagUnikId(aar, idx)} colSpan={overskrifter[aar].length}>{aar}</th>);
    });

    const underoverskrifter = Object
        .keys(overskrifter)
        .map((aar) => overskrifter[aar])
        .reduce((prevTidsintervall, curr)=> prevTidsintervall.concat(curr));

    const underoverskrift = () => {
        const underoverskrifter = Object
            .keys(overskrifter)
            .map((aar) => overskrifter[aar])
            .reduce((prevTidsintervall, curr)=> prevTidsintervall.concat(curr));
        return underoverskrifter.map((tidsintervall, idy)=> {
            return (
                <th key={idy} id={lagUnikId(tidsintervall, idy)}>
                    {tidsintervall}
                 </th>
            );
        });
    };

    const lagArray = (value, len) => {
        var arr = [];
        for (var i = 0; i < len; i ++ ) {
            arr.push(value);
        }
        return arr;
    };

    const aarRad = Object
        .keys(overskrifter)
        .map((aar)=> lagArray(aar, overskrifter[aar].length))
        .reduce((prev, curr)=>prev.concat(curr));

    const headers = (colx, coly)=>{
        const aarId = lagUnikId(aarRad[colx], colx);
        const underoverskriftId = lagUnikId(underoverskrifter[colx], colx);
        const serieNavnId = lagUnikId(serieNavn[coly], coly);
        return [serieNavnId, underoverskriftId, aarId].join(' ');
    };

    const serierHtml = rader.map((rad, coly) => {
        return (
            <tr key={coly}>
                <th id={lagUnikId(serieNavn[coly], coly)}>{serieNavn[coly]}</th>
                {
                    rad.map( (kolonne, colx)=>{
                        var innhold = kolonne === null ? '<4' : Number(kolonne).toLocaleString('nb', {maximumFractionDigits: 1});
                        return <td headers={headers(colx, coly)} key={colx}>{innhold}</td>;
                        })
                    }
            </tr>
        );
    });

    return (
        <div className="tabell-container">
            <table className="tabell tabell-skillestrek">
                <caption className="visuallyhidden">
                    {tabelloverskrift}
                </caption>
                <thead>
                <tr>
                    <th id={`${tabellId}_aar`}>År</th>
                    {aar}
                </tr>
                <tr>
                    <th id={`${tabellId}_periode`}>{periodetype}</th>
                    {underoverskrift()}
                </tr>
                </thead>
                <tbody>
                    {serierHtml}
                </tbody>
            </table>
        </div>
    );
};

GrafTabell.propTypes = {
    overskrifter: PT.object.isRequired,
    rader: PT.array.isRequired,
    serieNavn: PT.array.isRequired,
    tabelloverskrift: PT.oneOfType([PT.string, PT.object]).isRequired,
    tabellId: PT.string.isRequired
};

export default GrafTabell;