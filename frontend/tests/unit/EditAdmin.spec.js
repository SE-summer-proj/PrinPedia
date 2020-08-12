import {createLocalVue, mount} from "@vue/test-utils";
import EditAdmin from "@/components/EditAdmin.vue"
import ElementUI from "element-ui";

const localVue = createLocalVue();
localVue.use(ElementUI);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: ['迟先生', '苏大佬']
        },
        status: 200,
    })
}));

describe("EditAdmin", () => {
    it("editAdmin", async () => {
        const wrapper = mount(EditAdmin, { localVue });
        expect(wrapper.vm.activeName).toEqual('unchecked');
        expect(wrapper.vm.checked).toEqual([]);
        expect(wrapper.vm.unchecked).toEqual([]);
        await wrapper.vm.getData();
        expect(wrapper.vm.checked).toEqual(['迟先生', '苏大佬']);
        expect(wrapper.vm.unchecked).toEqual(['迟先生', '苏大佬']);
        expect(wrapper.vm.getStatusDesc(0)).toEqual('未审核');
        expect(wrapper.vm.getStatusDesc(1)).toEqual('已通过');
        expect(wrapper.vm.getStatusDesc(2)).toEqual('未通过');
    });
});