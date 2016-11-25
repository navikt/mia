import React from 'react';
import chai from 'chai';
import sinon from 'sinon';
import sinonChai from 'sinon-chai';
import chaiEnzyme from 'chai-enzyme';
import TestUtils from 'react-addons-test-utils';
import _ from './setup'; // eslint-disable-line no-unused-vars

const { assert, expect } = chai;

chai.should();
chai.use(sinonChai);
chai.use(chaiEnzyme());

export {
    React,
    chai,
    sinon,
    sinonChai,
    assert,
    expect,
    TestUtils
};
