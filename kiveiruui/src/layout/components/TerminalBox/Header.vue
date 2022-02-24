<template>
  <div class="terminal-header">
    <el-row class="terminal-header">
      <el-col :span="24">
        <div style="padding:10px;">
          <span id="terminal-title">X-SELL</span>
        </div>
      </el-col>
    </el-row>
  </div>

</template>

<script>
export default {
  name: "Header",
  data() {
    return {
    }
  },
}
</script>

<style scoped>
.terminal-header {
  width: 100%;
  height: 60px;
  background-color: #009688;
}

#terminal-title {
  text-align: center;
  letter-spacing:10px;
  font-size: 25px;
  color: #fff;
  font-family: Microsoft YaHei;
  font-weight: bold;
  font-style: italic;
}

</style>