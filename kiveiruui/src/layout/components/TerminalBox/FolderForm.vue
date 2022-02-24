<template>
  <el-dialog :title="folderTitle" v-model="dialogFormVisible" :show-close="false" :close-on-click-modal="false" width="30%">

    <el-form :model="form" :rules="rules" ref="form">
      <el-form-item label="名称" :label-width="formLabelWidth" prop="name">
        <el-input v-model="form.name" auto-complete="off"></el-input>
      </el-form-item>

      <el-form-item label="描述" :label-width="formLabelWidth" prop="description">
        <el-input v-model="form.description" auto-complete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="onCancel">取 消</el-button>
      <el-button type="primary" @click="onSubmit('form')">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addEcs, updateEcs } from '@/api/xshell/ecs'
import {ElMessage} from "element-plus";

export default {
  name: "FolderForm",
  props: {
    dialogFormVisible: {
      type: Boolean,
      default: false
    },
    cancel: Function,
    form: Object,
    treeRefresh:Function,
    currentClickNodeData: Object,
    folderTitle: String,
  },
  data() {
    return {
      formLabelWidth: '120px',
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min: 1, max: 500, message: '长度在 1 到 500 个字符', trigger: 'blur' }
        ],
        description: [
          { min: 0, max: 500, message: '长度在 0 到 500 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    openLayer(title, msg, type) {
      ElMessage({
        type: type,
        message: msg,
      })
    },
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const that = this;
          const formData = that.form;
          formData.type = "FOLDER";
          if(formData.id === 0) {
            formData.parentId = -1;
            // 新增的时候绑定父节点
            if(this.currentClickNodeData != null) {
              formData.parentId = this.currentClickNodeData.id;
            }
            // 新增
            addEcs(JSON.stringify(formData)).then(res => {
              if(res !== undefined && res.code !== undefined && res.code === '200') {
                this.openLayer('消息', '新增成功！', 'success');
                // 关闭弹出层
                this.onCancel();
                this.treeRefresh();
              } else {
                this.openLayer('消息', res.data, 'error');
              }
            });
          } else {
            // 更新
            updateEcs(JSON.stringify(formData)).then(res => {
              if(res !== undefined && res.code !== undefined && res.code === '200') {
                this.openLayer('消息', '修改成功！', 'success');
                // 关闭弹出层
                this.onCancel();
                this.treeRefresh();
              } else {
                this.openLayer('消息', res.data, 'error');
              }
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    onCancel() {
      this.cancel();
    },
  }
}
</script>

<style scoped>
</style>