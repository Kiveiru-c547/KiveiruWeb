<template>
  <div>
    <h1>
      Spider Page
    </h1>
    <div style="width: 100%;height: 100px">
      <div class="step">访问服务器</div>
      <div class="step">下载页面</div>
      <div class="step">分析页面</div>
      <div class="step">提取信息</div>
      <div class="step">保存数据</div>
    </div>
    <el-button @click="click" style="margin-top: 50px">
      点我获取http请求
    </el-button>
  </div>
</template>

<script>
import * as Spider from "@/views/Spider/spider";

export default {
  methods: {
    click() {
      Spider.getHttp();
    }
  }
}
</script>

<style scoped>
.step {
  width: 100px;
  height: 100px;
  background-color: coral;
  text-align: center;
  float: left;
  margin-left: 150px;
}
</style>