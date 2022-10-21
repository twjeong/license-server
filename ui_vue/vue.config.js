const fs = require('fs')

module.exports = {
  transpileDependencies: [
    'vuetify',
    'vuex-persist'
  ],
  pages: {
    index: {
      entry: 'src/main.js',
      // template title tag needs to be <title><%= htmlWebpackPlugin.options.title %></title>
      title: 'Policy Server',
    },
  },
  runtimeCompiler: true,
  publicPath: './',
  outputDir: './dist',
  indexPath: './index.html',
  devServer: {
      https: true,
      https: {
        key: fs.readFileSync('./domain.com.key'),
        cert: fs.readFileSync('./domain.com.crt')
      },
      compress: true,
      disableHostCheck: true,
      host: '0.0.0.0',
      public : '0.0.0.0:8443',
      port: 8443,
      proxy: {
        '/license-service': {
          target: 'https://localhost:8081',
          //target: 'https://gateway:8081',
          changeOrigin: true,
          secure: false
        }
      },
      overlay: false
  },
  chainWebpack: config => {
    const svgRule = config.module.rule('svg')
    config.module.rules.delete('eslint')
    svgRule.uses.clear()
    
    svgRule
      .use('babel-loader')
      .loader('babel-loader')
      .end()
      .use('vue-svg-loader')
      .loader('vue-svg-loader')
  }
};
