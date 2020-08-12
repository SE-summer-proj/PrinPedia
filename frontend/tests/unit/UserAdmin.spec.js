import {createLocalVue, mount} from "@vue/test-utils";
import UserAdmin from "@/components/UserAdmin.vue"
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
    }),
    post: () => Promise.resolve({
        data: {
            status: 0,
            extraData: ['迟先生', '苏大佬']
        },
        status: 200,
    }),
}));

describe("UserAdmin", () => {
    it("userAdmin", async () => {
        const wrapper = mount(UserAdmin, {
            localVue,
        });
        expect(wrapper.vm.users).toEqual([]);
        await wrapper.vm.getAllUsers();
        expect(wrapper.vm.users).toEqual(['迟先生', '苏大佬']);
        let row = {
            username : '迟先生',
            enabled: true,
        }
        await wrapper.vm.changeDisableState(row);
        expect(wrapper.vm.users).toEqual(['迟先生', '苏大佬']);
    });
});