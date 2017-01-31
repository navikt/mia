const OUTPUT_DIRECTORY = require('./constants').OUTPUT_DIRECTORY;

const isProduction = () => process.env.NODE_ENV === 'production';

function buildLess(gulp) {
    return () => {
        const less = require('gulp-less');
        const gulpif = require('gulp-if');
        const autoprefixer = require('gulp-autoprefixer');
        const gutil = require('gulp-util');
        const cleanCSS = require('gulp-clean-css');

        return gulp.src('./css/main.less')
            .pipe(less().on('error', function(err){
                gutil.log(`error in less: ${err.message}`);
                this.emit('end');
            }))
            .pipe(autoprefixer({browsers: ['last 2 versions'], cascade: false}))
            .pipe(gulpif(isProduction(), cleanCSS({ compatibility: 'ie11' })))
            .pipe(gulp.dest(OUTPUT_DIRECTORY + 'css/'));
    };
}

module.exports = buildLess;
