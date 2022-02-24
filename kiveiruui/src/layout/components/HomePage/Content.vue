<template>
  <article id="main-content" class="main-content">
    <section class="logos">
      <h1 style="margin: 0">logos</h1>
    </section>
    <section class="features">
      <h1 style="margin: 0">features</h1>
    </section>
    <section class="projects">
      <h1 style="margin: 0">projects</h1>
    </section>
  </article>
</template>

<script>
export default {
  name: 'Content'
}
</script>

<style scoped>
.main-content {
  position: relative;
  width: 100%;
  top: 0;
  z-index: 2;
}

.logos {
  position: relative;
  width: 100%;
  height: 150px;
  z-index: 2;
  background-color: aqua;
}

.features {
  position: relative;
  width: 100%;
  height: 500px;
  z-index: 2;
  background-color: burlywood;
}

.projects {
  position: relative;
  width: 100%;
  height: 500px;
  z-index: 2;
  background-color: darkgrey;
}
</style>