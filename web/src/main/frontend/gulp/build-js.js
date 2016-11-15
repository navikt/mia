const constants = require('./constants');
const OUTPUT_DIRECTORY = constants.OUTPUT_DIRECTORY;

const onError = function (err) {
    const gutil = require('gulp-util');
    gutil.beep();
    gutil.log("Error: [line " + err.lineNumber + "] " + err.message);
};

const isProduction = () => process.env.NODE_ENV === 'production';
const isDevelopment = () => process.env.NODE_ENV !== 'production';

function getExternalVendors() {
    return Object.keys(require('../package.json').dependencies);
}

const babelifyReact = function (file) {
    const babelify = require('babelify');
    return babelify(file);
};

function bundle(gulp, bundle, bundleFileName) {
    const gulpif = require('gulp-if');
    const buffer = require('vinyl-buffer');
    const uglify = require('gulp-uglify');
    const source = require('vinyl-source-stream');

    return bundle
        .bundle()
        .on('error', function (err) {
            onError(err);
            this.emit('end');
        })
        .pipe(source(bundleFileName))
        .pipe(gulpif(isProduction(), buffer()))
        .pipe(gulpif(isProduction(), uglify())).on('error', function (error) {
            onError(error);
            process.exit(1);
        })
        .pipe(gulp.dest(OUTPUT_DIRECTORY + 'js/'));
}

function buildJs(gulp) {
    return () => {
        const browserify = require('browserify');
        var bundler = browserify('./js/bootstrap.jsx', {
            debug: isDevelopment(),
            extensions: ['.js', '.jsx'],
            fullPaths: isDevelopment()
        }).external(getExternalVendors());
        return bundle(gulp, bundler.transform(babelifyReact), 'bundle.js');
    };
}

function buildVendors(gulp) {
    return () => {
        const browserify = require('browserify');
        const babelify = require('babelify');

        var bundler = browserify({
            debug: isDevelopment(),
            fullPaths: isDevelopment()
        }).require(getExternalVendors()).transform(babelify);
        return bundle(gulp, bundler, 'vendors.js');
    };
}

function buildJsWatchify(gulp) {
    return () => {
        const watchify = require('watchify');
        const browserify = require('browserify');
        const gutil = require('gulp-util');

        const browserifyOpts = {
            debug: isDevelopment(),
            entries: './js/bootstrap.jsx',
            extensions: ['.js', '.jsx'],
            cache: {},
            packageCache: {},
            fullPaths: isDevelopment()
        };

        var opts = Object.assign({}, watchify.args, browserifyOpts);
        var bundler = watchify(browserify(opts).external(getExternalVendors())).transform(babelifyReact);

        bundler.on('update', function () {
            gutil.log('Starting', gutil.colors.cyan("'watchify rebundle'"), '...');
            var start = new Date();

            return bundle(gulp, bundler, 'bundle.js').on('end', function () {
                var time = parseFloat((new Date() - start) / 1000).toFixed(2);
                gutil.log('Finished', gutil.colors.cyan("'watchify rebundle'"), 'after', gutil.colors.magenta(time + ' s'));
            });
        });

        return bundle(gulp, bundler, 'bundle.js');
    };
}

module.exports = {
    buildJsWatchify: (gulp) => buildJsWatchify(gulp),
    buildVendors: (gulp) => buildVendors(gulp),
    buildJs: (gulp) => buildJs(gulp)
};