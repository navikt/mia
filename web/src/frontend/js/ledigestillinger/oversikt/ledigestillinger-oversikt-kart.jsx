import React from "react";
import { Map, TileLayer, GeoJSON } from 'react-leaflet';
import {defineMessages, injectIntl} from 'react-intl';
import {highlightStyling, geojsonStyling, selectedStyling} from './kart/kart-styling';
import LandvisningControl from './kart/kart-landvisning-control';
import {finnIdForKommunenummer, finnIdForFylkenummer, highlightFeature, resetHighlight} from './kart/kart-utils';
import {visPopupForKommune, visPopupForFylke} from './kart/kart-popup';
import {ValgteFylker, ValgteKommuner} from '../../felles/filtervalg/filtervalgVisning';

const meldinger = defineMessages({
    kartplaceholder: {
        id: 'ledigestillinger.oversikt.kartplaceholder',
        defaultMessage: 'Kart for å velge fylker og kommuner.'
    },
    valgteKommuner: {
        id: 'ledigestillinger.oversikt.valgtekommuner',
        defaultMessage: 'Valgte kommuner:'
    },
    valgteFylker: {
        id: 'ledigestillinger.oversikt.valgtefylker',
        defaultMessage: 'Valgte fylker:'
    }
});

class Oversiktskart extends React.Component {
    constructor(props) {
        super(props);
        this.worldBounds = [[58, 3], [71, 31]];
    }

    componentDidMount() {
        const map = this.refs.map.leafletElement;
        map.touchZoom.disable();
        map.doubleClickZoom.disable();
        map.scrollWheelZoom.disable();
        map.keyboard.disable();
        map.boxZoom.disable();
        this.landvisningControl = new LandvisningControl(() => this.zoomTilLandvisning());
    }

    zoomTilLandvisning() {
        this.refs.map.leafletElement.fitBounds(this.worldBounds);
        this.refs.kommuner.leafletElement.setStyle({ opacity: 0 });
        this.refs.fylker.leafletElement.bringToFront();
        this.refs.map.leafletElement.removeControl(this.landvisningControl);
        this.props.resetValg();
        this.fjernSelectedFraFylker();
        this.resetKommuner();
    }

    erLandvisningZoom() {
        return this.refs.map.leafletElement.getZoom() === 5;
    }

    resetKommuner() {
        this.refs.kommuner.leafletElement.getLayers().forEach(layer => {
            layer.feature.properties.valgt = false;
            layer.setStyle(geojsonStyling);
        });
    }

    zoomTilFylke(e) {
        this.refs.map.leafletElement.fitBounds(e.target.getBounds());
        this.refs.kommuner.leafletElement.setStyle({ opacity: 0.3 });
        this.refs.kommuner.leafletElement.bringToFront();
        this.refs.map.leafletElement.addControl(this.landvisningControl);
        this.resetKommuner();
    }

    fjernSelectedFraFylker() {
        this.refs.fylker.leafletElement.getLayers().forEach(layer => {
            layer.setStyle(geojsonStyling);
        });
    }

    clickKommune(e) {
        const properties = e.target.feature.properties;
        const kommuneErValgt = properties.valgt === true;
        const kommuneId = finnIdForKommunenummer(properties.id, this.props.omrader);
        this.fjernSelectedFraFylker();

        if(kommuneErValgt) {
            e.target.setStyle(highlightStyling);
            this.props.avvelgKommune(kommuneId);
        } else {
            e.target.setStyle(selectedStyling);
            this.props.velgKommune(kommuneId);
        }

        properties.valgt = !kommuneErValgt;
    }

    clickFylke(e, layer) {
        this.zoomTilFylke(e);
        const fylkeId = finnIdForFylkenummer(e.target.feature.properties.id, this.props.omrader);
        this.props.velgFylke(fylkeId);
        layer.setStyle({fillOpacity: 0.1});
    }

    render() {
        const initialPosition = [63, 13];
        const initialZoom = 5;
        const maxBounds = [[57, 0], [72, 33]];

        const onEachFylke = (feature, layer) => {
            layer.setStyle(geojsonStyling);
            layer.on({
                mouseover: e => {
                    if(this.erLandvisningZoom()) {
                        highlightFeature(e);
                    }
                    visPopupForFylke(e, this.props, feature, layer);
                },
                mouseout: (e) => {
                    feature.properties.harFokus = false;
                    layer.closePopup();
                    if(this.erLandvisningZoom() && !feature.properties.isZooming) {
                        resetHighlight(e);
                    }
                },
                click: e => {
                    feature.properties.isZooming = true;
                    setTimeout(() => feature.properties.isZooming = false, 1000);
                    this.clickFylke(e, layer);
                }
            });
        };

        const onEachKommune = (feature, layer) => {
            layer.on({
                mouseover: e => {
                    highlightFeature(e);
                    visPopupForKommune(e, this.props, feature, layer);
                },
                mouseout: e => {
                    layer.closePopup();
                    feature.properties.harFokus = false;
                    resetHighlight(e);
                },
                click: e => this.clickKommune(e)
            });
        };

        const mapProps = {
            center: initialPosition,
            bounds: this.worldBounds,
            zoom: initialZoom,
            maxBounds: maxBounds,
            minZoom: initialZoom,
            maxZoom: 8,
            zoomControl: false
        };

        return (
            <div>
                <div className="oversikt-kart" aria-label={this.props.intl.formatMessage(meldinger.kartplaceholder)}>
                    <Map ref="map" {...mapProps}>
                        <TileLayer
                            url="/mia/map/{z}_{x}_{y}.png"
                            attribution="<a href='http://www.kartverket.no'>Kartverket</a>"
                        />
                        <GeoJSON ref="kommuner" data={this.props.kommunergeojson} style={{...geojsonStyling, opacity: 0, weight: 1}} onEachFeature={onEachKommune} />
                        <GeoJSON ref="fylker" data={this.props.fylkergeojson} onEachFeature={onEachFylke}/>
                    </Map>
                    <ValgteKommuner valgteKommuner={this.props.valgteKommuner} tekst={meldinger.valgteKommuner} omrader={this.props.omrader} class={'valgte-omrader'} />
                    <ValgteFylker valgteFylker={this.props.valgteFylker} tekst={meldinger.valgteFylker} omrader={this.props.omrader} class={'valgte-omrader'} />
                </div>
            </div>
        );
    }
}

export default injectIntl(Oversiktskart);