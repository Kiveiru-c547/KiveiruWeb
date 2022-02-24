import { createRouter, createWebHistory } from 'vue-router';
import { ElMessage } from 'element-plus';
import store from '@/store';
import Home from '../views/Home.vue';
import Layout from "@/layout/Layout";
import LoginLayout from "@/layout/LoginLayout";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    children: [
        {
            path: '/',
            name: 'LoginLayout',
            component: LoginLayout,
            children: [
                {
                    path: '/login',
                    name: 'Login',
                    component: () => import('../views/Login/login')
                },
                {
                    path: '/register',
                    name: 'Register',
                    component: () => import('../views/Login/register')
                },
                {
                    path: '/findpassword',
                    name: 'FindPassword',
                    component: () => import('../views/Login/FindPassword')
                },
                {
                    path: '/account',
                    name: 'Account',
                    component: () => import('../views/UserSpace/Account'),
                    children: [
                        {
                            path: '/account/home',
                            name: 'SpaceHome',
                            component: () => import('../views/UserSpace/SpaceHome')
                        },
                        {
                            path: '/account/setting',
                            name: 'Setting',
                            component: () => import('../views/UserSpace/Setting')
                        },
                        {
                            path: '/account/avatar',
                            name: 'UserAvatar',
                            component: () => import('../views/UserSpace/UserAvatar')
                        }
                    ]
                }
            ]
        },
        {
          path: '/about',
          name: 'About',
          // route level code-splitting
          // this generates a separate chunk (about.[hash].js) for this route
          // which is lazy-loaded when the route is visited.
          component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
        },
        {
          path: '/test',
          name: 'Test',
          component: () => import('../views/Test.vue')
        },
        {
            path: '/space',
            name: 'Space',
            component: () => import('../views/UserSpace/Space')
        },
        {
          path: '/terminaltest',
          name: 'TerminalTest',
          component: () => import('../views/Terminal/Terminal.vue'),
          hidden: true,
        },
        {
          path: '/spider',
          name: 'Spider',
          component: () => import('../views/Spider/Spider.vue'),
          hidden: true
        }
    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
    // 判断是否存在token
    if (store.state.token) {
        // 路径如果请求的是登录页面，则跳回首页
        if (to.path == "/login") {
            next({ path: '/' });
        } else {
            next();
        }
        // 如果存在token，证明用户登录过
        // 判断是否有用户信息
        if (store.state.account.length == 0) {
            // 需要获取用户信息
            store.dispatch("getUserInfo").then(res => {
                next();
            }).catch((error) => {
                ElMessage({
                    message: '登录已过期',
                    type: 'warning',
                    showClose:true
                });
                next({ path: '/login' });
            });
        } else {
            next();
        }
    } else {
        // if (to.matched.some(r => r.meta.requireLogin)) {
        //     this.$message({
        //         message: '请先登录哦',
        //         type: 'warning',
        //         showClose: true
        //     });
        // } else {
        //     next();
        // }
        next();
    }
});

export default router
