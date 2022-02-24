<template>
  <div id="file-tree" style="height: 100%" ref="fileTree" @setUserId="setUserId">
    <el-tree
        class="file-tree"
        :data="treeData"
        :load="onLoadData"
        :props="defaultProps"
        :default-expanded-keys="defaultExpandedKeys"
        :highlight-current="true"
        :lazy="true"
    >
      <template #default="{ node, data }">
        <el-tooltip
          class="tooltip-item"
          effect="dark"
          :content="data.fileName"
          placement="top"
          :show-arrow="false"
        >
          <span class="custom-tree-node">
            <span>
              <el-icon v-if="data.isDir === 'true' || data.isDir === true"><folder /></el-icon>
              <el-icon v-else-if="(data.isDir === 'false' || data.isDir === false) && isVideo(data)"><video-camera /></el-icon>
              <el-icon v-else-if="(data.isDir === 'false' || data.isDir === false) && isAudio(data)"><service /></el-icon>
              <el-icon v-else-if="(data.isDir === 'false' || data.isDir === false) && isPicture(data)"><picture-rounded /></el-icon>
              <el-icon v-else-if="(data.isDir === 'false' || data.isDir === false) && isZip(data)"><box /></el-icon>
              <el-icon v-else><document /></el-icon>
              {{ node.label }}
            </span>
          </span>
        </el-tooltip>
      </template>
    </el-tree>
  </div>
</template>

<script>
import { ref } from 'vue'
import { listFile } from "@/api/xshell/filesmanager";

export default {
  name: 'FileTree',
  data() {
    return {
      defaultExpandedKeys: [1],
      treeData: [],
      userId: ""
    }
  },
  methods: {
    getSuffix(filePath) {
      let index= filePath.lastIndexOf(".");
      let ext = filePath.substr(index+1).toLowerCase();
      return ext;
    },
    isVideo(data) {
      let ext = this.getSuffix(data.fileName);
      let extList = [
        'wmv',
        'asf',
        'asx',
        'rmvb',
        'mp4',
        '3gp',
        'mov',
        'm4v',
        'avi',
        'dat',
        'mkv',
        'flv',
        'vob'
      ];
      if (extList.indexOf(ext) > -1) {
        return true;
      }
      return false;
    },
    isAudio(data){
      let ext = this.getSuffix(data.fileName);
      let extList = [
        'cda',
        'wav',
        'mp3',
        'aif',
        'aiff',
        'mid',
        'wma',
        'ra',
        'vqf',
        'ape',
        'ogg',
        'midi',
        'flac',
        'aac'
      ];
      if (extList.indexOf(ext) > -1) {
        return true;
      }
      return false;
    },
    isPicture(data) {
      let ext = this.getSuffix(data.fileName);
      let extList = [
        'bmp',
        'pcx',
        'tif',
        'tiff',
        'gif',
        'jpg',
        'jpeg',
        'tga',
        'exif',
        'fpx',
        'svg',
        'psd',
        'cdr',
        'pcd',
        'dxf',
        'ufo',
        'eps',
        'ai',
        'png',
        'hdri',
        'raw',
        'wmf',
        'flic',
        'emf',
        'ico'
      ];
      if (extList.indexOf(ext) > -1) {
        return true;
      }
      return false;
    },
    isZip(data) {
      let ext = this.getSuffix(data.fileName);
      let extList = [
          'zip',
          'gzip',
          'rar',
          '7z',
          'bzip2',
          'tar',
          'cab',
          'iso',
          'arj',
          'lzh',
          'chm',
          'wim',
          'z',
          'cpio',
          'rpm',
          'deb',
          'nsis'
      ];
      if (extList.indexOf(ext) > -1) {
        return true;
      }
      return false;
    }
  },
  created() {
  },
  setup() {
    const userId = ref("");
    const treeData = ref([]);
    const defaultProps = ref({
      children: 'children',
      label: 'fileName',
      isLeaf: 'isFile',
    });
    function setUserId(id) {
      userId.value = id;
      treeData.value = [{
        fileName: '/',
        filePath: '',
        children: [],
        isFile: false,
        isDir: true
      }];
    }
    function onLoadData(treeNode, resolve) {
      if (treeNode.data.isDir == true || treeNode.data.isDir == 'true') {
        let path = treeNode.data.filePath;
        if (path.substr(0, 2) == '//') {
          path = path.slice(1);
        }
        listFile({
          userId: userId.value,
          path: path
        }).then(res => {
          let data = res.data;
          // data.map(item => {
          //   item.children = [];
          // });
          resolve(data);
        });
      } else {
        resolve([]);
      }
    }
    return {
      setUserId,
      treeData,
      defaultProps,
      onLoadData
    }
  }
}
</script>

<style>
#file-tree > .file-tree{
  height: 100%;
}
#file-tree .el-tree-node{
  max-height: 100%;
}
</style>