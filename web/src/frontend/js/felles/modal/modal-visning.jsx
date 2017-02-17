import React, {Component} from 'react';
import Portal from './portal.jsx';
import {defineMessages, FormattedMessage} from 'react-intl';
const knapper = {
    TAB: 9,
    SHIFT_TAB: 16,
    ESCAPE: 27
};

const messages = defineMessages({
    lukk: {
        id: 'modal.lukk',
        defaultMessage: 'Lukk'
    },
    avbryt: {
        id: 'modal.avbryt',
        defaultMessage: 'Avbryt'
    },
    lagre: {
        id: 'modal.lagre',
        defaultMessage: 'Lagre'
    },
    fortsett: {
        id: 'modal.fortsett',
        defaultMessage: 'Fortsett'
    },
    oppdater: {
        id: 'modal.oppdater',
        defaultMessage: 'Oppdater'
    }
});

export class ModalVisning extends Component {
    constructor(props) {
        super(props);
        this.onLukk = this.onLukk.bind(this);
        this.preventTab = this.preventTab.bind(this);
        this.preventShiftTab = this.preventShiftTab.bind(this);
        this.closeOnEscape = this.closeOnEscape.bind(this);
        this.forceFocusModal = this.forceFocusModal.bind(this);
        this.lagreOgLukk = this.lagreOgLukk.bind(this);
    }

    componentDidMount() {
        this.body = document.querySelector('body');
        this.html = document.querySelector('html');
        this.pagewrapper = document.querySelector('.pagewrapper');
        this.body.classList.add('modalside');
        this.html.classList.add('modalside');
        this.refs.modal.focus();
        this.pagewrapper.setAttribute('aria-hidden', true);
        this.preventFocusLost();
        this.addEventListeners();
    }

    componentWillUnmount() {
        this.body.classList.remove('modalside');
        this.html.classList.remove('modalside');
        this.pagewrapper.setAttribute('aria-hidden', false);
    }

    addEventListeners() {
        this.refs.lukk.addEventListener('keydown', this.preventShiftTab);
        this.pagewrapper.addEventListener('focusin', this.forceFocusModal);
        this.refs.modal.addEventListener('keydown', this.closeOnEscape);
        this.refs.avbryt.addEventListener('keydown', this.preventTab);
    }

    removeEventListeners() {
        this.refs.modal.removeEventListener('keydown', this.closeOnEscape);
        this.refs.lukk.removeEventListener('keydown', this.preventShiftTab);
        this.pagewrapper.removeEventListener('focusin', this.forceFocusModal);
        this.refs.avbryt.removeEventListener('keydown', this.preventTab);
    }

    onLukk() {
        this.removeEventListeners();
        if(this.props.onLukk) {
            this.props.onLukk();
        }
        this.props.lukkModal();
    }

    lagreOgLukk() {
        this.props.onLagre();
        this.onLukk();
    }

    closeOnEscape(event) {
        if (event.keyCode === knapper.ESCAPE) {
            this.onLukk();
        }
    }

    forceFocusModal(event) {
        event.preventDefault();
        this.refs.modal.focus();
    }

    preventFocusLost() {
        this.body.addEventListener('focusin', this.handleFocusIn);
    }

    preventShiftTab(event) {
        if (event.shiftKey === true && (event.keyCode === knapper.SHIFT_TAB || event.keyCode === knapper.TAB)) {
            event.preventDefault();
            this.refs.avbryt.focus();
        }
    }

    preventTab(event) {
        if (event.shiftKey === false && event.keyCode === knapper.TAB) {
            event.preventDefault();
            this.refs.lukk.focus();
        }
    }

    getMeny(lagreTekst, avbrytTekst) {
        return (
            <div className="knapperad knapperad-adskilt">
                <ul className="ustilet liste-ustilet">
                    <li className="blokk-xs">
                        <button type="button" onClick={this.lagreOgLukk} className="knapp knapp-hoved js-test-lagre">
                            <FormattedMessage {...lagreTekst} />
                        </button>
                    </li>
                    <li>
                        <a href="javascript:void(0)" role="button" className="lenke js-test-avbryt" onClick={this.onLukk} ref="avbryt">
                            <FormattedMessage {...avbrytTekst} />
                        </a>
                    </li>
                </ul>
            </div>
        );
    }

    getMenyForFeilmodal() {
        return this.getMeny(messages.oppdater, messages.fortsett);
    }


    getLagremeny() {
        return this.getMeny(messages.lagre, messages.avbryt);
    }

    getCssKlasserForHeader() {
        const hodeKlasser = "tittel-dekorert typo-innholdstittel blokk";
        return this.props.feilmodal ? hodeKlasser + " hode hode-dekorert hode-feil" : hodeKlasser;
    }

    render() {
        return (
            <Portal target="#modal">
                <div className="modal modal-bakteppe" role="dialog" tabIndex="-1" ref="modal">
                    <div className="container">
                        <section className="modal-vindu panel panel-ramme side-innhold blokk-xl">
                            <button className="modal-lukk" onClick={this.onLukk} ref="lukk">
                                <FormattedMessage {...messages.lukk}/></button>
                            <h1 className={this.getCssKlasserForHeader()}>
                                <FormattedMessage {...this.props.tittel}/></h1>

                            <div>{React.cloneElement(this.props.children)}</div>
                            {this.props.feilmodal ? this.getMenyForFeilmodal() : this.getLagremeny() }
                        </section>
                    </div>
                </div>
            </Portal>
        );
    }
}

export default ModalVisning;
