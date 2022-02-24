<template>
  <el-tabs class="terminal-tabs" v-model="editableTabsValue" type="card" @tab-remove="removeTab" @tab-click="switchTab">
    <el-tab-pane
      v-for="(item, index) in editableTabs"
      :key="item.name"
      :label="item.title"
      :name="item.name"
      :index="index"
      :closable="item.closable"
    >
      <LatestUsed v-if="item.name === '1'"></LatestUsed>
      <XTerm v-if="item.name !== '1'" :ref="`xterm${item.name}`" :divId="item.divId" :node="item.node" @setUserId="setUserId"></XTerm>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import XTerm from '@/layout/components/TerminalBox/XTerm.vue';
import LatestUsed from '@/layout/components/TerminalBox/LatestUsed.vue';

export default {
  name: "TerminalTabs",
  components: {
    XTerm: XTerm,
    LatestUsed: LatestUsed,
  },
  data() {
    return {
      editableTabsValue: '1',
      editableTabs: [{
        title: '工作台',
        name: '1',
        closable: false,
        divId: 'terminal_p',
      }],
      tabIndex: 1,
      // 激活的标签
      activeName: 1,
    }
  },
  methods: {
    addTab(node) {
      let newTabName = ++this.tabIndex + '';
      this.editableTabs.push({
        title: node.name + '-' + node.config.host,
        name: newTabName,
        closable: true,
        divId: "terminal_" + newTabName,
        node: node,
      });
      this.editableTabsValue = newTabName;
      this.activeName = newTabName;
    },
    removeTab(targetName) {
      let tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            let nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }
      this.editableTabsValue = activeName;
      this.activeName = activeName;
      this.editableTabs = tabs.filter(tab => tab.name !== targetName);
    },
    switchTab(tabs) {
      // 更改当前选中的选项卡
      this.activeName = tabs.props.name;
      if (this.activeName !== '1'){
        let userId = this.getUserId();
        this.setUserId(userId);
      }
    },
    getUserId() {
      return this.$refs[`xterm${this.activeName}`][0].userId;
    },
    setUserId(userId) {
      this.$emit('setUserId', userId);
    }
  },
  mounted() {
  }
}
</script>

<style>
.terminal-tabs {
  height: 100%;
}

.terminal-tabs > .el-tabs__content {
  height: calc(100% - 40px);
}

.terminal-tabs > .el-tabs__header {
  margin: 0;
}

.terminal-tabs .el-tab-pane {
  height: 99%;
}
</style>