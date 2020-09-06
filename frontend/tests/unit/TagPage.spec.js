import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import TagPage from "@/pages/TagPage";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            message: "test",
            extraData: [
                'test1',
                'test2'
            ]
        },
        status: 200,
    }),
}));

describe("tagPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("tagPage", async () => {
        const wrapper = mount(TagPage, {
            store,
            localVue,
        });
        await wrapper.vm.getAllTags();
        expect(wrapper.vm.allTags[0]).toEqual('test1');
    });
});