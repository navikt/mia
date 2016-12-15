import {Component} from 'react';
import {unstable_renderSubtreeIntoContainer as renderSubtreeIntoContainer, unmountComponentAtNode} from 'react-dom';

class Portal extends Component {

    componentDidMount() {
        this.target = document.querySelector(this.props.target);
        this.renderTilTarget();
    }

    componentDidUpdate() {
        this.renderTilTarget();
    }

    renderTilTarget() {
        if(this.props.forceRender === true) {
            this.fjernReactInnholdFraTarget();
        }
        renderSubtreeIntoContainer(
            this,
            this.props.children,
            this.target
        );
    }

    fjernReactInnholdFraTarget() {
        unmountComponentAtNode(this.target);
    }

    componentWillUnmount() {
        this.fjernReactInnholdFraTarget();
    }

    render() {
        return null;
    }
}

export default Portal;