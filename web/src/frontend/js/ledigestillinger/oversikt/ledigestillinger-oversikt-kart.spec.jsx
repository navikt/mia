import {expect, React} from '../../../test/test-helper';
import {shallow} from "enzyme";
import { Map } from 'react-leaflet';

import { Oversiktskart } from "./ledigestillinger-oversikt-kart";

describe('ledigestillinger oversikt kart', function() {
    beforeEach(() => {
        this.defaultProps = {
            intl: {formatMessage: tekst => tekst.defaultMessage},
            valgtYrkesomrade: 10,
            valgteYrkesgrupper: [20, 30]
        }
    });

    it('skal rendre leaflet-kart', () => {
        const wrapper = shallow(<Oversiktskart {...this.defaultProps}/>);
        expect(wrapper).to.have.descendants(Map);
    });
});

