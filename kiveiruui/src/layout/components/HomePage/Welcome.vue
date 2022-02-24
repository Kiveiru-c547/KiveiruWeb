<template>
  <div class="welcome">
    <img alt="Vue logo" src="@/assets/logo.png">
    <h1>{{ msg }}</h1>
    <a href="/register" class="button register" @mouseenter="addHover('register')" @mouseleave="removeHover('register')">
      <span>注册</span>
      <div class="rect">
        <i class="r1"></i>
        <i class="r2"></i>
        <i class="r3"></i>
        <i class="r4"></i>
      </div>
    </a>
    <a href="/login" class="button login" @mouseenter="addHover('login')" @mouseleave="removeHover('login')">
      <span>登录</span>
      <div class="rect">
        <i class="r1"></i>
        <i class="r2"></i>
        <i class="r3"></i>
        <i class="r4"></i>
      </div>
    </a>
  </div>
</template>

<script>

export default {
  name: 'Welcome',
  props: {
    msg: String
  },
  methods: {
    addHover(btnType) {
      if (btnType === 'register') {
        let btn = Array.from(document.getElementsByClassName("register"));
        btn.map(item => { item.classList.add("hover") });
      } else {
        let btn = Array.from(document.getElementsByClassName("login"));
        btn.map(item => { item.classList.add("hover") });
      }
    },
    removeHover(btnType) {
      if (btnType === 'register') {
        let btn = Array.from(document.getElementsByClassName("register"));
        btn.map(item => { item.classList.remove("hover") });
      } else {
        let btn = Array.from(document.getElementsByClassName("login"));
        btn.map(item => { item.classList.remove("hover") });
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.button {
  display: inline-block;
  position: relative;
  width: 208px;
  height: 50px;
  margin: 13px 13px 12px 12px;
  background: #e91e63;
  color: white;
  text-align: center;
  text-decoration: none;
  font-size: 18px;
  font-weight: bold;
  cursor: pointer;
  line-height: 50px;
}

.button .rect {
  width: 100%;
  height: 100%;
  position: absolute;
  right: -6px;
  bottom: -6px;
}

.button .r1 {
  position: absolute;
  width: 2px;
  height: 6px;
  bottom: 0;
  left: 0;
  background: #e91e63;
}

.button .r2 {
  position: absolute;
  height: 2px;
  bottom: 0;
  left: 0;
  width: 100%;
  background: #e91e63;
}

.button .r3 {
  position: absolute;
  width: 2px;
  top: 0;
  right: 0;
  height: 100%;
  background: #e91e63;
}

.button .r4 {
  position: absolute;
  width: 6px;
  height: 2px;
  right: 0;
  top: 0;
  background: #e91e63;
}

.button.login {
  background: none;
  border: 2px solid white;
  width: 204px;
  height: 46px;
  top: 2px;
}

.button.login .r1 {
  background: white;
  bottom: -2px;
}

.button.login .r2 {
  background: white;
  bottom: -2px;
}

.button.login .r3 {
  background: white;
  top: 2px;
  right: -2px;
}

.button.login .r4 {
  background: white;
  right: -2px;
}

.button.hover {
  transform: translateX(2px) translateY(2px) !important;
}

.button.hover .rect {
  right: -4px;
  bottom: -4px;
}

.button.hover .r1 {
  height: 4px;
}
</style>
