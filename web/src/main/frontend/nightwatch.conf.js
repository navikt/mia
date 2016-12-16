const argv = require('yargs')
    .option('url',  {
        type: 'string'
    })
    .option('t',  {
        alias: 'timeout',
        type: 'number'
    })
    .argv;

const chromedrivers = {
    'linux': './selenium/chromedriver',
    'win32': './selenium/chromedriver.exe',
    'win64': './selenium/chromedriver.exe'
};

const phantomjsdrivers = {
    'linux': './selenium/phantomjs',
    'win32': './selenium/phantomjs.exe',
    'win64': './selenium/phantomjs.exe'
};

module.exports = ((settings) => {
    settings.selenium.cli_args['webdriver.chrome.driver'] = chromedrivers[process.platform];
    settings.selenium.cli_args['phantomjs.binary.path'] = phantomjsdrivers[process.platform];

    if(argv.url) {
        settings['test_settings'].default['launch_url'] = argv.url;
    }

    if(argv.timeout) {
        settings['test_settings'].default.timeout = argv.timeout;
    }

    let seleniumPort = process.env['seleniumport'];
    settings.selenium.port = seleniumPort;
    settings['test_settings'].default['selenium_port'] = seleniumPort;
    
    return settings;
})(require('./nightwatch.json'));
