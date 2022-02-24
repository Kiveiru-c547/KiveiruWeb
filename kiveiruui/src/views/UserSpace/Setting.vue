<template>
  <div>
    <div class="security-right-title">
      <span class="security-right-title-icon"></span>
      <span class="security-right-title-text">我的信息</span>
    </div>
    <div class="user-setting-wrap">
      <el-form>
        <el-form-item label="昵称:" label-width="95px">
          <el-input v-model="form.nickname" style="width: 200px" />
        </el-form-item>
        <el-form-item label="用户名:" label-width="95px">
          <span class="userInfo-descript">
            {{ form.username }}
          </span>
        </el-form-item>
        <el-form-item label="权限:" label-width="95px">
          <span class="userInfo-descript">
            {{ form.authority }}
          </span>
        </el-form-item>
        <el-form-item label="我的签名:" label-width="95px">
          <el-input v-model="form.sign" type="textarea" maxlength="60" show-word-limit/>
        </el-form-item>
        <el-form-item style="width: 789px">
          <div class="padding-dom"></div>
          <el-button>保存</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Setting",
  data() {
    return {
      form: {
        nickname: "nickname",
        username: "username",
        authority: "authority",
        sign: "sign"
      }
    }
  }
}
</script>

<style scoped>
.security-right-title {
  height: 50px;
  padding-left: 30px;
  border-bottom: 1px solid #ddd;
}

.security-right-title-icon {
  float: left;
  width: 4px;
  height: 16px;
  margin-top: 18px;
  background-color: #d60079;
  border-radius: 4px;
}

.security-right-title-text {
  float: left;
  margin: 15px 0 0 5px;
  color: #c5255e;
  font-size: 14px;
  cursor: default;
}

.user-setting-wrap {
  position: relative;
  padding: 20px 20px 0;
}

.userInfo-descript {
  font-size: 14px;
  color: #898989;
}

.padding-dom {
  width: 789px;
  height: 39px;
  border-bottom: 1px solid #e5e9ef;
  margin-bottom: 40px;
}
</style>