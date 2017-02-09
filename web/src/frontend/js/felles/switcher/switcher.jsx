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
        });
    }

    render() {
        const { id, elementer, switchere } = this.props;
        const domId = 'switcher-' + id;
        const vistValgNr = switchere[id] ? switchere[id] : 0;
        const ariaDescribedBy = `${domId}-${vistValgNr}-knapp`;

        return (
            <div id={domId}>
                <div role="tablist" className="blokk-s text-right">
                    {this.lagKnapper(domId, elementer, vistValgNr)}
                </div>
                <div id={`${domId}-${vistValgNr}`}
                     role="tabpanel"
                     aria-describedby={ariaDescribedBy}>
                    {elementer[vistValgNr].element}
                </div>
            </div>
        );
    }
}

export default connect((state) => ({
    switchere: state.ledigestillinger.statistikk.switcher
}), {
    settValg
})(Switcher);
