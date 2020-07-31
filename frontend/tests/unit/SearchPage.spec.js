import {mount, createLocalVue} from "@vue/test-utils";
import ElementUI from 'element-ui';
import Mock from '../../mock';
import Vuex from 'vuex';
import VueRouter from 'vue-router';

import SearchBar from "@/components/SearchBar";
import test_routes from '../test_routes';
import test_store from "../test_store";
import SearchPage from "@/pages/SearchPage";
import Recommend from "@/components/Recommend";
import Ranking from "@/components/Ranking";

const localVue = createLocalVue();
localVue.use(ElementUI);
localVue.use(Mock);
localVue.use(Vuex);
localVue.use(VueRouter);

const defaultStore = new Vuex.Store(test_store);
const defaultRouter = new VueRouter(test_routes);

describe("search page", () => {
    let store = defaultStore;
    let router = defaultRouter;
    beforeEach(() => {
        store = defaultStore;
        router = defaultRouter;
    });
    const wrapper = mount(SearchPage, {router, store, localVue});
    it('should search', () => {
        const searchBar = wrapper.findComponent(SearchBar);
        expect(searchBar.exists()).toBeTruthy();
        const input = searchBar.find("#search-input");
        expect(input.exists()).toBeTruthy();
        input.setValue("AirCon");
        expect(searchBar.vm.searchKeyword).toEqual("AirCon");
        const btn = searchBar.find("#search-button");
        expect(btn.exists()).toBeTruthy();
        btn.trigger("click");
    });

    it('should show recommended entries', () => {
        const recommend = wrapper.findComponent(Recommend);
        expect(recommend.exists()).toBeTruthy();
        // expect(recommend.vm.entries).toEqual(["哈哈哈", "嘻嘻嘻"]);
    });

    it('should show ranking', async () => {
        const ranking = wrapper.findComponent(Ranking);
        expect(ranking.exists()).toBeTruthy();
        // expect(ranking.vm.hotWords).toEqual([
        //     {
        //         change: 5,
        //         type: "教育",
        //         word: "上海交通大学"
        //     },
        //     {
        //         change: -1,
        //         type: "卖弱",
        //         word: "迟先生"
        //     }
        // ]);
    });
});
