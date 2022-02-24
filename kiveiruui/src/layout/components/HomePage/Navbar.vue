<template>
  <nav>
    <div class="inner">
      <a href="/" class="main-nav-item">首页</a>
      <a href="/account/home" class="main-nav-item">学无止境</a>
      <a href="/test" class="main-nav-item">不亦乐乎</a>
      <a href="/space" class="main-nav-item">工具站</a>
      <a href="/terminaltest" class="main-nav-item">测试页</a>
      <el-button @click="handleLogout">登出</el-button>
      <div class="right-entry">
        <el-popover
            :width="359"
            :show-arrow="false"
            popper-style="box-shadow: rgb(14 18 22 / 35%) 0px 10px 38px -10px, rgb(14 18 22 / 20%) 0px 10px 20px -15px; padding: 20px;"
        >
          <template #reference>
            <div class="login-btn" @click="clickLoginBtn">
              <div class="header-login-entry">
                <span>登录</span>
              </div>
            </div>
          </template>
          <template #default>
            <div class="login-panel-popover">
              <p class="title">登录后你可以：</p>
              <div class="login-tip-content">
                <div class="login-tip-content-item">aaaaa</div>
                <div class="login-tip-content-item">bbbbb</div>
                <div class="login-tip-content-item">ccccc</div>
                <div class="login-tip-content-item">ddddd</div>
              </div>
              <a class="goto-login" href="/login">
                登
                <b style="width:22px; display:inline-block"></b>
                录
              </a>
              <div class="register-tip">
                注册请戳这里➡
                <span class="register-exper">点我注册</span>
              </div>
            </div>
          </template>
        </el-popover>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
  methods: {
    clickLoginBtn() {
      this.$router.push({ path: '/login' });
    },
    handleLogout() {
      this.$store.dispatch("logout").then(() => {
        this.$router.push({ path: '/login' });
      }).catch((error) => {
        if (error !== 'error') {
          this.$message({
            message: error,
            type: 'error',
            showClose: true
          });
        }
      });
    }
  }
}
</script>

<style scoped>
.main-nav {
  position: absolute !important;
  width: 100%;
  height: 61px;
  z-index: 3;
}

nav.shown {
  background-color: ghostwhite;
}

nav.fixed {
  position: static;
  background-color: ghostwhite;
}

div.inner {
  position: relative;
  width: 100%;
  max-width: 980px;
  height: 100%;
  padding: 0 20px;
  margin: 0 auto;
}

.main-nav-item {
  display: inline-block;
  padding: 20px 24px;
  text-decoration: none;
  color: gray;
  font-weight: bold;
}

.main-nav-item:hover {
  color: rgb(0, 207, 254);
}

.right-entry {
  display: inline-block;
  position: absolute;
  right: 0;
  width: auto;
  height: 61px;
}

.login-btn {
  display: inline-block;
  position: absolute;
  align-items: center;
  text-align: center;
  padding: 12px 12px;
  cursor: pointer;
}

.header-login-entry {
  width: 37px;
  height: 37px;
  border-radius: 50%;
  background: #f6f6f6;
  text-align: center;
  color: plum;
  font-size: 14px;
  font-weight: bold;
  line-height: 37px;
}

.login-panel-popover {
  border-radius: 8px;
  text-align: left;
}

.login-panel-popover .title {
  margin-bottom: 15px;
  color: black;
  font-size: 14px;
  line-height: 20px;
}

.login-tip-content {
  display: flex;
  flex-wrap: wrap;
}

.login-tip-content-item {
  display: flex;
  align-items: center;
  margin-bottom: 14px;
  width: 50%;
  height: 26px;
}

.goto-login {
  display: block;
  padding: 10px 0;
  height: 40px;
  box-sizing: border-box;
  border-radius: 8px;
  background-color: #00a1d7;
  color: white;
  text-align: center;
  letter-spacing: 0;
  font-size: 14px;
  line-height: 20px;
  text-decoration: none;
  cursor: pointer;
  transition: background-color .3s;
}

.goto-login:hover {
  background-color: #008ab4;
}

.register-tip {
  margin-top: 16px;
  color: black;
  text-align: center;
  letter-spacing: 0;
  font-size: 14px;
  line-height: 20px;
}

.register-exper {
  margin-left: 24px;
  color: deepskyblue;
  cursor: pointer;
}
</style>