import {mount, createLocalVue} from "@vue/test-utils";
import ElementUI from 'element-ui';
import Mock from '../../mock';
import Vuex from 'vuex';
import test_store from "../test_store";
import ResultPage from "@/pages/ResultPage";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Mock);
localVue.use(Vuex);

const defaultStore = new Vuex.Store(test_store);

describe('result page', () => {
    let store = defaultStore;
    beforeEach(() => {
        store = defaultStore;
    });
    it('should show the result if entry exists', () => {
        const wrapper = mount(ResultPage, {
            localVue,
            store,
            mocks: {
                $route: {
                    path: '/result/:keyword',
                    params: {
                        keyword: 'AirCon'
                    }
                }
            }
        });
        expect(wrapper.vm.$route.params.keyword).toBe('AirCon');
        expect(wrapper.find('.found').exists()).toBe(true);
        expect(wrapper.find('.not-found').exists()).toBe(false);
    });
    it('should show message if entry does not exist', () => {
        const wrapper = mount(ResultPage, {
            localVue,
            store,
            mocks: {
                $route: {
                    path: '/result/:keyword',
                    params: {
                        keyword: 'Foo'
                    }
                }
            }
        });
        expect(wrapper.vm.$route.params.keyword).toBe('Foo');
        expect(wrapper.find('.not-found').exists()).toBe(false);
        expect(wrapper.find('.found').exists()).toBe(true);
    });
});
