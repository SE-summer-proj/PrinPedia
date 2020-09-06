const apiUrl = 'http://localhost:9090';
export const Constants = {
    loginUrl: apiUrl + '/login',
    logoutUrl: apiUrl + '/logout',
    registerUrl: apiUrl + '/user/register',

    recommendUrl: apiUrl + '/recommend',
    rankingUrl: apiUrl + '/rank',
    searchUrl: apiUrl + '/search',
    advancedSearchUrl: apiUrl + '/search/advanced',
    graphUrl: apiUrl + '/relation',
    createUrl: apiUrl + '/create',

    entryUrl: apiUrl + '/entry',
    entryEditUrl: apiUrl + '/entry/edit',
    editUrl: apiUrl + '/entry/edit/request',
    userLogUrl: apiUrl + '/entry/edit/userLog',
    editDetailUrl: apiUrl + '/entry/edit/detail',
    adminLogUrl: apiUrl + '/entry/edit/adminLog',
    examineUrl: apiUrl + '/entry/edit/examine',
    requestUrl: apiUrl + '/entry/edit/request',

    adminUrl: apiUrl + '/admin',
    usersUrl: apiUrl + '/admin/allUser',
    disableUrl: apiUrl + '/admin/ability',

    collectionUrl: apiUrl + '/collection',
    checkCollectionUrl: apiUrl + '/collection/query',
    addCollectionUrl: apiUrl + '/collection/add',
    removeCollectionUrl: apiUrl + '/collection/remove',
    userCollectionUrl: apiUrl + '/collection/user',

    msgUrl: apiUrl + '/message',
    adminGetMsgUrl: apiUrl + '/message/admin',
    userGetMsgUrl: apiUrl + '/message/user',
    replyMsgUrl: apiUrl + '/message/reply',
    createMsgUrl: apiUrl + '/message/create',

    statUrl: apiUrl + '/statics',
    entryStatUrl: apiUrl + '/statics/entry',
    dailyStatUrl: apiUrl + '/statics/entryTitle',
    searchStatUrl: apiUrl + '/statics/search',
    userStatUrl: apiUrl + '/statics/user',

    tagUrl: apiUrl + '/tag',
    tagOfEntryUrl: apiUrl + '/tag/title',
    entryOfTagUrl: apiUrl + '/tag/tagName',
    allTagUrl: apiUrl + '/tag/allTag',
    createTagUrl: apiUrl + '/tag/create',
    editTagUrl: apiUrl + '/tag/edit'
};
