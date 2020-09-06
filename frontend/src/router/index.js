import Vue from 'vue';
import VueRouter from 'vue-router';
import SearchPage from "@/pages/SearchPage";
import EntryPage from "@/pages/EntryPage";
import ResultPage from "@/pages/ResultPage";
import UserPage from "@/pages/UserPage";
import EditPage from "@/pages/EditPage";
import LoginPage from "@/pages/LoginPage";
import UserLogDetailPage from "@/pages/UserLogDetailPage";
import AdminPage from "@/pages/AdminPage";
import ComparePage from "@/pages/ComparePage";
import StatisticsPage from "@/pages/StatisticsPage";
import FeedbackAdminPage from "@/pages/FeedbackAdminPage";
import FeedbackPage from "@/pages/FeedbackPage";
import AdvancedSearchPage from "../pages/AdvancedSearchPage";
import Graph from "../components/Graph";
import TagPage from "@/pages/TagPage";

Vue.use(VueRouter);

const routes = [
    {
        path: '/index',
        component: SearchPage
    },
    {
        path: '/graph',
        component: Graph
    },
    {
        path: '/',
        redirect: '/index'
    },
    {
        path: '/entry/:entryName',
        component: EntryPage
    },
    {
        path: '/advanced',
        component: AdvancedSearchPage
    },
    {
        path: '/result/:keyword',
        component: ResultPage
    },
    {
        path: '/user',
        component: UserPage
    },
    {
        path: '/edit/:entryName',
        component: EditPage
    },
    {
        path: '/login',
        component: LoginPage
    },
    {
        path: '/userLogDetail/:userLogId',
        component: UserLogDetailPage
    },
    {
        path: '/admin',
        component: AdminPage
    },
    {
        path: '/compare/:logId',
        component: ComparePage
    },
    {
        path: '/feedback',
        component: FeedbackPage
    },
    {
        path: '/stat',
        component: StatisticsPage
    },
    {
        path: '/admin/feedback',
        component: FeedbackAdminPage
    }
];

export const router = new VueRouter({
    routes: routes
});
