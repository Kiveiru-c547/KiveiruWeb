<template>
  <el-card id="xterm-box" body-style="padding:3px;height:100%">
    <div :id='divId' class="xterm" />
  </el-card>

</template>

<script>
import "xterm/css/xterm.css";
import "xterm/lib/xterm.js";
// FitAddon: 窗口自适应插件
import { Terminal } from "xterm";
import { FitAddon } from 'xterm-addon-fit';

export default {
  name:'XTerm',
  props: {
    divId: String,
    node: Object,
  },
  data() {
    return {
      userId: ""
    };
  },
  methods: {
    initXterm() {
      this.term = new Terminal({
        rendererType: "canvas", //渲染类型
        cols: 170,
        rows: 47, //行数
        convertEol: true, //启用时，光标将设置为下一行的开头
        scrollback: 10, //终端中的回滚量
        disableStdin: false, //是否应禁用输入
        cursorStyle: "underline", //光标样式
        cursorBlink: true, //光标闪烁
        theme: {
          foreground: "#009688", //字体
          background: "#060101", //背景色
          cursor: "help" //设置光标
        }
      });
      this.term.open(document.getElementById(this.divId));

      // 终端窗口自适应插件
      const fitAddon = new FitAddon();
      this.term.loadAddon(fitAddon);
      fitAddon.fit();

      // 初始化高度
      window.addEventListener("resize", () => {
        try {
          fitAddon.fit();
        } catch (e) {}
      });
      // 支持输入与粘贴方法
      // let _this = this; //一定要重新定义一个this，不然this指向会出问题
      // this.term.onData(function(key) {
      //   // let order = ["stdin",key]; //这里key值是你输入的值，数据格式一定要找后端要！！！！
      //   _this.socket.send(JSON.stringify({type:"command", data: key})); //转换为字符串
      // });
    },
    init(url) {
      // 实例化socket
      this.socket = new WebSocket(url);
      // 监听socket连接
      this.socket.onopen = this.open;
      // 监听socket错误信息
      this.socket.onerror = this.error;
      // 监听socket消息
      this.socket.onmessage = this.getMessage;
      // 发送socket消息
      this.socket.onsend = this.send;
    },
    open: function() {
      console.log("socket连接成功");
      this.initXterm();
      // 发送连接服务器指令
      this.socket.send(JSON.stringify({
        operate: 'connect',
        host: this.node.config.host, // IP
        port: this.node.config.port, // 端口号
        username: this.node.config.user, // 用户名
        password: this.node.config.password, // 密码
        savePassword: false    //tabInfo.savePassword
      }))
      //this.socket.onsend(JSON.stringify({type:"connect",data: this.node.id })); //转换为字符串
    },
    error: function(e) {
      console.log("连接错误");
      this.$message.error(e);
    },
    close: function() {
      this.socket.close();
      console.log("socket已经关闭");
    },
    getMessage: function(msg) {
      //this.term.write(msg.data);
      let data = JSON.parse(msg.data);
      // 连接成功消息
      if (data.type === 'WEBSSH_MESSAGE_TYPE_CONNECT') {
        this.userId = data.userId;
        this.$emit('setUserId', data.userId);
        // 当在终端输入信息后发送到后端
        this.term.onData(data => {
          this.socket.send(JSON.stringify({'operate': 'command', 'command': data}))
        })
      } else if (data.type === 'WEBSSH_MESSAGE_TYPE_COMMAND') {
        // 后端返回的信息显示到终端
        this.term.write(data.data);
      } else if (data.type === 'WEBSSH_MESSAGE_TYPE_ERROR') {
        // 提示错误信息
        this.$message.error(data.data);
      }
    },
    send: function(order) {
      this.socket.send(order);
    },
  },
  mounted() {
    let url = "ws://" + location.host + "/socket/webssh";
    this.init(url);
  },
};
</script>

<style scoped>
#xterm-box {
  margin: 1%;
  height: 95%;
  background-color: black;
}
.xterm {
  height: 100%;
}
</style>