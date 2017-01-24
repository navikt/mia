import React from "react";
import { Map, TileLayer, GeoJSON } from 'react-leaflet';
import {defineMessages, injectIntl, FormattedMessage} from 'react-intl';
import {highlightStyling, geojsonStyling, selectedStyling} from './kart/kart-styling';
import LandvisningControl from './kart/kart-landvisning-control';
import {finnIdForKommunenummer, getNavnForKommuneId, finnIdForFylkenummer, getNavnForFylkeId} from './kart/kart-utils';

const meldinger = defineMessages({
    kartplaceholder: {
        id: 'ledigestillinger.oversikt.kartplaceholder',
        defaultMessage: 'Kart for å velge fylker og kommuner.'
    },
    kommunePopup: {
        id: 'ledigestillinger.oversikt.kommunepopup',
        defaultMessage: '<h3>{kommune}</h3><p>Ledige stillinger: {stillinger}<br />Arbeidsledige: {arbeidsledige}</p>'
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
    }

    valgteKommuner() {
        if(this.props.valgteKommuner.length !== 0) {
            return (
                <p className="valgte-omrader">
                    <span className="typo-element valgte-omrader-tittel">
                        <FormattedMessage {...meldinger.valgteKommuner}/>
                    </span>
                    {this.props.valgteKommuner.map(kommuneid => getNavnForKommuneId(kommuneid, this.props.omrader)).join(', ')}
                </p>
            );
        }
    }

    valgteFylker() {
        if(this.props.valgteFylker.length !== 0) {
            return (
                <p className="valgte-omrader">
                    <span className="typo-element valgte-omrader-tittel">
                        <FormattedMessage {...meldinger.valgteFylker}/>
                    </span>
                    {this.props.valgteFylker.map(fylkeid => getNavnForFylkeId(fylkeid, this.props.omrader)).join(', ')}
                </p>
            );
        }
    }

    render() {
        const initialPosition = [63, 13];
        const initialZoom = 5;
        const maxBounds = [[57, 3], [72, 33]];

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

        const clickKommune = e => {
            const properties = e.target.feature.properties;
            const kommuneErValgt = properties.valgt === true;
            const kommuneId = finnIdForKommunenummer(properties.komm, this.props.omrader);

            if(kommuneErValgt) {
                e.target.setStyle(highlightStyling);
                this.props.avvelgKommune(kommuneId);

            } else {
                e.target.setStyle(selectedStyling);
                this.props.velgKommune(kommuneId);
            }

            properties.valgt = !kommuneErValgt;
        };

        const clickFylke = e => {
            this.zoomTilFylke(e);
            const fylkeId = finnIdForFylkenummer(e.target.feature.properties.fylkesnr, this.props.omrader);
            this.props.velgFylke(fylkeId);
        };

        const onEachFylke = (feature, layer) => {
            layer.on({
                mouseover: highlightFeature,
                mouseout: resetHighlight,
                click: clickFylke
            });
        };

        const onEachKommune = (feature, layer) => {
            layer.on({
                mouseover: e => {
                    feature.properties.hasFocus = true;
                    highlightFeature(e);
                    setTimeout(() => {
                        if(feature.properties.hasFocus) {
                            const tekstAttrs = {
                                kommune: e.target.feature.properties.navn,
                                arbeidsledige: 0 + '',
                                stillinger: 22 + ''
                            };

                            layer.bindPopup(this.props.intl.formatMessage(meldinger.kommunePopup, tekstAttrs)).openPopup();
                        }
                    }, 700);
                },
                mouseout: e => {
                    feature.properties.hasFocus = false;
                    resetHighlight(e);
                },
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
                    {this.valgteKommuner()}
                    {this.valgteFylker()}
                </div>
            </div>
        );
    }
}

export default injectIntl(Oversiktskart);