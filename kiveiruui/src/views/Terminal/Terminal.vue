<template>
  <div id="x-shell">
    <div id="x-shell-background">
      <iframe src="/xshellback/index.html" frameborder="0"></iframe>
    </div>
    <div id="x-shell-content">
      <TerminalBox />
    </div>
  </div>
</template>

<script>
import TerminalBox from "@/layout/components/TerminalBox/TerminalBox";

export default {
  name: 'Terminal',
  components: {
    TerminalBox: TerminalBox
  }
}
</script>

<style scoped>
#x-shell-background {
  position: absolute;
  z-index: 1;
  width: 100%;
  height: 100%;
}

#x-shell-content {
  position: absolute;
  z-index: 2;
  width: 90%;
  height: 80%;
  margin-left: 5%;
  margin-top: 5%;
}

#x-shell-background iframe{
  width: 100%;
  height: 100%;
}
</style>