import Vue from "vue";
import Vuex from 'vuex';

Vue.use(Vuex);

export const store = new Vuex.Store({
    state: {
        logged: false,
        userData: {
            username: '',
            userType: [],
            birthday: '',
            mailAddr: ''
        }
    },
    mutations: {
        setUserData(state, userData) {
            state.userData = userData;
            state.logged = true;
        },
        logout(state) {
            state.userData = {
                username: '',
                userType: [],
                birthday: '',
                mailAddr: ''
            }
            state.logged = false;
        }
    }
});
