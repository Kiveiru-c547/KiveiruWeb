<template>
  <div>
    <div class="top-banner"></div>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'LoginLayout',
}
</script>

<style scoped>
.top-banner {
  background: plum;
  height: 86px;
  text-align: center;
  margin-bottom: 20px;
}
</style>