import {mount, createLocalVue} from "@vue/test-utils";
import ElementUI from 'element-ui';
import Mock from '../../mock';
import Vuex from 'vuex';
import VueRouter from 'vue-router';

import LoginDialog from "@/components/LoginDialog";
import test_routes from '../test_routes';
import test_store from "../test_store";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Mock);
localVue.use(Vuex);
localVue.use(VueRouter);

const defaultStore = new Vuex.Store(test_store);
const defaultRouter = new VueRouter(test_routes);

describe('login dislog', () => {
    let store = defaultStore;
    let router = defaultRouter;
    beforeEach(() => {
        store = defaultStore;
        router = defaultRouter;
    });
    const wrapper = mount(LoginDialog, {router, store, localVue});
    it('should register', async () => {
        wrapper.vm.activeName = 'register';
        const regName = wrapper.find('#register-username');
        const regPass = wrapper.find('#register-password');
        const regMail = wrapper.find('#register-mail');
        expect(regName.exists()).toBe(true);
        regName.setValue('WTL');
        expect(wrapper.vm.registerForm.username).toBe('WTL');
        expect(regPass.exists()).toBe(true);
        regPass.setValue('password');
        expect(wrapper.vm.registerForm.password).toBe('password');
        expect(regMail.exists()).toBe(true);
        regMail.setValue('lycheenut@sjtu.edu.cn');
        expect(wrapper.vm.registerForm.mailAddr).toBe('lycheenut@sjtu.edu.cn');
        const regBtn = wrapper.find('#register-btn');
        expect(regBtn.exists()).toBe(true);
        await regBtn.trigger('click');
        // expect(wrapper.vm.activeName).toBe('login');
    });
    it('should login', async () => {
        const loginName = wrapper.find('#login-username');
        const loginPass = wrapper.find('#login-password');
        expect(loginName.exists()).toBe(true);
        loginName.setValue('lycheenut');
        expect(wrapper.vm.loginForm.username).toBe('lycheenut');
        expect(loginPass.exists()).toBe(true);
        loginPass.setValue('admin');
        expect(wrapper.vm.loginForm.password).toBe('admin');
        const loginBtn = wrapper.find('#login-btn');
        expect(loginBtn.exists()).toBe(true);
        await loginBtn.trigger('click');
        // expect(wrapper.vm.$store.state.userData).toBe({
        //     username: 'lycheenut',
        //     userType: 1
        // });
    })
});
