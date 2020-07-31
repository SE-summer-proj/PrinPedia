import {mount, createLocalVue} from "@vue/test-utils";
import ElementUI from 'element-ui';
import Mock from '../../mock';
import Vuex from 'vuex';
import VueRouter from 'vue-router';

import test_routes from '../test_routes';
import test_store from "../test_store";
import EntryPage from "@/pages/EntryPage";
import Catalog from "@/components/Catalog";
import MainText from "@/components/MainText";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Mock);
localVue.use(Vuex);
localVue.use(VueRouter);

const defaultStore = new Vuex.Store(test_store);
const defaultRouter = new VueRouter(test_routes);

describe('entry page', () => {
    let store = defaultStore;
    let router = defaultRouter;
    beforeEach(() => {
        store = defaultStore;
        router = defaultRouter;
    });
    const wrapper = mount(EntryPage, {router, store, localVue});
    it('should show catalog', () => {
        const catalog = wrapper.findComponent(Catalog);
        expect(catalog.exists()).toBe(true);
    });
    it('should show main text', () => {
        const mainText = wrapper.findComponent(MainText);
        expect(mainText.exists()).toBe(true);
    });
    it('should trigger edit', () => {
        const editBtn = wrapper.find('#edit-btn');
        expect(editBtn.exists()).toBe(true);
        editBtn.trigger('click');
    });
});
