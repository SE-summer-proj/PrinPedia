****This is the backend app of this project.****
The app is created using springboot.

Run "BackendApplication" to run this backend.
It runs on localhost:8080 by default.

****example****
**User registration**
url: http://localhost:8080/user/register
method: post
body:
{
    "username": "user0",
    "password": "password",
    "email": "123@123.com"
}
response: nothing

**User login**
url: http://localhost:8080/user/login
method: post
body:
{
    "username": "user0",
    "password": "password"
}
response:
{
    "userType": 1,
    "username": "user0"
}
note: if login fail, "userType" is 0; "userType" will be 1 if this is a normal user

**rank**
url: http://localhost:8080/rank
method: get
response:
[
    {
        "change": 5,
        "type": "教育",
        "word": "上海交通大学"
    }
]

**recommend**
url: http://localhost:8080/recommend?userId=?
method: get
response:
[
    "哈哈哈",
    "嘻嘻嘻"
]

**search**
url: http://localhost:8080/search?keyword=?
method: get
response:
[
    "第一条结果",
    "第二条结果",
    "The third item"
]

