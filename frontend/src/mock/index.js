import Mock from 'mockjs';
import {loginUrl, logoutUrl, rankingUrl, recommendUrl, registerUrl, searchUrl} from "@/utils/constants";
import {users, entries} from "../../tests/test_data";

Mock.mock(loginUrl, 'post', (options) => {
    const userData = options.body;
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

Mock.mock(registerUrl, 'post', () => null);

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

Mock.mock(recommendUrl, 'get', () => {
    return {
        extraData: ["哈哈哈", "嘻嘻嘻"],
        message: "success",
        status: 0
    }
});

// Mock.mock(searchUrl, 'get', (options) => {
//     const keyword = options.body.keyword;
// })

export default Mock;
