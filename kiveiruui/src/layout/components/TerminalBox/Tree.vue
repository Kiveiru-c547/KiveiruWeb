<template>
  <div id="tree-content">
    <div :class="['box', theme]" ref="box" @click.stop="leftClick" @contextmenu="rightClick">
      <el-tree
          class="filter-tree"
          :data="data"
          :props="defaultProps"
          :default-expanded-keys="defaultExpandedKeys"
          ref="tree"
          :highlight-current="true"
          @node-click="handleNodeClick"
          @node-contextmenu="handleContextMenu"
          style="height: 100%"
      >
        <template #default="{ node, data }">
          <span class="custom-tree-node">
            <span>
              <el-icon v-if="data.type=='NODE'"><monitor /></el-icon>
              <el-icon v-if="data.type=='FOLDER'"><folder-opened /></el-icon>
              {{ node.label }}
            </span>
          </span>
        </template>
      </el-tree>
    </div>
    <vue3-menus v-model:open="isOpen" :event="eventVal" :menus="menus" hasIcon>
      <template #icon="{item: {activeIndex}}">{{activeIndex}}</template>
      <!--    <template #label="{ item: { item } }">插槽：{{ item.label }}</template>-->
    </vue3-menus>

    <FolderForm :dialogFormVisible="folderFormVisible" :cancel="handleFolderFormCancel" :form="folderForm" :treeRefresh="refresh" :currentClickNodeData="currentClickNodeData" :folderTitle="folderTitle"/>
    <ConnectForm :dialogFormVisible="connectFormVisible" :cancel="handleConnectFormCancel" :form="connectForm" :treeRefresh="refresh" :currentClickNodeData="currentClickNodeData" :connectTitle="connectTitle"/>
  </div>

</template>

<script>
import FolderForm from '@/layout/components/TerminalBox/FolderForm.vue';
import ConnectForm from '@/layout/components/TerminalBox/ConnectForm.vue';
import { Vue3Menus } from 'vue3-menus';
import { ElMessageBox, ElMessage } from 'element-plus'
import { getEcsTree, deleteEcs } from '@/api/xshell/ecs'
import { nextTick, ref, shallowRef } from "vue";

export default {
  name: "Tree",
  props: {
    addTab: Function,
    theme: String,
  },
  components: {
    Vue3Menus,
    FolderForm : FolderForm,
    ConnectForm : ConnectForm,
  },
  data() {
    return {
      defaultExpandedKeys: [1],
      data: [],
      loading: true
    };
  },
  methods: {
    getTree() {
      getEcsTree().then(res=>{
        this.data = res.data;
      });
    },
    refresh() {
      this.search();
    },
    search() {
      this.loading = true;
      //get接口
      getEcsTree().then(res=>{
        this.data = res.data;
      });
    },
  },
  created() {
    this.getTree();
  },
  setup(props) {
    const isOpen = ref(false);
    const folderFormVisible = ref(false);
    const connectFormVisible = ref(false);
    const currentClickNodeData = ref(null);
    const eventVal = ref({});
    const folderTitle = ref('新增文件夹');
    const connectTitle = ref("新建连接");
    const connectForm = ref({
      id: 0,
      name: '',
      description: '',
      config: {},
    });
    const folderForm = ref({
      id: 0,
      name: '',
      description: '',
    });
    function leftClick() {
      isOpen.value = false;
    }
    function rightClick(event) {
      menus.value = menus1.value;
      isOpen.value = false;
      nextTick(() => {
        eventVal.value = event;
        isOpen.value = true;
      })
      event.preventDefault();
    }
    let menus = shallowRef([]);
    const menus1 = shallowRef([
      {
        label: "新建文件夹",
        click: () => {
          folderFormVisible.value = true;
          folderForm.value = {
            id: 0,
            name: '',
            description: '',
            parentId: -1,
          };
        }
      },
      {
        label: "新建连接",
        click: () => {
          connectFormVisible.value = true;
          connectForm.value = {
            id: 0,
            name: '',
            description: '',
            parentId: -1,
            config: {},
          };
        }
      }
    ]);
    const menus2 = shallowRef([
      {
        label: "编辑连接",
        click: () => {
          connectFormVisible.value = true;
          connectTitle.value = '编辑连接';
          connectForm.value = {
            id : currentClickNodeData.value.id,
            name: currentClickNodeData.value.name,
            description: currentClickNodeData.value.description,
            config : {
              host : currentClickNodeData.value.config.host,
              port : currentClickNodeData.value.config.port,
              user : currentClickNodeData.value.config.user,
              password : currentClickNodeData.value.config.password,
              authMethod : currentClickNodeData.value.config.authMethod,
              identity : currentClickNodeData.value.config.identity,
              passphrase : currentClickNodeData.value.config.passphrase
            }
          };
        }
      },
      {
        label: "删除连接",
        click: () => {
          ElMessageBox.confirm(
            '是否删除连接' + '“' + currentClickNodeData.value.name + '”？',
            'Warning',
            {
              confirmButtonText: '确认',
              cancelButtonText: '取消',
              type: 'warning',
            }
          ).then(() => {
            deleteEcs(parseInt(currentClickNodeData.value.id)).then(res=>{
              if(res !== undefined && res.code !== undefined && res.code === '200' && res.data !== 0) {
                ElMessage({
                  type: 'success',
                  message: '连接' + '“' + currentClickNodeData.value.name + '”已删除',
                });
                getEcsTree().then(res=>{
                  data.value = res.data;
                });
              } else {
                ElMessage({
                  type: 'error',
                  message: '无法删除id为' + res.data + '的数据项',
                })
              }
            })
          }).catch(() => {
            ElMessage({
              type: 'info',
              message: '取消删除',
            })
          })
        }
      }
    ]);
    const menus3 = shallowRef([
      {
        label: "新建文件夹",
        click: () => {
          folderFormVisible.value = true;
          folderForm.value = {
            id: 0,
            name: '',
            parentId: -1,
            description: '',
          };
        }
      },
      {
        label: "新建连接",
        click: () => {
          connectFormVisible.value = true;
          connectForm.value = {
            id: 0,
            name: '',
            description: '',
            parentId: -1,
            config: {},
          };
        }
      },
      {
        label: "编辑文件夹",
        click: () => {
          folderFormVisible.value = true;
          folderTitle.value = '编辑文件夹';
          folderForm.value = {
            id: currentClickNodeData.value.id,
            name: currentClickNodeData.value.name,
            description: currentClickNodeData.value.description
          };
        }
      },
      {
        label: "删除文件夹",
        click: () => {
          console.log('删除文件夹');
          ElMessageBox.confirm(
              '是否删除文件夹' + '“' + currentClickNodeData.value.name + '”？注意：文件夹下所有子目录都将被删除',
              'Warning',
              {
                confirmButtonText: '确认',
                cancelButtonText: '取消',
                type: 'warning',
              }
          ).then(() => {
            deleteEcs(parseInt(currentClickNodeData.value.id)).then(res=>{
              if(res !== undefined && res.code !== undefined && res.code === '200' && res.data !== 0) {
                ElMessage({
                  type: 'success',
                  message: '文件夹' + '“' + currentClickNodeData.value.name + '”已删除',
                });
                getEcsTree().then(res=>{
                  data.value = res.data;
                });
              } else {
                ElMessage({
                  type: 'error',
                  message: '无法删除id为' + res.data + '的数据项',
                })
              }
            })
          }).catch(() => {
            ElMessage({
              type: 'info',
              message: '取消删除',
            })
          })
        }
      }
    ]);
    const data = ref([]);
    const defaultProps = ref({
      children: 'children',
      label: 'name',
      isLeaf: 'leaf',
      icon: 'icon',
    });
    function handleNodeClick(node) {
      isOpen.value = false;
      if(node.type === 'NODE') {
        props.addTab(node);
      }
    }
    function handleContextMenu(event, data) {
      currentClickNodeData.value = data;
      if (data.type == 'NODE') {
        menus.value = menus2.value;
      } else {
        menus.value = menus3.value;
      }
      isOpen.value = false;
      nextTick(() => {
        eventVal.value = event;
        isOpen.value = true;
      })
      event.preventDefault();
    }
    function handleFolderFormCancel() {
      folderFormVisible.value = false;
      folderForm.value = {
        id: 0,
        name: '',
        description: '',
        parentId: -1,
      };
    }
    function handleConnectFormCancel() {
      connectFormVisible.value = false;
      connectForm.value = {
        id: 0,
        name: '',
        description: '',
        parentId: -1,
        config: {},
      };
    }
    return {
      menus,
      isOpen,
      folderFormVisible,
      connectFormVisible,
      folderForm,
      connectForm,
      currentClickNodeData,
      folderTitle,
      connectTitle,
      leftClick,
      rightClick,
      eventVal,
      data,
      defaultProps,
      handleNodeClick,
      handleContextMenu,
      handleFolderFormCancel,
      handleConnectFormCancel
    }
  }
}
</script>

<style scoped>
#tree-content {
  height: 100%;
}
#tree-content > .box {
  height: 100%;
}
</style>