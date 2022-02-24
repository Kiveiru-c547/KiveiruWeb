import { createStore } from 'vuex'
import {getUserInfo, login, logout, register} from "@/api/login";

export default createStore({
  state: {
    id: '',
    account: '',
    nickname: '',
    // avatar: '',
    authority: '',
    token: localStorage.token,
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    SET_ID: (state, id) => {
      state.id = id;
    },
    SET_ACCOUNT: (state, account) => {
      state.account = account;
    },
    SET_NICKNAME: (state, nickname) => {
      state.nickname = nickname;
    },
    // SET_AVATAR: (state, avatar) => {
    //   state.avatar = avatar;
    // },
    SET_AUTHORITY: (state, authority) => {
      state.authority = authority;
    }
  },
  actions: {
    login({ commit }, user) {
      return new Promise((resolve, reject) => {
        login(user).then(res => {
          if (res.success) {
            commit('SET_TOKEN', res.data);
            localStorage.token = res.data;
            resolve();
          } else {
            reject(res.msg);
          }
        });
      });
    },
    register({ commit }, user) {
      // Promise 对象代表未来将要发生的事件，用来传递异步操作的消息
      // rejected: 意味着操作失败
      return new Promise((resolve, reject) => {
        register(user).then(res => {
          if (res.success) {
            resolve();
          } else {
            reject(res.msg);
          }
        }).catch((error) => {
          reject(error);
        })
      })
    },
    getUserInfo({ commit }) {
      return new Promise((resolve, reject) => {
        getUserInfo().then(res => {
          if (res.success) {
            commit('SET_ACCOUNT', res.data.account);
            commit('SET_NICKNAME', res.data.nickname);
            commit('SET_AUTHORITY', res.data.authority);
            commit('SET_ID', res.data.id);
            // commit('SET_AVATAR', res.data.avatar);
            resolve();
          } else {
            commit('SET_ACCOUNT', '');
            commit('SET_NICKNAME', '');
            commit('SET_AUTHORITY', '');
            commit('SET_ID', '');
            // commit('SET_AVATAR', res.data.avatar);
            commit('SET_TOKEN', '');
            reject(res.msg)
          }
        }).catch((error) => {
          reject(error);
        });
      });
    },
    logout({ commit }) {
      return new Promise((resolve, reject) => {
        logout().then(res => {
          if (res.success) {
            commit('SET_ACCOUNT', '');
            commit('SET_NICKNAME', '');
            commit('SET_AUTHORITY', '');
            commit('SET_ID', '');
            // commit('SET_AVATAR', res.data.avatar);
            commit('SET_TOKEN', '');
            localStorage.removeItem("token");
            resolve();
          } else {
            reject(res.msg);
          }
        }).catch((error) => {
          reject(error);
        });
      });
    }
  },
  modules: {
  }
})
