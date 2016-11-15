import {connect, Provider as ReduxProvider} from 'react-redux';
import {IntlProvider, addLocaleData} from 'react-intl';
import nbLocale from 'react-intl/locale-data/nb';
import React, {PropTypes} from 'react';

addLocaleData(nbLocale);

const Provider = ({store, ...props}) => {
    return (
        <ReduxProvider store={store}>
            <IntlProvider {...props} locale="no" />
        </ReduxProvider>
    );
};

Provider.propTypes = {
    children: PropTypes.element.isRequired
};

const mapStateToProps = (state) => {
    const {intl} = state;
    return {
        ...intl
    };
};

export default connect(mapStateToProps)(Provider);