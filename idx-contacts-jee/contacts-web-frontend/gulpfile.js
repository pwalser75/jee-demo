'use strict';

const gulp = require('gulp'); 
const clean = require('gulp-clean');
const notify = require('gulp-notify');
const rename = require('gulp-rename');
const jshint = require('gulp-jshint');
const uglify = require('gulp-uglify');
const concat = require('gulp-concat');
const autoprefixer = require('gulp-autoprefixer');
const sass = require('gulp-sass');
const sourcemaps = require('gulp-sourcemaps'); 
const babelify = require('babelify'); 
const browserify = require('browserify');
const watchify = require('watchify');
const merge = require('utils-merge'); 
const source = require('vinyl-source-stream'); 
const buffer = require('vinyl-buffer');
const concatcss = require('gulp-concat-css');
const browserSync = require('browser-sync').create();
const url = require('url');
const proxy = require('proxy-middleware');

// Configuration

const config = {
	source: './src/main/frontend',
	target: './build/frontend',
	javascript: {
		source: 'index.js',
		target: 'build.js'
	},
	css: {
		target: 'style.css'
	},
	proxy: {
		url: 'http://localhost:8080/contacts/api',
		route: '/api'
	},
	filetypes: {
		javascript: ['js','json'],
		stylesheet: ['css', 'scss'],
		resources:  ['html','jpg','png','svg','woff','woff2']
	}
};

// Build functions

function fileTypeMatcher(fileSuffixArray) {
	return fileSuffixArray.map(type=> config.source+'/**/*.'+type);
}

function cleanTarget() {
	return gulp.src(config.target, {read: false}).pipe(clean());
}

function copyStatic() {
	return gulp.src(fileTypeMatcher(config.filetypes.resources))
		.pipe(gulp.dest(config.target));
}

function compileStylesheets() {
  return gulp.src(fileTypeMatcher(config.filetypes.stylesheet))
    .pipe(sass().on('error', sass.logError))
	.pipe(autoprefixer())
	.pipe(concatcss(config.css.target))
    .pipe(gulp.dest(config.target));
}

function bundleJavascript() {
	
	var args = merge(watchify.args, { debug: true });
  
	var bundler = browserify(config.source+'/'+config.javascript.source, args)
	             .transform(babelify, {presets: ['es2015', 'react']});

	return bundler
		.bundle()
		.pipe(source(config.javascript.source)) 
		.pipe(jshint())
		.pipe(jshint.reporter('default'))
		.pipe(buffer())
		.pipe(rename(config.javascript.target))
		.pipe(sourcemaps.init({loadMaps: true})) 
		.pipe(uglify())
		.pipe(sourcemaps.write('./map'))
		.pipe(gulp.dest(config.target)); 
}

// build targets

gulp.task('clean', function() { return cleanTarget(); });

gulp.task('build', ['clean'], function(){
	copyStatic();
	compileStylesheets();
	bundleJavascript();
});

gulp.task('watch', ['build'], function() { 
	
	gulp.watch(fileTypeMatcher(config.filetypes.resources),function() {
		copyStatic()
			.pipe(browserSync.stream())
			.pipe(notify({message: 'Updated resources', onLast: true }));
	});
	gulp.watch(fileTypeMatcher(config.filetypes.stylesheet),function() {
		compileStylesheets()
			.pipe(browserSync.stream())
			.pipe(notify({message: 'Updated stylesheets', onLast: true }));
	});
	gulp.watch(fileTypeMatcher(config.filetypes.javascript),function() {
		bundleJavascript()
			.pipe(notify({message: 'Updated sources', onLast: true }));
	});
});

gulp.task('server', ['watch'], function() {
	
	var proxyOptions = url.parse(config.proxy.url);
    proxyOptions.route = config.proxy.route;

	browserSync.init({
		server: {
			baseDir: config.target,
			middleware: [proxy(proxyOptions)]
		}
	});
});

gulp.task('default', ['server']);
