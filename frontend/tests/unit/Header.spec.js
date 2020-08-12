import {createLocalVue, mount} from "@vue/test-utils";
import Header from "@/components/Header.vue"
import ElementUI from "element-ui";
import Vuex from "vuex";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

// const $store = {
//     state: () => ({
//         logged: true
//     })
// };

// jest.mock("Vuex", () => ({
//     $store: () => Promise.resolve({
//         state: {logged: true}
//     })
// }));

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
        },
        status: 200,
    })
}));

describe("header1", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: false,
                userData: { username: "test" }
            }
        })
    })
    it("find login-button", async () => {
        const wrapper = mount(Header, { store, localVue });
        /**
         * 通过css选择器找到按钮，并检查是否找到
         */
        const btn0 = wrapper.find("#login-button");
        expect(btn0.exists()).toBeTruthy();
    });
});
describe("header2", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            },
            mutations: {
                logout(state) {
                    state.userData = {
                        username: '',
                        userType: 0,
                    }
                    state.logged = false;
                }
            }
        })
    })
    it("find logout-button", async () => {
        const wrapper = mount(Header, { store, localVue });
        /**
         * 通过css选择器找到按钮，并检查是否找到
         */
        const btn1 = wrapper.find("#user-button");
        expect(btn1.exists()).toBeTruthy();
        const btn2 = wrapper.find("#logout-button");
        expect(btn2.exists()).toBeTruthy();
        await btn2.trigger('click');
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.$store.state.logged).toBe(false);
    });
});