import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import FeedbackPage from "@/pages/FeedbackPage";

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

let k = 0;

const $message = {
    info: () => { k = k+1},
};

describe("feedbackPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("feedbackPage", async () => {
        const wrapper = mount(FeedbackPage, {
            store,
            localVue,
            mocks: {
                $message,
            }
        });
        expect(wrapper.vm.thumb('test233')).toEqual('test233');
        await wrapper.vm.getTableData();
        expect(wrapper.vm.tableData[1]).toEqual('test2');
        await wrapper.vm.submit();
        expect(k).toEqual(1);
    });
});