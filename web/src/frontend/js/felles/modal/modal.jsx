import React, {Component, PropTypes as PT} from 'react';
import {connect} from 'react-redux';
import ModalVisning from './modal-visning.jsx';
import {lukkModal} from './modal-reducer';

class Modal extends Component {
    render () {
        if(this.props.apenmodal === this.props.id) {
            return <ModalVisning {...this.props}/>;
        } else {
            return null;
        }
    }
}

Modal.propTypes = {
    id: PT.string.isRequired,
    tittel: PT.object.isRequired,
    onLukk: PT.func,
    onLagre: PT.func.isRequired
};

const mapToProps = (state) => {
    return {
        apenmodal: state.modal.apenmodal
    };
};

const actionsToProps = {lukkModal};

export default connect(mapToProps, actionsToProps)(Modal);
