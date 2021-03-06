import * as L from 'leaflet';

const landvisningControl = L.Control.extend({
  initialize(onClickFunction) {
    this.onClick = onClickFunction;
  },

  options: {
    position: 'topleft',
  },

  onAdd() {
    const container = L.DomUtil.create('div', 'leaflet-bar leaflet-control leaflet-control-custom');
    const kartUrl = process.env.PUBLIC_URL + "/norway.svg";

    container.style.background = 'rgba(255, 255, 255, 0.7)';
    container.style['background-image'] = `url('${kartUrl}')`;
    container.style['background-size'] = '100% 100%';
    container.style.width = '120px';
    container.style.height = '120px';
    container.style.cursor = 'pointer';
    container.onclick = this.onClick;

    return container;
  },
});

export default landvisningControl;
