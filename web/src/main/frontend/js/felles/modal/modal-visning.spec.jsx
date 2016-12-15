import {expect, React} from '../../../test/test-helper';
import {shallow} from 'enzyme';
import {ModalVisning} from './modal-visning.jsx';

describe('ModalVisning - ', () => {
    var props;
    beforeEach(() => {
        props = {
            id: "testbase",
            lukkModal: function() {},
            tekster: {
                tittel: {}
            },
            children: <div id="mittIndreBarn"> </div>,
            apenmodal: true,
            onLukk: "onLukkCallback",
            feilmelding: "Du gjorde feil!"
        };
    });

    it('Skal vises', () => {
        const wrapper = shallow(<ModalVisning {... props}/>);

        var children = wrapper.find('#mittIndreBarn');
        expect(children).to.have.length(1);
    });
});

