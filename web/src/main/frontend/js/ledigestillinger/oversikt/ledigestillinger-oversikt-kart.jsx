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
        //map.scrollWheelZoom.disable();
        map.keyboard.disable();
        map.boxZoom.disable();
    }

    zoomToWorldView() {
        this.refs.map.leafletElement.fitBounds(this.worldBounds);
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

        const hilightFeature = e => {
            const layer = e.target;
            layer.setStyle({
                fillOpacity: 0.4
            });
        };

        const resetHilight = e =>  {
            e.target.setStyle(geojsonStyling);
        };

        const zoomToFeature = e => {
            this.refs.map.leafletElement.fitBounds(e.target.getBounds());
        };

        const onEachFeature = (feature, layer) => {
            layer.on({
                mouseover: hilightFeature,
                mouseout: resetHilight,
                click: zoomToFeature
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
                        <GeoJSON data={this.props.geojson} style={geojsonStyling} onEachFeature={onEachFeature}/>
                    </Map>
                </div>
            </div>
        );
    }
}

export default injectIntl(Oversiktskart);