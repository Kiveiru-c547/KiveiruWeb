
function realconsole(){
    alert("hello.thanks use me");
}

function getHttp() {
    const request = require('request');
    request('http://localhost:9876/fanbox', (error, response, body)=>{
        console.log('error', error);
        console.log('statusCode', response && response.statusCode);
        console.log('body', body)
    })
}

export {
    realconsole,
    getHttp
}