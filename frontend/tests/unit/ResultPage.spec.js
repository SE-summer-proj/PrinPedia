import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import ResultPage from "@/pages/ResultPage";
import Vuex from "vuex";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: [
                {
                    title: "test",
                    summary: "text",
                }
            ],
        },
        status: 200,
    }),
    post: () => Promise.resolve({
        data: {
            status: 0,
        },
        status: 200,
    }),
}));

const $route = {
    params: {
        keyword: "test",
    }
}

let k = 0;

const $router = {
    push: () => { k= k+1; },
}

describe("resultPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("resultPage", async () => {
        const wrapper = mount(ResultPage, {
            store,
            localVue,
            mocks:{
                $route,
                $router,
            }
        });
        await wrapper.vm.search();
        expect(wrapper.vm.searchResults[0].title).toEqual("test");
        await wrapper.vm.createEntry();
        expect(k).toEqual(1);
    });
});