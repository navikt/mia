const gulp = require('gulp');
const gutil = require('gulp-util');

const constants = require('./gulp/constants');
const OUTPUT_DIRECTORY = constants.OUTPUT_DIRECTORY;

const isProduction = () => process.env.NODE_ENV === 'production';
const isDevelopment = () => process.env.NODE_ENV !== 'production';

gulp.task('build-js', require('./gulp/build-js').buildJs(gulp));
gulp.task('build-js-watchify', require('./gulp/build-js').buildJsWatchify(gulp));
gulp.task('build-vendors', require('./gulp/build-js').buildVendors(gulp));

gulp.task('build', ['clean'], function () {
    gulp.start(['build-js', 'build-vendors']);
});

gulp.task('clean', function (callback) {
    const del = require('del');
    return del([
        OUTPUT_DIRECTORY + 'js/',
        OUTPUT_DIRECTORY + 'css/'
    ], {'force': true}, callback);
});

gulp.task('default', ['clean'], function () {
    gutil.log("-------- Start building for " + (isProduction() ? "production" : "development"));
    gulp.start('build');
});