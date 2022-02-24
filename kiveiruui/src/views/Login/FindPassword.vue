<template>
  <div class="container">
    <div>
      <div class="breadcrumb-box">
        <el-breadcrumb>
          <el-breadcrumb-item :to="{ path: '/login' }">登录</el-breadcrumb-item>
          <el-breadcrumb-item>忘记密码</el-breadcrumb-item>
        </el-breadcrumb>
      </div>
      <div class="container">

      </div>
      <div class="container">

      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FindPassword',
}
</script>

<style scoped>
.container {
  width: 980px;
  margin: 0 auto;
}

.breadcrumb-box {
  padding-top: 24px;
}
</style>