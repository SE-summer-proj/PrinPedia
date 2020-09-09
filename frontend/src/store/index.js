import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        logged: false,
        userType: [],
        username: '',
        userData: {
            username: '',
            birthday: '',
            email: '',
            avatarBase64: ''
        }
    },
    mutations: {
        login(state, loginInfo) {
            state.logged = true;
            state.userType = loginInfo.userType;
            state.username = loginInfo.username;
        },
        setUserData(state, userData) {
            state.userData = userData;
        },
        logout(state) {
            state.userData = {
                username: '',
                birthday: '',
                email: '',
                avatarBase64: ''
            };
            state.userType = [];
            state.logged = false;
        }
    }
});
