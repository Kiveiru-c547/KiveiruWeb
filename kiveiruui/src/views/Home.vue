<template>
  <div class="home">
    <div id="homepage">
      <header id="homepage-header" class="homepage-header">
        <navbar class="main-nav" />
        <div id="home-background">
          <iframe src="/homebg/index.html" frameborder="0" class="iframe-animation"></iframe>
        </div>
        <div class="welcome">
          <Welcome msg="Welcome to My Web"/>
        </div>
      </header>
      <header id="main-header" class="main-header">
        <navbar class="main-nav shown"></navbar>
      </header>
      <Content />
      <Footer />
    </div>
  </div>
</template>

<script>
import Navbar from '@/layout/components/HomePage/Navbar.vue'
import Welcome from '@/layout/components/HomePage/Welcome.vue'
import Content from "@/layout/components/HomePage/Content";
import Footer from "@/layout/components/HomePage/Footer";

export default {
  name: 'Home',
  components: {
    Navbar,
    Welcome,
    Content,
    Footer
  },
  methods: {
    navbarController() {
      let navbar = document.getElementById("main-header");
      let article = document.getElementById("main-content");
      window.onscroll = () => {
        if (window.pageYOffset >= article.offsetTop) {
          navbar.classList.add("shown");
        } else {
          navbar.classList.remove("shown");
        }
      }
    }
  },
  mounted() {
    this.navbarController();
  }
}
</script>

<style scoped>
.home {
  width: 100%;
}

#home-background {
  width: 100%;
  height: calc(100vh - 150px);
  position: absolute;
  top:0;
  left: 0;
  right: 0;
  z-index: 1;
}

.iframe-animation {
  width: 100%;
  height: calc(100vh - 150px);
  margin: 0;
}

#homepage {
  position: absolute;
  width: 100%;
  z-index: 2;
  padding: 0;
  margin: 0;
  top: 0;
  left: 0;
}

.homepage-header {
  position: relative;
  left: 0;
  top: 0;
  width: 100%;
  height: calc(100vh - 150px);
  z-index: 1;
  background: #000;
}

.main-header {
  position: fixed;
  left: 0;
  top: -61px;
  width: 100%;
  height: 61px;
  z-index: 3;
  transition: all .6s;
}

header.shown {
  top: 0;
  transition: all .6s;
}

.welcome {
  position: absolute;
  width: 100%;
  height: fit-content;
  z-index: 1;
  margin: auto;
  top: 0;
  bottom: 0;
}
</style>