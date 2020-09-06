import {createLocalVue, mount} from "@vue/test-utils";
import Ranking from "@/components/Ranking.vue"
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

describe("Ranking", () => {
    it("ranking", async () => {
        const wrapper = mount(Ranking, { localVue });
        /**
         * 通过css选择器找到按钮，并检查是否找到
         */
        expect(wrapper.vm.hotWords).toEqual([]);
        await wrapper.vm.getRanking();
        expect(wrapper.vm.hotWords).toEqual(['迟先生', '苏大佬']);
    });
});