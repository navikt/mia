const OUTPUT_DIRECTORY = require('./constants').OUTPUT_DIRECTORY;

function buildHtml(gulp) {
    const replace = require('gulp-replace');
    const timestamp = Date.now();

    return () => {
        return gulp.src('./index.html')
            .pipe(replace("{timestamp}", timestamp))
            .pipe(gulp.dest(OUTPUT_DIRECTORY));
    };
}

module.exports = buildHtml;