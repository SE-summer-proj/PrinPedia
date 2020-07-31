import { shallowMount, createLocalVue, mount } from "@vue/test-utils";
import dialog from "@/components/LoginDialog.vue"
import ElementUI from "element-ui";
import Vuex from "vuex"

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    post: () => Promise.resolve({
        message: "succeed.",
        status: 0
    })
}));

describe("init", () => {
    it("init state", () => {
        const wrapper = shallowMount(dialog, {
            localVue,
            // mocks: {
            //     axios
            // },
        });
        /**
         * 检查data的初始状态
         */
        expect(wrapper.vm.loginForm.username).toEqual('');
        expect(wrapper.vm.loginForm.password).toEqual('');
        expect(wrapper.vm.registerForm.username).toEqual('');
        expect(wrapper.vm.registerForm.password).toEqual('');
    });
});

const $store = {
    commit: () => "true"
};
const $router = {
    push: () => "true"
};

/**
 * 异步和mock
 */
describe("login", () => {

    it("login success by call function", () => {
        const wrapper = mount(dialog, {
            localVue,
            // mocks: {
            //     axios
            // },
        });
        /**
         * 直接调用组件的method，相当于this.login()
         */
        expect(wrapper.vm.login()).resolves.toBeTruthy();
    });

    it("login success by click", async () => {
        /**
         * 如果使用element-ui等第三方组件库，不要使用shallowMount
         */
        const wrapper = mount(dialog, {
            localVue,
            mocks: {
                $store,
                $router
            },
        });
        /**
         * 通过css选择器找到登录按钮，并检查是否找到
         */
        const btn = wrapper.find('#login-btn');
        expect(btn.exists()).toBe(true);
        /**
         * 模拟点击。由于login是async的，因此需要await点击完成
         */
        await btn.trigger('click');
        wrapper.vm.$nextTick(() => {
            expect(wrapper.vm.isLogged).toBeTruthy();
        });
    });
});

describe("register", () => {

    it("register success by call function", () => {
        const wrapper = mount(dialog, {
            localVue,
            // mocks: {
            //     axios
            // },
        });
        /**
         * 直接调用组件的method，相当于this.login()
         */
        wrapper.vm.activeName = "register";
        expect(wrapper.vm.register()).resolves.toBeTruthy();
        // expect(wrapper.vm.activeName).toEqual("login");
    });

    it("register success by click", async () => {
        /**
         * 如果使用element-ui等第三方组件库，不要使用shallowMount
         */
        const wrapper = mount(dialog, {
            localVue,
            // mocks: {
            //     axios
            // },
        });
        /**
         * 通过css选择器找到登录按钮，并检查是否找到
         */
        wrapper.vm.activeName = "register";
        // await wrapper.vm.$nextTick();
        const btn = wrapper.find('#regi-btn');
        expect(btn.exists()).toBe(true);
        /**
         * 模拟点击。由于login是async的，因此需要await点击完成
         */
        await btn.trigger('click');
        console.log(wrapper.vm.activeName);
        wrapper.vm.$nextTick(() => {
            expect(wrapper.vm.activeName).toEqual("login");
        });
    });
});
