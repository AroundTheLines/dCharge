module.exports = {
	entry: "./public/static/js/main.js",
	output: {
		filename: "./public/static/dist/bundle.js"
	},
	module: {
		loaders: [
			{
				exclude: /(node_modules | appTest.js)/,
				loader: 'babel',
				query:{
					presets: ["es2015", "react"]
				}
			}
		]
	}
};
