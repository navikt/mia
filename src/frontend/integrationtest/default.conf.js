
const config = {
    src_folders: ["integrationtest/tests"],
    page_objects_path: ["integrationtest/pages"],
    output_folder: "integrationtest/reports",
    custom_commands_path: "integrationtest/commands",
    custom_assertions_path: "",
    globals_path: "globals.js",

    selenium: {
        start_process: false,
        port: 9515,
        host: 'localhost'
    },

    test_settings: {
        default: {
            globals: {
                launch_url: 'http://localhost:3000',
                timeout: 20000,
            },
            default_path_prefix: '',
            timeout: 15000,
            desiredCapabilities: {
                browserName: "chrome",
                javascriptEnabled: true,
                acceptSslCerts: true,
                keepAlive: false,
                chromeOptions: {
                    args: ['--no-sandbox', '--disable-gpu', '--log-level=3'],
                },
            }
        },
    }
};

module.exports = config;
