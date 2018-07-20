import 'leaflet.locatecontrol'

import L from 'leaflet'
import {MapControl} from 'react-leaflet'

import PropTypes from 'prop-types'


export default class LocateControl extends MapControl {
    createLeafletElement(props) {
        const options = {...{
            position: 'bottomleft',
            clickBehavior: {
                inView: 'setView'
            },
            strings: {
                title: 'Her er du',
                popup: 'Her er du'
            }
        }, ...props};
        const {map} = this.context;

        this.lc = L.control.locate(options).addTo(map);
        return this.lc
    }
}


LocateControl.propTypes = {
    options: PropTypes.object
};
