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

Vue.use(VueRouter);

const routes = [
    {
        path: '/index',
        component: SearchPage
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
    }
];

export const router = new VueRouter({
    routes: routes
});
