import {createLocalVue, mount} from "@vue/test-utils";
import Recommend from "@/components/Recommend.vue"
import ElementUI from "element-ui";
import Vuex from "vuex";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: ['迟先生', '苏大佬']
        },
        status: 200,
    })
}));

describe("Recommend", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                userData: { username: "test" }
            },
        })
    })
    it("recommend", async () => {
        const wrapper = mount(Recommend, { store, localVue });
        /**
         * 通过css选择器找到按钮，并检查是否找到
         */
        expect(wrapper.vm.entries).toEqual([]);
        await wrapper.vm.getRecommend();
        expect(wrapper.vm.entries).toEqual(['迟先生', '苏大佬']);
    });
});