import React, { Component } from 'react';
import { connect } from 'react-redux';
import { settValg } from './switcher-reducer';
import SwitcherKnapp from './switcher-knapp.jsx';

export class Switcher extends Component {
    knappTrykket(index) {
        this.props.settValg(this.props.id, index);
    }

    lagKnapper(domId, elementer, valg) {
        return elementer.map((el, index) => {
            return <SwitcherKnapp
                key={index}
                id={domId + '-' + index}
                aktiv={index === valg}
                onClick={() => this.knappTrykket(index)}
                tekst={el.tekst}
            />;
        })
    }

    lagTabs(domId, elementer, valg) {
        return elementer.map((el, index) => {
            return <div id={`${domId}-${index}`}
                 key={index}
                 role="tabpanel"
                 aria-describedby={`${domId}-${index}-knapp`}
                 hidden={index !== valg}
            >
                {elementer[index].element}
            </div>

        })
    }

    render() {
        const { id, elementer, switchere } = this.props;
        const domId = 'switcher-' + id;
        const vistValgNr = switchere[id] ? switchere[id] : 0;
        return (
            <div id={domId}>
                <div role="tablist" className="blokk-s text-right hidden-xs hidden-sm">
                    {this.lagKnapper(domId, elementer, vistValgNr)}
                </div>
                {this.lagTabs(domId, elementer, vistValgNr)}
            </div>
        );
    }
}

export default connect((state) => ({
    switchere: state.ledigestillinger.statistikk.switcher
}), {
    settValg
})(Switcher);
