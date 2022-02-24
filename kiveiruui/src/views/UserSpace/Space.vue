<template>
  <div>
    <div class="space-header">
      <div class="wrapper">
        <div class="space-header-inner">
          <div class="space-header-user">
            <div class="space-header-userInfo">
              <div class="avatar-container">
                <div>
                  <div class="user-avatar">
                    <img class="user-avatar-img" src="./test-avatar.jfif">
                    <span class="user-avatar-img-icon"></span>
                  </div>
                </div>
                <a href="/account/avatar" class="avatar-cover">更换头像</a>
              </div>
              <div class="userInfo-basic">
                <div style="display: flex">
                  <span id="user-nickname">{{ nickname }}</span>
                  <div class="authority-and-level">
                    <div class="authority-level-box">
                      <div class="authority-box">
                        <div class="tiny">{{ authority }}</div>
                      </div>
                      <div class="level-box">
                        <div class="tiny">{{ level }}</div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="userInfo-basic-spacing">
                  <input id="modify-sign" type="text" placeholder="编辑个性签名" maxlength="60" v-model="sign">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="navigator">

    </div>
    <div class="navigator-fixed">

    </div>
    <div class="space">

    </div>
  </div>
</template>

<script>
export default {
  name: "Space",
  data() {
    return {
      nickname: '用户昵称',
      authority: '用户权限',
      level: 'xx',
      sign: '这是个性签名'
    }
  }
}
</script>

<style scoped>
.space-header {
  position: relative;
}

.wrapper {
  position: relative;
  width: 1283px;
  margin: 0 auto;
}

.space-header-inner {
  position: relative;
  padding-top: 116px;
  background: deepskyblue;
  /*background-position: 50%;*/
  /*background-size: cover;*/
  /*transition: background-image .2s ease,background-size 1s ease;*/
}

.space-header-user {
  position: relative;
  z-index: 1;
}

.space-header-userInfo {
  margin-left: 20px;
  padding-bottom: 16px;
  display: flex;
  align-items: center;
}

.avatar-container {
  position: relative;
}

.user-avatar {
  display: block;
  position: relative;
  width: 60px;
  height: 60px;
  background: plum;
  border-radius: 50%;
  margin: 0;
  padding: 0;
}

.user-avatar-img {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  height: 100%;
  border: 2px solid hsla(0,0%,100%,.4);
  border-radius: 50%;
}

.user-avatar-img-icon {
  position: absolute;
  z-index: 1;
  width: 18px;
  height: 18px;
  right: 0;
  bottom: -1px;
  background: orange;
}

.avatar-cover {
  position: absolute;
  top: 0;
  left: 0;
  width: 60px;
  height: 60px;
  font-size: 12px;
  line-height: 60px;
  text-decoration: none;
  text-align: center;
  opacity: 0;
  border-radius: 50%;
  transition: opacity .2s;
  background: rgba(0,0,0,.6);
  color: #fff;
}

.avatar-cover:hover {
  opacity: 1;
}

.userInfo-basic {
  color: #fff;
  font-size: 0;
  margin-left: 22px;
}

#user-nickname {
  display: inline-block;
  margin-right: 5px;
  font-weight: 700;
  line-height: 18px;
  font-size: 18px;
  vertical-align: middle;
}

.authority-and-level {
  display: inline-block;
  vertical-align: middle;
  margin-left: 5px;
}

.authority-level-box {
  display: flex;
  position: relative;
  vertical-align: middle;
  margin-left: 3px;
  padding-left: 0;
  height: 16px;
  line-height: 10px;
  display: -ms-inline-flexbox;
  display: inline-flex;
  font-size: 8px;
  color: #f25d8e;
  border-radius: 1px;
}

.authority-box {
  width: 48px;
  padding-left: 2px;
  padding-right: 2px;
  border-color: rgb(26, 84, 75);
  border-right-width: 0;
  border-bottom-left-radius: 1px;
  border-top-left-radius: 1px;
  color: rgb(207,0 , 254);
  background-image: linear-gradient(90deg, rgb(255, 84, 70), rgb(255, 255, 100));
  white-space: nowrap;
  text-align: center;
  box-sizing: content-box;
}

.level-box {
  width: 18px;
  background: #fff;
  border: 1px solid #f25d8e;
  color: rgb(26, 84, 75);
  text-align: center;
  box-sizing: content-box;
}

.tiny {
  transform: scale(.5);
  width: 200%;
  height: 200%;
  font-weight: 400;
  transform-origin: left 30%;
  font-size: 20px;
}

.userInfo-basic-spacing {
  margin-top: 6px;
}

#modify-sign {
  background: transparent;
  border-radius: 4px;
  border: none;
  box-shadow: none;
  color: hsla(0,0%,100%,.8);
  font-size: 12px;
  font-family: Microsoft Yahei;
  line-height: 26px;
  height: 26px;
  margin-left: -5px;
  padding: 0 5px;
  position: relative;
  top: -1px;
  width: 730px;
  transition: all .3s ease;
  vertical-align: top;
}

#modify-sign:hover {
  background: hsla(0,0%,100%,.2);
  box-shadow: 0 0 0 1px hsl(0,0%,100%);
}
</style>