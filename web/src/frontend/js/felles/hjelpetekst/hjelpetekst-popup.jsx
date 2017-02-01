import React, {Component} from 'react';

class HjelpetekstPopup extends Component {

    componentDidMount() {
        window.addEventListener('click', this.props.skjul);
    }

    componentWillUnmount() {
        window.removeEventListener('click', this.props.skjul);
    }

    render() {
        const {id, skjul, tittel, tekst} = this.props;

        return (
            <div role="tooltip"
                 id={id}
                 onClick={(e) => e.stopPropagation()}
                 className="hjelpetekst-tooltip er-synlig">
                <h3 className="decorated hjelpetekst-tittel">{tittel}</h3>
                <div className="hjelpetekst-tekst">
                    <p>{tekst}</p>
                </div>

                <button className="hjelpetekst-lukk" aria-controls={id} onClick={skjul}>
                    <span className="visuallyhidden">Lukk</span>
                </button>
            </div>
        );
    }
}

export default HjelpetekstPopup;