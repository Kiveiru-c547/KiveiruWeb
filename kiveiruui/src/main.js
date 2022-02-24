import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import * as Icons from '@element-plus/icons-vue'

const app = createApp(App)
Object.keys(Icons).forEach(key => {
    app.component(key, Icons[key])
})
app.use(store).use(router).use(ElementPlus).mount('#app')