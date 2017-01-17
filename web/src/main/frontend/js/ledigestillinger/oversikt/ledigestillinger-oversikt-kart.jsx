import React from "react";
import { Map, TileLayer, GeoJSON } from 'react-leaflet';
import {defineMessages} from 'react-intl';

const meldinger = defineMessages({
    kartplaceholder: {
        id: 'ledigestillinger.oversikt.kartplaceholder',
        defaultMessage: 'Kart over {fylke}-fylke. Det er {stillinger} ledige stillinger.'
    }
});

class Oversiktskart extends React.Component {
    render() {
        const initialPosition = [63, 11.5];
        const initialZoom = 4;
        const maxBounds = [[57, 3], [72, 32.5]];

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

        return (
            <div>
                <div className="oversikt-kart">
                    <Map
                        ref="map"
                        center={initialPosition}
                        zoom={initialZoom}
                        maxBounds={maxBounds}
                        minZoom={initialZoom}
                    >
                        <TileLayer
                            url="/mia/map/{z}/{x}/{y}.png"
                            attribution="<a href='http://www.kartverket.no'>Kartverket</a>"
                        />
                        <GeoJSON data={this.props.geojson} style={geojsonStyling} onEachFeature={onEachFeature}/>
                    </Map>
                </div>
            </div>
        );
    }
}

export default Oversiktskart;