const CracoLessPlugin = require('craco-less');
const NpmImportPlugin = require('less-plugin-npm-import');

module.exports = {
    plugins: [
        { plugin: CracoLessPlugin,
            options: {
                lessLoaderOptions: {
                    loader: new NpmImportPlugin({ prefix: '~' })
                }
            }
        }
    ]
};
