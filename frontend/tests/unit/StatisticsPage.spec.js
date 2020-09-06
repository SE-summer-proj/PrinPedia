import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import StatisticsPage from "@/pages/StatisticsPage";

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

describe("statisticsPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("statisticsPage", async () => {
        const wrapper = mount(StatisticsPage, {
            store,
            localVue,
            mocks: {
                $message,
            }
        });
        wrapper.vm.userTable.period = [
            '',
            ''
        ]
        await wrapper.vm.getEntry();
        expect(wrapper.vm.entryTable.tableData[0]).toEqual('test1');
        await wrapper.vm.getDaily();
        expect(wrapper.vm.entryTable.tableData[1]).toEqual('test2');
        await wrapper.vm.getUser();
        expect(wrapper.vm.entryTable.tableData[0]).toEqual('test1');
        await wrapper.vm.getSearch();
        expect(wrapper.vm.entryTable.tableData[1]).toEqual('test2');
    });
});