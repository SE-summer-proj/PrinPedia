import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        userData: {
            avatar: '',
            username: '',
            userId: ''
        }
    },
    mutations: {
        setUserData(state, userData) {
            state.userData = userData;
        }
    }
});