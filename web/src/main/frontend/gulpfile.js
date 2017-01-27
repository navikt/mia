const gulp = require('gulp');
const gutil = require('gulp-util');
const pathExistst = require('path-exists');

const constants = require('./gulp/constants');
const OUTPUT_DIRECTORY = constants.OUTPUT_DIRECTORY;

function copyImage() {
    if (isProduction()) {
        gulp.start('copy-img');
    } else {
        pathExistst(OUTPUT_DIRECTORY + 'img/').then(function (exists) {
            if (!exists) {
                gulp.start('copy-img');
            }
        });
    }
}

const isProduction = () => process.env.NODE_ENV === 'production';
process.env.NODE_ENV = gutil.env.prod != null ? 'production' : 'development'; //eslint-disable-line no-eq-null, eqeqeq

gulp.task('build-js', require('./gulp/build-js').buildJs(gulp));
gulp.task('build-less', require('./gulp/build-less')(gulp));
gulp.task('build-js-watchify', require('./gulp/build-js').buildJsWatchify(gulp));
gulp.task('build-html', require('./gulp/build-html')(gulp));
gulp.task('build-vendors', require('./gulp/build-js').buildVendors(gulp));
gulp.task('eslint', require('./gulp/eslint')(gulp));
gulp.task('copy-img', require('./gulp/copy-img').copyImg(gulp));
gulp.task('clean-img', require('./gulp/copy-img').cleanImg());
gulp.task('build-properties', ['build-js'], require('./gulp/build-properties').buildProperties())

gulp.task('build', ['clean'], function () {
    gulp.start(['build-js', 'build-vendors', 'build-less', 'build-properties', 'build-html']);
    copyImage();
});

gulp.task('watch', ['clean'], function () {
    gulp.start(['build-js-watchify', 'build-vendors', 'build-less', 'build-properties', 'build-html', 'copy-img']);
    gulp.watch('./css/*.less', ['build-less']);
    copyImage();
});

gulp.task('clean', function (callback) {
    const del = require('del');
    return del([
        OUTPUT_DIRECTORY + 'js/',
        './messages/js',
        OUTPUT_DIRECTORY + 'css/',
        OUTPUT_DIRECTORY + 'index.html'
    ], {'force': true}, callback);
});

gulp.task('default', ['clean'], function () {
    gutil.log("-------- Start building for " + (isProduction() ? "production" : "development"));
    gulp.start('build');
});