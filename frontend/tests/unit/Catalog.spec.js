import {createLocalVue, mount} from "@vue/test-utils";
import Catalog from "@/components/Catalog.vue";
import ElementUI from "element-ui";
import Vuex from "vuex"
import {contents} from "../test_data"

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Vuex);

describe("catalog", () => {
    it("init state", () => {
        const wrapper = mount(Catalog, {
            localVue,
        });
        /**
         * 检查data的初始状态
         */
        expect(wrapper.vm.defaultProps.children).toEqual('children');
        expect(wrapper.vm.defaultProps.label).toEqual('label');
    });
    it("get title", () => {
        const wrapper = mount(Catalog, {
            localVue,
        });
        expect(wrapper.vm.getTitle("test", 1, 2, 3)).toEqual("2.3.4 test");
    })
    it("get content", () => {
        const wrapper = mount(Catalog, {
            localVue,
        });
        // console.log(contents("test").catalog[0].children);
        expect(wrapper.vm.formatTitle(contents("test")).summary).toEqual("Summary text of test");
    })
});