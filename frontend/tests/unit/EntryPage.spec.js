import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Vuex from "vuex";
import EntryPage from "@/pages/EntryPage";
import {Constants} from "@/utils/constants";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

jest.mock("axios", () => ({
    get: (url, params) => {
        if (url === 'http://localhost:9090/entry')
            return Promise.resolve({
                data: {
                    status: 0,
                    extraData:{
                        title: "test",
                        wikiText: "text",
                    },
                },
                status: 200,
            });
        else
            return Promise.resolve({
                data: {
                    status: 0,
                    extraData: true,
                },
                status: 200,
            });
    },
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

describe("entryPage", () => {
    let store;
    beforeEach(() => {
        store = new Vuex.Store({
            state: {
                logged: true,
                userData: { username: "test" }
            }
        })
    })
    it("entryPage", async () => {
        const wrapper = mount(EntryPage, {
            store,
            localVue,
            mocks:{
                $route,
                $message
            }
        });
        await wrapper.vm.getContents();
        expect(wrapper.vm.wikiData.wikiText).toEqual("text");
        await wrapper.vm.getCollectionStat();
        expect(wrapper.vm.isInCollection).toEqual(true);
        await wrapper.vm.switchCollection();
        expect(wrapper.vm.isInCollection).toEqual(false);
    });
});