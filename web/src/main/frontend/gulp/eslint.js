function buildEslint(gulp) {
    return () => {
        const eslint = require('gulp-eslint');
        return gulp.src(['./js/**/*.{js,jsx}'])
            .pipe(eslint())
            .pipe(eslint.format())
            .pipe(eslint.failAfterError());
    };
}

module.exports = buildEslint;
