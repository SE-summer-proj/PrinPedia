import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import TagSheet from "@/components/TagSheet";

const localVue = createLocalVue();
localVue.use(ElementUI);

jest.mock("axios", () => ({
    get: () => Promise.resolve({
        data: {
            status: 0,
            extraData: [
                "test233",
            ]
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

const $refs = {
    saveTagInput: () => { k = k+1 },
}

describe("tagSheet", () => {
    it("tegSheet", async () => {
        let tags = [
            'test1',
            'test2'
        ]
        const wrapper = mount(TagSheet, {
            propsData: { tags },
            localVue,
            mocks:{
                $refs
            }
        });
        expect(wrapper.vm.getIndex(2, 2)).toEqual(5);
        wrapper.vm.handleClose(1, 1);
        expect(wrapper.vm.tags[0]).toEqual('test2');
        await wrapper.vm.handleClick(1, 1);
        expect(wrapper.vm.results[0]).toEqual('test233');
        wrapper.vm.showInput();
        await wrapper.vm.$nextTick();
        expect(wrapper.vm.inputVisible).toEqual(true);
        await wrapper.vm.handleInputConfirm();
        expect(wrapper.vm.inputVisible).toEqual(false);

    });
});