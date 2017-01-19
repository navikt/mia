import React from "react";
import { Map, TileLayer, GeoJSON } from 'react-leaflet';
import {defineMessages, injectIntl} from 'react-intl';

const meldinger = defineMessages({
    kartplaceholder: {
        id: 'ledigestillinger.oversikt.kartplaceholder',
        defaultMessage: 'Kart for å velge fylker og kommuner.'
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
        map.dragging.disable();
    }

    render() {
        const initialPosition = [63, 13];
        const initialZoom = 5;
        const maxBounds = [[57, 3], [72, 33]];

        const geojsonStyling = {
            color: "#000000",
            fillcolor: "transperant",
            weight: 1,
            fillOpacity: 0
        };

        const highlightStyling = {
            fillOpacity: 0.2,
            weight: 3
        };

        const selectedStyling = {
            fillOpacity: 0.4
        };

        const highlightFeature = e => {
            const layer = e.target;
            let styling = highlightStyling;

            if(layer.feature.properties.valgt === true) {
                styling = {...styling, ...selectedStyling};
            }
            layer.setStyle(styling);
        };

        const resetHighlight = e =>  {
            e.target.setStyle(geojsonStyling);
            if(e.target.feature.properties.valgt === true) {
                e.target.setStyle(selectedStyling);
            }
        };

        const zoomTilFylke = e => {
            this.refs.map.leafletElement.fitBounds(e.target.getBounds());
            this.refs.kommuner.leafletElement.setStyle({ opacity: 0.3 });
            this.refs.kommuner.leafletElement.bringToFront();

        };

        const clickKommune = e => {

            const properties = e.target.feature.properties;
            const kommuneErValgt = properties.valgt === true;
            if(kommuneErValgt) {
                e.target.setStyle(highlightStyling);
            } else {
                e.target.setStyle(selectedStyling);
            }

            properties.valgt = !kommuneErValgt;
        };

        const zoomTilLandvisning = () => {
            this.refs.map.leafletElement.fitBounds(this.worldBounds);
            this.refs.kommuner.leafletElement.setStyle({ opacity: 0 });
            this.refs.fylker.leafletElement.bringToFront();

            this.refs.kommuner.leafletElement.getLayers().forEach(layer => {
                layer.feature.properties.valgt = false;
                layer.setStyle(geojsonStyling);
            });
        };

        const onEachFylke = (feature, layer) => {
            layer.on({
                mouseover: highlightFeature,
                mouseout: resetHighlight,
                click: zoomTilFylke
            });
        };

        const onEachKommune = (feature, layer) => {
            layer.on({
                mouseover: highlightFeature,
                mouseout: resetHighlight,
                click: clickKommune
            });
        };

        const mapProps = {
            center: initialPosition,
            bounds: this.worldBounds,
            zoom: initialZoom,
            maxBounds: maxBounds,
            minZoom: initialZoom,
            maxZoom: 8,
            zoomControl: false,
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
                        <GeoJSON ref="fylker" data={this.props.fylkergeojson} style={geojsonStyling} onEachFeature={onEachFylke}/>
                    </Map>
                </div>
                <button className="knapp knapp-hoved knapp-liten" onClick={() => {zoomTilLandvisning();}}>
                    Reset kart
                </button>
            </div>
        );
    }
}

export default injectIntl(Oversiktskart);