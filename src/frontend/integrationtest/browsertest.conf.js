
let nightwatch_config = {
    src_folders: ["integrationtest/tests"],
    page_objects_path: ["integrationtest/pages"],
    output_folder: "integrationtest/reports",
    custom_commands_path: "integrationtest/commands",
    custom_assertions_path: "",
    globals_path: "./integrationtest/globals.js",
    output: true,

    selenium: {
        start_process: false,
        host: 'hub-cloud.browserstack.com',
        port: 80,
    },

    common_capabilities: {
        'browserstack.debug': true,
        'browserstack.local': true,
        project: 'mia',
    },

    test_settings: {
        default: {
            globals: {
                launch_url: '',
                timeout: 40000,
            },
            screenshots: {
                enabled: true,
                on_failure: true,
                on_error: true,
                path: 'test/nightwatch-tester/reports',
            },
        },
        win_chrome: {
            desiredCapabilities: {
                os: 'Windows',
                os_version: '10',
                browser: 'Chrome',
                resolution: '1024x768',
            },
        },
        win_ie: {
            desiredCapabilities: {
                os: 'Windows',
                os_version: '10',
                browser: 'IE',
                browser_version: '11.0',
                resolution: '1024x768',
            },
        },
        win_edge: {
            desiredCapabilities: {
                os: 'Windows',
                os_version: '10',
                browser: 'Edge',
                resolution: '1024x768',
            },
        },
        ios_safari: {
            desiredCapabilities: {
                device: 'iPhone X',
                realMobile: 'true',
                os_version: '11.0',
            },
        },
    },
};

// Code to support common capabilites
nightwatch_config.common_capabilities['browserstack.user'] =
    process.env.BROWSERSTACK_USER;
nightwatch_config.common_capabilities['browserstack.key'] =
    process.env.BROWSERSTACK_KEY;

const branch = 'Local';
nightwatch_config.common_capabilities['build'] = branch;

for (let i in nightwatch_config.test_settings) {
    let config = nightwatch_config.test_settings[i];
    config['selenium_host'] = nightwatch_config.selenium.host;
    config['selenium_port'] = nightwatch_config.selenium.port;
    config['desiredCapabilities'] = config['desiredCapabilities'] || {};

    for (let j in nightwatch_config.common_capabilities) {
        config['desiredCapabilities'][j] =
            config['desiredCapabilities'][j] ||
            nightwatch_config.common_capabilities[j];
    }
}

module.exports = nightwatch_config;
