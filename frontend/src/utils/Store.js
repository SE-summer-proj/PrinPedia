import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        logged: false,
        userData: {
            avatar: '',
            username: ''
        }
    },
    mutations: {
        setUserData(state, userData) {
            state.userData = userData;
            state.logged = true;
        }
    }
});
