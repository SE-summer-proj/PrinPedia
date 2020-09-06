import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import EditPage from "@/pages/EditPage";

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

const $message = {
    info: () => { k = k+1 },
}

describe("editPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("editPage", async () => {
        const wrapper = mount(EditPage, {
            store,
            localVue,
            mocks:{
                $route,
                $message
            }
        });
        await wrapper.vm.getContents();
        expect(wrapper.vm.wikiData).toEqual("test");
        await wrapper.vm.submit();
        expect(k).toEqual(1);
    });
});