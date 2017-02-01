import React, {Component} from 'react';
import {connect} from 'react-redux';
import HjelptekstPopup from './hjelpetekst-popup.jsx';
import {apneHjelpetekst, lukkHjelpetekster} from './hjelpetekst-reducer';

export class Hjelpetekst extends Component {

    constructor(props) {
        super(props);

        this.toggle = this.toggle.bind(this);
        this.vis = this.vis.bind(this);
        this.skjul = this.skjul.bind(this);
    }

    toggle(event) {
        if (this.erApen()) {
            this.skjul(event);
        } else {
            this.vis(event);
        }
    }

    vis(event) {
        event.stopPropagation();
        this.props.apneHjelpetekst(this.getId());
    }

    skjul() {
        this.props.lukkHjelpetekster();
        this.refs.apneknapp.focus();
    }

    erApen() {
        return this.props.hjelpetekst.apenId === this.getId();
    }

    getId() {
        return this.props.id ? this.props.id : this.props.tittel.props.id;
    }

    componentDidMount() {
        let parent = this.refs.div.parentNode;
        parent.classList.add('hjelpetekst-parent');
        if (this.props.inline) {
            parent.classList.add('hjelpetekst-parent-block');
        }
    }

    render() {
        const domId = 'tooltip-' + this.getId();
        const className = 'hjelpetekst';

        return (
            <div className={className} ref="div">
                <button
                    className="hjelpetekst-ikon"
                    aria-describedby={domId}
                    onClick={this.toggle}
                    ref="apneknapp">
                    <span aria-hidden="true">
                        ?
                    </span>
                    <span className="vekk">
                        Hjelpetekst
                    </span>
                </button>

                {this.erApen() ?
                    <HjelptekstPopup
                        id={domId}
                        skjul={this.skjul}
                        tittel={this.props.tittel}
                        tekst={this.props.tekst}
                    />
                    : null
                }

            </div>
        );
    }
}


export default connect((state) => ({
    hjelpetekst: state.hjelpetekst
}), {
    apneHjelpetekst,
    lukkHjelpetekster
})(Hjelpetekst);