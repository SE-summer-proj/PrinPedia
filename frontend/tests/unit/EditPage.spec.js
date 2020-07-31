import {mount, createLocalVue} from "@vue/test-utils";
import ElementUI from 'element-ui';
import Mock from '../../mock';
import Vuex from 'vuex';

// import test_routes from '../test_routes';
import test_store from "../test_store";

import EditPage from "@/pages/EditPage";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Mock);
localVue.use(Vuex);

const defaultStore = new Vuex.Store(test_store);

describe('edit page', () => {
    let store = defaultStore;
    beforeEach(() => {
        store = defaultStore;
    });
    it('should show append dialog when clicking on button', async () => {
        const wrapper = await mount(EditPage, {
            store,
            localVue,
            mocks: {
                $route: {
                    path: '/edit/:keyword',
                    params: {
                        keyword: 'AirCon'
                    }
                }
            }
        });
        expect(wrapper.vm.appendDialogVisible).toBe(false);
        const appendBtn = wrapper.find('#append-btn');
        expect(appendBtn.exists()).toBeTruthy();
        appendBtn.trigger('click');
        expect(wrapper.vm.appendDialogVisible).toBe(true);
    })
});
