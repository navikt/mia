import {connect} from "react-redux";
import * as React from "react";


function createKategoriURI(valgtyrkesomrade, valgteyrkesgrupper) {
    if(valgtyrkesomrade && valgtyrkesomrade === 'alle') {
        return null;
    }

    let a = [];
    if(valgtyrkesomrade) {
        a.push('occupationFirstLevels[]=' + encodeURIComponent(valgtyrkesomrade))
    }
    if(valgteyrkesgrupper && valgteyrkesgrupper.length) {
        const l1 = encodeURIComponent(valgtyrkesomrade);
        valgteyrkesgrupper.forEach(gruppe => {
            a.push('occupationSecondLevels[]=' +  l1 + '.' + encodeURIComponent(gruppe))
        })
    }
    return a.filter(x => !!x ).join("&")
}

function findKommuneParam(id, omroder) {
    let kommune = null;
    const fylke = omroder.find(
        fylke =>
            kommune = fylke.underomrader.find(komune => komune.id === id)
    );

    if(fylke) {
        return 'municipals[]=' + encodeURIComponent(fylke.name.toUpperCase()) + '.' + encodeURIComponent(kommune.name.toUpperCase());
    }
    return null;
}

function findFylkeParam(id, omroder) {
    let fylke = omroder.find(omrade => omrade.id === id);
    if(fylke) {
        return 'counties[]=' + encodeURIComponent(omrade.navn.toUpperCase());
    }
    return null
}

function createOmrodeFilter(valgteKommuner, valgteFylker, omrader) {
    let params = [];
    if(valgteFylker && valgteFylker.length) {
        valgteFylker.forEach(fylke => {
            params.push(findFylkeParam(fylke, omrader))
        })
    }
    if(valgteKommuner && valgteKommuner.length) {
        valgteKommuner.forEach(komune => {
            params.push(findKommuneParam(komune, omrader))
        })
    }
    return params.filter(x => !!x).join("&")
}

function createUrl(props) {
    let url = 'https://stillingsok.nav.no/stillinger?';
    let params = [];
    params.push(createKategoriURI(props.valgtyrkesomrade, props.valgteyrkesgrupper));
    params.push(createOmrodeFilter(props.valgteKommuner,props.valgteFylker, props.omrader));
    return url + params.filter(x => !!x).join("&")

}

function TilPam(props) {
    return (
        <div className="pamlenkeboks">
            <a href={createUrl(props)} className="knapp knapp--hoved"> se stillinger </a>
        </div>
    )
}

const stateToProps = state => ({
    yrkesgrupper: state.rest.yrkesgrupper,
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper,
    valgtyrkesomrade: state.ledigestillinger.bransje.valgtyrkesomrade,
    valgteKommuner: state.ledigestillinger.oversikt.valgteKommuner,
    valgteFylker: state.ledigestillinger.oversikt.valgteFylker,
    omrader: state.rest.omrader.data
});

export default connect(stateToProps)(TilPam);
