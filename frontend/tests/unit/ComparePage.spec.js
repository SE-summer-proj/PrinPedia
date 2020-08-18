import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import ComparePage from "@/pages/ComparePage";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: "test",
        },
        status: 200,
    }),
    post: () => Promise.resolve({
        data: {
            status: 0,
            message: "test",
        },
        status: 200,
    }),
}));

let k = 0;

const $route = {
    params: {
        keyword: "keyword",
    },
};

const $router = {
    push: () => { k = k+1 },
};

describe("comparePage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("comparePage", async () => {
        const wrapper = mount(ComparePage, {
            store,
            localVue,
            mocks:{
                $route,
                $router
            }
        });
        await wrapper.vm.getDetail();
        expect(wrapper.vm.logDetail).toEqual("test");
        await wrapper.vm.getCurrent();
        expect(wrapper.vm.logDetail).toEqual("test");
        await wrapper.vm.submit(true);
        expect(k).toEqual(1);
    });
});