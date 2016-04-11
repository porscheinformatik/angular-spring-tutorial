// - modules

var path = require('path'),
    gulp = require('gulp'),
    tsc = require('gulp-typescript'),
    tslint = require('gulp-tslint'),
    concat = require('gulp-concat'),
    jshint = require('gulp-jshint'),
    uglify = require('gulp-uglify'),
    sass = require('gulp-sass'),
    angularFilesort = require('gulp-angular-filesort'),
    sourcemaps = require('gulp-sourcemaps'),
    util = require('gulp-util');

// - config

var npmPath = 'node_modules/';
var srcDir = './src/main/resources/';
var conf = {
    paths: {
        vendorjs: ['node_modules/angular/angular.js',
            'node_modules/angular-ui-router/release/angular-ui-router.min.js'],
        styles: srcDir + 'scss/*.scss',
        dist: './target/classes/static/assets/'
    },
    production: !!util.env.production
};

var tsProject = tsc.createProject('tsconfig.json'),
    tsGlob = tsProject.config.compilerOptions.rootDir + '**/*.ts';

// - tasks
gulp.task('ts-lint', function() {
    return gulp.src(tsGlob)
        .pipe(tslint())
        .pipe(tslint.report('prose'), {
            emitError: false
        });
});

gulp.task('ts-compile', function() {
    return gulp.src(tsGlob)
        .pipe(tsc(tsProject))
        .pipe(angularFilesort())
        .pipe(concat('app.min.js'))
        .pipe(gulp.dest(conf.paths.dist));
});

gulp.task('ts-testcompile', function() {
    gulp.src('src/test/typescript/**/*.ts')
        .pipe(tsc())
        .pipe(gulp.dest('target/typescript/'));
});


gulp.task('scripts-vendor', function () {
    return gulp.src(conf.paths.vendorjs)
        .pipe(sourcemaps.init({loadMaps: true}))
        .pipe(concat('vendor.min.js'))
        .pipe(conf.production ? util.noop() : sourcemaps.write())
        .pipe(gulp.dest(conf.paths.dist));
});

gulp.task('style', function () {
    return gulp.src(conf.paths.styles)
        .pipe(sourcemaps.init())
        .pipe(sass({ includePaths : npmPath, outputStyle: 'compressed' }).on('error', sass.logError))
        .pipe(conf.production ? util.noop() : sourcemaps.write())
        .pipe(gulp.dest(conf.paths.dist));
});

gulp.task('test', ['scripts-vendor', 'ts-testcompile', 'ts-compile'], function (done) {
    var Server = require('karma').Server;
    new Server({
        configFile: __dirname + '/src/test/karma.conf.js',
        browsers: ['Firefox'],
        singleRun: true
    }, done).start();
});

gulp.task('default', ['style', 'scripts-vendor', 'ts-lint', 'ts-compile']);

gulp.task('watch', ['default'], function () {
    gulp.watch(tsGlob, ['ts-compile']);
    gulp.watch(srcDir + 'scss/**', ['style']);
});
