import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import FeedbackAdminPage from "@/pages/FeedbackAdminPage";

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

describe("feedbackAdminPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("feedbackAdminPage", async () => {
        const wrapper = mount(FeedbackAdminPage, {
            store,
            localVue,
        });
        await wrapper.vm.getTableData();
        expect(wrapper.vm.replied[1]).toEqual('test2');
    });
});