export default {
    state: {
        logged: false,
        userData: {
            username: '',
            userType: 0
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
                userType: 0
            }
            state.logged = false;
        }
    }
};
