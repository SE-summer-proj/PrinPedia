import {createLocalVue, mount} from "@vue/test-utils";
import SearchBar from "@/components/SearchBar.vue"
import ElementUI from "element-ui";

const localVue = createLocalVue();
localVue.use(ElementUI);

let testRouter = 'local/';
const $router = {
    push: (string) => {
        testRouter = testRouter + string;
    }
};

describe("searchBar", () => {
    it("searchBar", () => {
        const wrapper = mount(SearchBar, {
            localVue,
            mocks: {
                $router,
            },
        });
        /**
         * 检查data的初始状态
         */
        const btn = wrapper.find("#search-button");
        expect(btn.exists()).toBeTruthy();
        wrapper.vm.searchKeyword = 'test';
        wrapper.vm.search();
        expect(testRouter).toEqual('local//result/test');
    });
});