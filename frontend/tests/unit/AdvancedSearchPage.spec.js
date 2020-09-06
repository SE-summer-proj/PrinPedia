import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import AdvancedSearchPage from "@/pages/AdvancedSearchPage";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    post: () => Promise.resolve({
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

describe("advancedSearchPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("advancedSearchPage", async () => {
        const wrapper = mount(AdvancedSearchPage, {
            store,
            localVue,
        });
        expect(wrapper.vm.advancedDetail.must).toEqual('');
        wrapper.vm.advancedDetail = {
            must: '1',
            should: '2',
            mustNot: '3',
            mustTotal: '4',
            mustTitle: '5'
        }
        wrapper.vm.clearCondition();
        expect(wrapper.vm.advancedDetail.must).toEqual('');
        await wrapper.vm.advancedSearch();
        expect(wrapper.vm.searchResults[1]).toEqual('test2');
    });
});