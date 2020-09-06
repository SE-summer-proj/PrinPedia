import {createLocalVue, mount} from "@vue/test-utils";
import UserPage from "@/pages/UserPage.vue";
import ElementUI from "element-ui";
import Vuex from "vuex"

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: [{
                type: "test"
            }]
        },
        status: 200,
    }),
    post: () => Promise.resolve({
        data: {
            status: 0,
        },
        status: 200,
    })
}));

describe("userPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: {
                    username: "test",
                    mailAddr: "test@test.test",
                    birthday: "2002-02-02",
                    userType: "ROLE_ADMIN"
                }
            },
        })
    })
    it("userPage", async () => {
        const wrapper = mount(UserPage, {
            store,
            localVue,
        });
        /**
         * 检查data的初始状态
         */
        await wrapper.vm.switchCollectionStat("test");
        expect(wrapper.vm.collection).toEqual([{ type: "test" }]);
        await wrapper.vm.getUserLog();
        expect(wrapper.vm.userLog).toEqual([{ type: "test" }]);
        expect(wrapper.vm.getRowClassName({row: 0})).toEqual('unchecked');
        expect(wrapper.vm.getRowClassName({row: 1})).toEqual('passed');
        expect(wrapper.vm.getRowClassName({row: 2})).toEqual('rejected');
        expect(wrapper.vm.getRowClassName({row: 3})).toEqual('status');
    });
});