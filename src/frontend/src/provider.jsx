import {connect, Provider as ReduxProvider} from 'react-redux';
import {IntlProvider, addLocaleData} from 'react-intl';
import nbLocale from 'react-intl/locale-data/nb';
import React from 'react';
import PropTypes from 'prop-types';
import {formats} from './felles/utils/formats';

addLocaleData(nbLocale);

const Provider = ({store, ...props}) => {
    return (
        <ReduxProvider store={store}>
            <IntlProvider {...props} formats={formats}/>
        </ReduxProvider>
    );
};

Provider.propTypes = {
    children: PropTypes.element.isRequired
};

const mapStateToProps = state => ({...(state.tekster)});

export default connect(mapStateToProps)(Provider);