<template>
<div id="login-app">
  <div>
    <div class="title-line">
      <span class="title">登录</span>
    </div>
    <div class="login-box">
      <div class="login-left"></div>
      <div class="line"></div>
      <div class="login-right">
        <div class="input-box">
          <div id="form-login">
          <el-tabs v-model="activeName">
            <el-tab-pane label="密码登录" name="passwd">
              <el-form ref="loginForm" :model="form" :rules="rules">
                <el-form-item class="login-input" prop="account">
                  <el-input v-model="form.account" placeholder="你的手机号/邮箱"></el-input>
                </el-form-item>
                <el-form-item class="login-input" prop="password">
                  <el-input v-model="form.password" type="password" show-password placeholder="密码"></el-input>
                </el-form-item>
                <div class="remember">
                  <el-checkbox v-model="rememberPasswd" label="记住我" size="large"></el-checkbox>
                  <span class="remember-tab">不是自己的电脑上不要勾选此项</span>
                  <a href="/findpassword">忘记密码？</a>
                </div>
              </el-form>
              <div class="login-button-group">
                <el-button type="primary" @click="handleLogin">登录</el-button>
                <el-button>注册</el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane label="短信登录" name="message">
              短信登录
            </el-tab-pane>
          </el-tabs>
        </div>
        </div>
      </div>
    </div>
  </div>
</div>
</template>

<script>
import Cookies from "js-cookie";
import { login, getUserInfo } from "@/api/login";

export default {
  name: "Login",
  data() {
    return {
      activeName: 'passwd',
      form: {
        account: '',
        password: ''
      },
      rules: {
        account: [{
          required: true,
          message: '请告诉我注册时用的邮箱或者手机号哟~',
          trigger: 'blur',
        }],
        password: [{
          required: true,
          message: '密码也请告诉我੭ ᐕ)੭*⁾⁾',
          trigger: 'blur',
        }]
      },
      rememberPasswd: false
    }
  },
  methods: {
    handleLogin() {
      if (this.rememberPasswd) {
        Cookies.set("account", this.form.account, { expires: 30});
        Cookies.set("password", this.form.password, { expires: 30});
        Cookies.set("rememberPasswd", this.rememberPasswd, { expires: 30});
      } else {
        Cookies.remove("account", this.form.account, { expires: 30});
        Cookies.remove("password", this.form.password, { expires: 30});
        Cookies.remove("rememberPasswd", this.rememberPasswd, { expires: 30});
      }
      console.log(this.form)
      this.$store.dispatch('login', this.form).then(() => {
        this.$router.push({ path:'/' });
      }).catch((error) => {
        this.$message({
          message: error,
          type: 'error',
          showClose: true
        });
      });
    }
  }
}
</script>

<style>
.input-box {
  margin-top: 50px;
}

.title-line {
  width: 980px;
  height: 28px;
  margin: 0 auto 28px auto;
  border-bottom: 1px solid #ddd;
  text-align: center;
}

.title-line .title {
  height: 56px;
  line-height: 56px;
  margin: 0 auto;
  padding: 0 20px;
  background: #fff;
  text-align: center;
  font-size: 38px;
}

.login-box {
  position: relative;
  width: 980px;
  margin: 0 auto;
  display: flex;
}

.login-left {
  width: 489px;
}

.login-box .line {
  border-right: 1px solid #ddd;
  height: 300px;
  margin-top: 28px;
}

.login-right {
  padding-left: 45px;
  width: 490px;
  box-sizing: border-box;
}

.login-input {
  margin-bottom: 24px;
}

.login-input .el-input__inner {
  height: 40px;
}

.login-input.is-error .el-input__inner {
  border-color: deepskyblue;
}

.login-input .el-form-item__error {
  padding: 0;
  margin: 6px 0;
  font-size: 12px;
  color: deepskyblue;
}

.remember {
  display: flex;
  text-align: center;
  align-items: center;
}

.remember .el-checkbox--large .el-checkbox__label {
  font-size: 12px;
}

.remember .remember-tab {
  margin: 0 0 0 10px;
  font-size: 12px;
  color: #bbb;
}

.remember a {
  margin-left: auto;
  font-size: 12px;
  color: deepskyblue;
  text-decoration: none;
}

.login-button-group {
  display: flex;
}

</style>