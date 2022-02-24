<template>
  <div id="nav">
    <router-view />
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 0;
  width: 100%;
  position: absolute;
  top:0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
}

#nav a.router-link-exact-active {
  color: #42b983;
}

</style>
