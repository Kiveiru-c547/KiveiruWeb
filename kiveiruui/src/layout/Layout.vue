<template>
  <div>
    <Navbar class="fixed" />
    <router-view />
  </div>
</template>

<script>
import Navbar from "@/layout/components/HomePage/Navbar";
export default {
  name: 'Layout',
  components: {
    Navbar
  }
}
</script>

<style>

</style>