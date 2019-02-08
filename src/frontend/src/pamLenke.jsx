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

function findKouneParam(id, omroder) {
    var param;
    omroder.some(
        fylke => {
            return fylke.underomrader.some(
                komune => {
                    if(komune.id === id) {
                        param = 'municipals[]=' + encodeURIComponent(fylke.name.toUpperCase()) + '.' + encodeURIComponent(komune.name.toUpperCase());
                        return true;
                    }else {
                        return false;
                    }
                }
            )
        }
    );
    return param;
}

function findFyllkeParam(id, omroder) {
    var param;
    omroder.find(
        omrade => {
            if(omrade.id === id) {
                param  = 'counties[]=' + encodeURIComponent(omrade.navn.toUpperCase());
                return true;
            } else {
                return false;
            }
        }
    );
    return param;
}

function createOmrodeFilter(valgteKommuner, valgteFylker, omrader) {
    let params = [];
    if(valgteFylker && valgteFylker.length) {
        valgteFylker.forEach(fylke => {
            params.push(findFyllkeParam(fylke, omrader))
        })
    }
    if(valgteKommuner && valgteKommuner.length) {
        valgteKommuner.forEach(komune => {
            params.push(findKouneParam(komune, omrader))
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
