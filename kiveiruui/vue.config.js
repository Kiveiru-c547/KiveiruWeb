const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}

// 跨域配置
module.exports = {
    devServer: {
        port: 9876,
        proxy: {                                    //设置代理
            '/api': {                               //设置拦截器 拦截器格式 斜杠+拦截器名字
                target: 'http://localhost:8181',    //代理的目标地址
                changeOrigin: true,                 //是否设置同源
                //ws: true,
                pathRewrite: {                      //路径重写
                    '/api': ''                      //选择忽略拦截器里面的单词
                }
            },
            '/socket': {
                target: 'ws://localhost:8181',//后端目标接口地址
                changeOrigin: true,//是否允许跨域
                pathRewrite: {
                    '^/socket': '',//重写,
                },
                ws: true //开启ws, 如果是http代理此处可以不用设置
            },
            'fanbox': {
                target: 'https://camonome.fanbox.cc/',
                changeOrigin: true,
                pathRewrite: {
                    '/fanbox': ''
                }
            }
        }
    },
    configureWebpack: {
        module: {
            rules: [{
                test: /\.mjs$/,
                include: /node_modules/,
                type: 'javascript/auto'
            },
            {
                test:/.(jpg|gif|svg|jfif)$/,
                loader: 'url-loader',
                // exclude: [resolve('src/icons')],
                // options: {
                //     limit: 10000,
                // }
            }]
        }
    }
}