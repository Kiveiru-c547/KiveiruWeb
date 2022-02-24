<template>
  <div class="about">
    <h1>This is an Test page</h1>
    <el-button @click="add">新增</el-button>
    <div>
        <el-table :data="user">
            <el-table-column prop="account" label="账号" />
            <el-table-column prop="nickname" label="昵称" />
            <el-table-column prop="password" label="密码" />
            <el-table-column prop="authority" label="权限" :formatter="authorityFormatter"/>
        </el-table>
    </div>
    <!-- 添加按钮对话框 -->
    <el-dialog title="添加用户" v-model="open" width="800px" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="账号" prop="account" label-width="107px">
              <el-input v-model="form.account" placeholder="请输入账号" style="width:200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="nickname" label-width="107px">
              <el-input v-model="form.nickname" placeholder="请输入昵称" style="width:200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password" label-width="107px">
              <el-input v-model="form.password" placeholder="请输入密码" style="width:200px"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="权限" prop="authority" label-width="107px">
              <el-select v-model="form.authority" placeholder="请选择权限" style="width:200px">
                <el-option label="Thaumiel" value="1"></el-option>
                <el-option label="Keter" value="2"></el-option>
                <el-option label="Euclid" value="3"></el-option>
                <el-option label="Safe" value="4"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'Test',
  data() {
    return {
        user: [{
            id:1,
            account: 'kiv001',
            password: '123456',
            nickname: 'kiv123',
            authority: 4
        },
        {
            id:2,
            account: 'kiv002',
            password: '123456',
            nickname: 'kiv234',
            authority: 2
        },
        {
            id:3,
            account: 'kiv003',
            password: '123456',
            nickname: 'kiv345',
            authority: 3
        }],
        form: {},
        open: false
    }
  },
  created() {
      
  },
  methods: {
      getUser() {

      },
      add() {
          this.open = true;
      },
      submitForm() {
          this.form.authority = parseInt(this.form.authority);
          console.log("this.form", this.form);
          this.$store.dispatch("register", this.form).then(() => {
            this.$message({
              message: '注册成功',
              type: 'success',
              showClose: true
            });
            this.open = false;
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
      },
      cancel() {
          this.open = false;
          this.form = {
            id: null,
            account: null,
            password: null,
            nickname: null,
            authority: null
          }
      },
      authorityFormatter(row) {
          if (row.authority == 1) {
              return "Thaumiel";
          } else if (row.authority == 2) {
              return "Keter";
          } else if (row.authority == 3) {
              return "Euclid";
          } else if (row.authority == 4) {
              return "Safe";
          }
      }
  },
}
</script>