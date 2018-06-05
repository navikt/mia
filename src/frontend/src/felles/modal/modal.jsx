import React, {Component} from 'react';
import PT from 'prop-types';
import {Innholdstittel} from 'nav-frontend-typografi';
import { Hovedknapp } from 'nav-frontend-knapper';
import { FormattedMessage, defineMessages } from 'react-intl';
import {connect} from 'react-redux';
import Modal from 'nav-frontend-modal';
import {lukkModal} from './modal-reducer';

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

class ModalWrapper extends Component {
    onLukk() {
        if(this.props.onLukk) {
            this.props.onLukk();
        }
        this.props.lukkModal(this.props.id)
    }

    lagreOgLukk() {
        this.props.onLagre();
        this.onLukk();
    }

    getMeny(lagreTekst, avbrytTekst) {
        return (
            <div className="knapperad text-center">
                <ul className="ustilet liste-ustilet">
                    <li className="blokk-xs">
                        <Hovedknapp onClick={() => this.lagreOgLukk()}>
                            <FormattedMessage {...lagreTekst} />
                        </Hovedknapp>
                    </li>
                    <li>
                        <a href="javascript:void(0)" role="button" className="lenke js-test-avbryt" onClick={() => this.onLukk()} ref="avbryt">
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

    render () {
        const isOpen = this.props.apenmodal === this.props.id;
        return (
            <Modal
                isOpen={isOpen}
                onRequestClose={() => this.props.lukkModal(this.props.id)}
                closeButton={true}
                contentLabel="Heisann."
            >
                <div>
                    <Innholdstittel className="blokk-m text-center">
                        <FormattedMessage {...this.props.tittel} />
                    </Innholdstittel>
                    <div className="blokk-l">
                        { this.props.children }
                    </div>
                    {this.props.feilmodal ? this.getMenyForFeilmodal() : this.getLagremeny() }
                </div>
            </Modal>
        )
    }
}

ModalWrapper.propTypes = {
    id: PT.string.isRequired,
    tittel: PT.object.isRequired,
    onLukk: PT.func,
    onLagre: PT.func,
    feilmodal: PT.bool
};

const mapToProps = (state) => {
    return {
        apenmodal: state.modal.apenmodal
    };
};

const actionsToProps = {lukkModal};

export default connect(mapToProps, actionsToProps)(ModalWrapper);
