const { JSDOM } = require('jsdom');
const request = require('request');
const fs = require('fs');

const getDecorator = (baseuri) =>
    new Promise((resolve, reject) => {
        const callback = (error, response, body) => {
            if (
                !error &&
                response.statusCode >= 200 &&
                response.statusCode < 400
            ) {
                const { document } = new JSDOM(body).window;
                const prop = 'innerHTML';

                const data = {
                    NAV_SCRIPTS: document.getElementById('scripts')[prop],
                    NAV_STYLES: document.getElementById('styles')[prop],
                    NAV_HEADING: document.getElementById('header')[prop],
                    NAV_FOOTER: document.getElementById('footer')[prop]
                };
                resolve(data);
            } else {
                console.log(error);
                reject(new Error(error));
            }
        };

        request(`${baseuri}/common-html/v4/navno?header=true&styles=true&scripts=true&footer=true`, callback);
    });

const inputPath = process.argv[2];
const outputPath = process.argv[3];
const appresUri = "https://appres.nav.no";
const templateFile = fs.readFileSync(inputPath, "utf-8");

getDecorator(appresUri)
    .then(decorator => templateFile
        .replace("{{decorator-scripts}}", decorator.NAV_SCRIPTS + "\n")
        .replace("{{decorator-styles}}", decorator.NAV_STYLES + "\n")
        .replace("{{decorator-header}}", decorator.NAV_HEADING + "\n")
        .replace("{{decorator-footer}}", decorator.NAV_FOOTER + "\n")
    )
    .then(outputFile => fs.writeFileSync(outputPath, outputFile, "utf-8"));
