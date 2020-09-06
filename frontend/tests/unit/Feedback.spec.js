import {createLocalVue, mount} from "@vue/test-utils";
import ElementUI from "element-ui";
import Feedback from "@/components/Feedback";

const localVue = createLocalVue();
localVue.use(ElementUI);

jest.mock("axios", () => ({
    post: () => Promise.resolve({
        data: {
            status: 0,
            message: "test",
        },
        status: 200,
    }),
}));

let k = 0;

const $message = {
    info: () => { k = k+1 },
}

describe("feedBack", () => {
    it("feedBack", async () => {
        const wrapper = mount(Feedback, {
            // propsData: { msg },
            localVue,
            mocks:{
                $message
            }
        });
        let scope = {
            row: {
                content: 'test2333',
            }
        }
        expect(wrapper.vm.thumb(scope)).toEqual(scope.row.content);
        await wrapper.vm.submitReply(1);
        expect(k).toEqual(1);
    });
});