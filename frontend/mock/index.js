import Mock from 'mockjs';
import {entryUrl, loginUrl, logoutUrl, rankingUrl, recommendUrl, registerUrl, searchUrl} from "@/utils/constants";
import {users, entries} from "../tests/test_data";
import {getParamValue} from '../tests/utils';

Mock.mock(loginUrl, 'post', (options) => {
    const userData = JSON.parse(options.body);
    console.log(userData);
    let index = users.findIndex((item) => {
        return (item.username === userData.username && item.password === userData.password);
    });
    if (index !== -1) {
        return {
            extraData: {
                userType: users[index].userType,
                username: users[index].username
            },
            message: "Login success",
            status: 0
        }
    } else {
        return {
            extraData: null,
            message: "Login failed",
            status: -1
        }
    }
});

Mock.mock(logoutUrl, 'post', () => {
    return {
        message: "Logout success",
        status: 0
    }
});

Mock.mock(registerUrl, 'post', (configs) => {
    const username = configs.body.username;
    if (users.findIndex((user) => user.username === username))
        return {
            message: "Register failed. Username duplicated.",
            status: -1
        }
    else return {
        message: "Register success",
        status: 0
    }
});

Mock.mock(rankingUrl, 'get', () => {
    return {
        extraData: [
            {
                change: 5,
                type: "教育",
                word: "上海交通大学"
            },
            {
                change: -1,
                type: "卖弱",
                word: "迟先生"
            }
        ],
        message: "success",
        status: 0
    }
});

Mock.mock(new RegExp(recommendUrl + '.*'), 'get', () => {
    return {
        extraData: ["哈哈哈", "嘻嘻嘻"],
        message: "success",
        status: 0
    }
});

Mock.mock(new RegExp(searchUrl + '.*'), 'get', (options) => {
    const url = options.url;
    const keyword = getParamValue(url);
    if (entries.findIndex((entry) => entry.title === keyword) !== -1) {
        return {
            extraData: [keyword],
            message: "success",
            status: 0
        };
    } else return {
        extraData: ["第一条结果", "第二条结果", "The third item"],
        message: "cannot find matched entry",
        status: -1
    }
});

Mock.mock(new RegExp(entryUrl + '.*'), 'get', (options) => {
    const url = options.url;
    const entryName = getParamValue(url);
    const index = entries.findIndex((entry) => entry.title === entryName);
    if (index !== -1) {
        return {
            extraData: entries[index],
            message: "fetch detail success",
            status: 0
        }
    } else return {
        extraData: null,
        message: 'entry does not exist',
        status: 0
    }
})

export default Mock;
