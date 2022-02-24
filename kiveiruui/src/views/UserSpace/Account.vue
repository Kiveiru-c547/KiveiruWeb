<template>
  <div>
    <div class="security-content">
      <div class="security-left">
        <span class="security-title">个人中心</span>
        <ul class="security-ul">
          <li class="security-list on" @click="clickListItem('/account/home', $event)">
            <span class="security-nav-name">
              首
              <b style="width:22px; display:inline-block"></b>
              页
            </span>
          </li>
          <li class="security-list" @click="clickListItem('/account/setting', $event)">
            <span class="security-nav-name">
              我的信息
            </span>
          </li>
          <li class="security-list" @click="clickListItem('/account/avatar', $event)">
            <span class="security-nav-name">
              我的头像
            </span>
          </li>
        </ul>
      </div>
      <div class="security-right">
        <router-view />
      </div>
    </div>
    <Footer />
  </div>
</template>

<script>
import Footer from "@/layout/components/HomePage/Footer";
export default {
  name: "Account",
  components: {
    Footer: Footer
  },
  methods: {
    clickListItem(path, event) {
      let listItems = document.getElementsByClassName('security-list');
      listItems = Array.from(listItems);
      listItems.map(item => {
        if (item.classList.contains('on')) {
          item.classList.remove('on')
        }
      });
      let element = event.currentTarget;
      element.classList.add('on');
      this.$router.push({ path: path });
    }
  }
}
</script>

<style scoped>
.security-content {
  width: 980px;
  height: 100%;
  margin: 10px auto 100px;
  border: 1px solid #e1e2e5;
  box-shadow: 0 2px 4px rgb(0 0 0);
  background: #fafafa;
  border-radius: 4px;
  overflow: hidden;
  display: flex;
}

.security-left {
  width: 150px;
  height: 100%;
  overflow: hidden;
}

.security-title {
  display: block;
  width: 150px;
  height: 50px;
  text-align: center;
  line-height: 50px;
  font-size: 16px;
  color: #99a2aa;
  cursor: default;
  border-bottom: 1px solid #e1e2e5;
}

.security-ul {
  padding: 0;
  margin: 0;
  border-bottom: 1px solid #e1e2e5;
  list-style: none;
}

.security-list {
  width: 150px;
  height: 48px;
  line-height: 48px;
}

.security-left .on {
  background-color: #00a1d7 !important;
}

.security-list:hover {
  background: #e1e4ea;
  cursor: pointer;
}

.security-nav-name {
  margin-left: 12px;
  font-size: 14px;
  color: #222;
}

.security-right {
  width: 829px;
  border-left: 1px solid #ddd;
  background: #fff;
  min-height: 890px!important;
}
</style>