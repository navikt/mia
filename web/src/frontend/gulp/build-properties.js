const constants = require('./constants');
const OUTPUT_PROPERTIES = constants.OUTPUT_PROPERTIES;

function readPropertiesFromFile(name) {
    const fs = require('fs');
    const content = fs.readFileSync(name, 'utf8');
    const fileObject = {};

    content.split('\n').map(row => row.split('=')).filter(rowArray => rowArray.length > 1).forEach(rowArray => {
        fileObject[rowArray[0]] = rowArray.slice(1, rowArray.length).join('=');
    });

    return fileObject;
}

function mergeProperties(fileObject, compiledObject) {
    const mergedObject = {};
    Object.keys(compiledObject).forEach(key => {
        mergedObject[key] = fileObject.hasOwnProperty(key) ? fileObject[key] : compiledObject[key];
    });
    return mergedObject;
}

function writeToFile(name, messages) {
    const fs = require('fs');

    fs.writeFileSync(name, '', {encoding: 'utf-8'});
    Object.keys(messages).sort().forEach(function (key) {
        fs.appendFileSync(name, key + "=" + messages[key] + '\n', {encoding: 'utf-8'});
    });
}

function writePropertiesToFile() {
    const fse = require('fs-extra');
    const file = require('file');

    var messages = {};
    fse.ensureDirSync('./messages/js');
    file.walkSync('./messages/js',
        function (dir, dirs, files) {
            var res = files.reduce((prev, cfile) => {
                var json = require('../' + dir + '/' + cfile);
                return Object.assign({}, prev, json.reduce((res, message) => {
                    var result = {};
                    result[message.id] = message.defaultMessage;
                    return Object.assign({}, res, result);
                }, {}));
            }, {});
            messages = Object.assign({}, messages, res);
        });

    const fileMessages = readPropertiesFromFile(OUTPUT_PROPERTIES);
    writeToFile(OUTPUT_PROPERTIES, mergeProperties(fileMessages, messages));
}

module.exports = {
    buildProperties: () => writePropertiesToFile
};