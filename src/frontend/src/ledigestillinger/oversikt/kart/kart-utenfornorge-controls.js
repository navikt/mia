import * as L from 'leaflet';

export const VelgUtenforNorgeControl = L.Control.extend({
  initialize(onClickFunction, linkText) {
    this.onClick = onClickFunction;
    this.text = linkText;
  },

  options: {
    position: 'topleft',
  },

  onAdd() {
    const container = L.DomUtil.create('a', 'leaflet-control lenke-fremhevet');
    container.setAttribute('role', 'button');
    container.innerHTML = this.text;
    container.style.cursor = 'pointer';
    container.onclick = this.onClick;

    return container;
  },
});

export default VelgUtenforNorgeControl;
