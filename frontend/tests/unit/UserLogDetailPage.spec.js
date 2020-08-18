import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import UserLogDetailPage from "../../src/pages/UserLogDetailPage";
import Vuex from "vuex";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: {
                id: 1,
                date: "2002-02-02",
                title: "test",
                wikiText: "text",
                status: 0,
            },
        },
        status: 200,
    }),
}));

const $route = {
    params: {
        userLogId: 1,
    }
}

describe("userLogDetailPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("userLogDetailPage", async () => {
        const wrapper = mount(UserLogDetailPage, {
            store,
            localVue,
            mocks:{
                $route,
            }
        });
        await wrapper.vm.getWikiText();
        expect(wrapper.vm.logDetail.id).toEqual(1);
    });
});