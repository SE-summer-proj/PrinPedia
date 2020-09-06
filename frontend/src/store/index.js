import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        logged: false,
        userType: [],
        userData: {
            username: '',
            birthday: '',
            email: '',
            avatarBase64: ''
        }
    },
    mutations: {
        login(state, userType) {
            state.logged = true;
            state.userType = userType;
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
