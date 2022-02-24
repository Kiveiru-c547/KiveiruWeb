<template>
  <div id="terminal-box">
    <el-card id="terminal-contain" body-style="width:100%;height:100%;padding:0">
      <div id="terminal-header">
        <Header></Header>
      </div>
      <div id="terminal-content">
        <el-row class="terminal-content">
          <el-col class="terminal-tree" :span="4">
            <el-tabs type="border-card" style="height: 100%;">
              <el-tab-pane>
                <template #label>
                  <span><el-icon><calendar /></el-icon>会话管理器</span>
                </template>
                <Tree :addTab="addTab"></Tree>
              </el-tab-pane>
              <el-tab-pane>
                <template #label>
                  <span><el-icon><folder /></el-icon>文件管理器</span>
                </template>
                <FileTree ref="fileTree" style="overflow: auto"></FileTree>
              </el-tab-pane>
            </el-tabs>
          </el-col>
          <el-col class="terminal-workspace" :span="20">
            <TerminalTabs ref="terminalTabs" @setUserId="setUserId"></TerminalTabs>
          </el-col>
        </el-row>
      </div>

    </el-card>
  </div>
</template>

<script>
import Header from "@/layout/components/TerminalBox/Header";
import TerminalTabs from "@/layout/components/TerminalBox/TerminalTabs.vue";
import Tree from "@/layout/components/TerminalBox/Tree.vue";
import FileTree from "@/layout/components/TerminalBox/FileTree.vue"

export default {
  name: "TerminalBox",
  components: {
    Header: Header,
    TerminalTabs: TerminalTabs,
    Tree: Tree,
    FileTree: FileTree
  },
  methods: {
    addTab(node) {
      this.$refs.terminalTabs.addTab(node);
    },
    setUserId(userId) {
      this.$refs.fileTree.setUserId(userId);
    }
  }
}
</script>

<style>
#terminal-box {
  height: 100%;
}
#terminal-contain {
  background-color: antiquewhite;
  height: 100%;
}

#terminal-header {
  width: 100%;
  height: 60px;
}

#terminal-content {
  width: 100%;
  height: calc(100% - 60px);
}

.terminal-content {
  width: 100%;
  height: 100%;
}

.terminal-workspace {
  height: 100%;
  background-image: url("../../../../src/assets/xtermAssets/xterm-workspace-bg.jfif");
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

.terminal-tree {
  height: 100%;
}

#terminal-content .el-tabs__content {
  height: calc(100% - 70px);
}

#terminal-content .el-tab-pane {
  height: 100%;
}

</style>