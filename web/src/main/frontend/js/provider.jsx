import {connect, Provider as ReduxProvider} from 'react-redux';
import {IntlProvider, addLocaleData} from 'react-intl';
import nbLocale from 'react-intl/locale-data/nb';
import React, {PropTypes} from 'react';
import {formats} from './felles/utils/date-utils';

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