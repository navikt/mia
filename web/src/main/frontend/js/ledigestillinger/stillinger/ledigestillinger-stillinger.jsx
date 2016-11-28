import React from 'react';
import {connect} from 'react-redux';
import {injectIntl} from 'react-intl';
import Stillingsvisning from './ledigestillinger-stillinger-visning';

export const Stillinger = (props) => {

    function skalVises() {
        return props.valgteyrkesgrupper.length > 0;
    }

    return skalVises() ? <Stillingsvisning /> : null;
};


const stateToProps = state => ({
    valgteyrkesgrupper: state.ledigestillinger.bransje.valgteyrkesgrupper
});

export default connect(stateToProps)(injectIntl(Stillinger));
