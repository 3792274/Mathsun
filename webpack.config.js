const webpack = require('webpack');
const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin'); // separate css
const CopyWebpackPlugin = require('copy-webpack-plugin');

const webpackConfig = module.exports = {}; // init object
const isProduction = process.env.NODE_ENV === 'production'; // production environment

const resolve = require('path').resolve
const url = require('url')

// input
webpackConfig.entry = {
    index: './vue/src/main.js'
};

// output
webpackConfig.output = {
    path: resolve(__dirname, './core/src/main/resources/static/dist'),
    publicPath: '/dist',
    filename: isProduction ? '[name].[hash].js' : '[name].js',
};

// loader
webpackConfig.module = {
    loaders: [
        { test: /\.css$/, loader: 'style-loader!css-loader' },
        { test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: "file" },
        { test: /\.(woff|woff2)$/, loader:"url?prefix=font/&limit=5000" },
        { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=application/octet-stream" },
        { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=image/svg+xml" }
    ],
    rules: [
        {
            test: /\.css$/,
            use: ['style-loader', 'css-loader', 'postcss-loader']
        },
        {
            test: /\.vue$/,
            use: ['vue-loader']
        },
        {
            test: /\.js$/,
            use: ['babel-loader'],
            exclude: /node_modules/
        },
        {
            test: /\.(png|jpg|jpeg|gif|eot|ttf|woff|woff2|svg|svgz)(\?.+)?$/,
            use: [{
                loader: 'url-loader',
                options: {
                    limit: 10000
                }
            }]
        },
    ]
};


webpackConfig.plugins = [
    new webpack.optimize.CommonsChunkPlugin({
        names: ['vendor', 'manifest']
    }),
    // make index.html
    new HtmlWebpackPlugin({
        template: './vue/src/index.html'
    }),
    // separate css file
    new ExtractTextPlugin({
        filename: isProduction ? 'app.[hash].css' : 'app.css',
        // disable: false,
        // allChunks: true
    }),
    new webpack.DefinePlugin({
        'process.env': {
            NODE_ENV: '"production"'
        }
    }),
    new CopyWebpackPlugin([
        { from: 'vue/src/mock/api.json', to: 'mock' },
        { context: 'vue/src/images', from: '*', to: path.join(__dirname, './core/src/main/resources/static/dist', 'images') }
    ])
];

webpackConfig.resolve ={
    alias: {
        '~': resolve(__dirname, 'vue/src')
    }
}

if (!isProduction) {
    webpackConfig.devServer = {
        contentBase: path.resolve(__dirname, './core/src/main/resources/static/dist'),
        compress: true,
        historyApiFallback: true,
    };
}
