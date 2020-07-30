import { shallowMount, createLocalVue, mount } from "@vue/test-utils";
import dialog from "@/components/LoginDialog.vue"
import ElementUI from "element-ui";

const localVue = createLocalVue();
localVue.use(ElementUI);

jest.mock("axios", () => ({
    post: () => Promise.resolve({ status: 0 })
}));

describe("init", () => {
    it("init state", () => {
        const wrapper = shallowMount(dialog, {
            localVue
        });
        /**
         * 检查data的初始状态
         */
        expect(wrapper.vm.loginForm.username).toEqual('');
        expect(wrapper.vm.loginForm.password).toEqual('');
    });
});

/**
 * 异步和mock
 */
describe("login (async function)", () => {

    it("login success by call function", () => {
        const wrapper = shallowMount(dialog, {
            localVue
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
        expect(wrapper.vm).toBeTruthy();
    });
});
