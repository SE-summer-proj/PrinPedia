# This is the backend app of this project

The app is created using springboot.

Run "BackendApplication" to run this backend.
It runs on localhost:8080 by default.

**If you want to run this app on your local machine, please read 
"Test and Run" in this file first.**

## API

### User registration

url: http://localhost:8080/user/register

method: post

body:

    {
        "username": "user0",  
        "password": "password",
        "mailAddr": "123@123.com"
    }


response: nothing

### User login

url: http://localhost:8080/login

method: post

content-type: application/x-www-form-urlencoded

body:

    username=xxx&password=xxx
    
response:

    {
        "extraData": {
            "userType": 1,
            "username": "user0"
        },
        "message": "Login succeess",
        "status": 0
    }

If login fails:

response: 

    {
        "message": "Login failure",
        "status": -1
    }

### User Logout

url: http://localhost:8080/logout

method: get

response:

    {
            "message": "Logout success",
            "status": 0
    }  

### Rank

url: http://localhost:8080/rank

method: get

response:

    {
        "extraData": [
            {
                "change": 5,
                "type": "教育",
                "word": "上海交通大学"
            },
            {
                "change": -1,
                "type": "卖弱",
                "word": "迟先生"
            }
        ],
        "message": "success",
        "status": 0
    }

### Recommend

url: http://localhost:8080/recommend?userId=?

method: get

response:

    {
        "extraData": [
            "哈哈哈",
            "嘻嘻嘻"
        ],
        "message": "success",
        "status": 0
    }

### Search

Note: searching result would have 3 types. 

First, if the keyword is the same (not required exactly same)
as any entries' title, the response would only contain that **one** title.
"SCIencE" can match "science" in this type.

Second, if cannot find a matched entry, the searching will look up all entries' titles
and summaries to find some possible entries. That is, those returned entries contain
some keyword in their title or summary. 

Third, cannot find any possible entry and fail this searching, return none.


Here are examples of success search and failed search respectively:

url: http://localhost:8080/search?keyword=?

method: get

succeed:

response:

    {
        "extraData": [
            "Science"
        ],
        "message": "success",
        "status": 0
    }

possible(or half succeed):

response:

    {
        "extraData": [
            "History",
            "Geography"
        ],
        "message": "no exactly matched entry, find suggestions",
        "status": 1
    }

fail(or no match):

response:

    {
        "extraData": [
            "第一条结果",
            "第二条结果",
            "The third item"
        ],
        "message": "cannot find matched entry",
        "status": -1
    }

### Fetch entry details

url: http://localhost:8080/entry?entryName=?

method: get

Note: response may be very long, following is just an example.

response:

    {
        "extraData": {
            "title": "This is the title of the entry",
            "summary": "This is summary of the entry",
            "content": [
                {
                    "label": "first",
                    "text": "this is the first"
                },
                {
                    "label"： "2 second",
                    "text": "this is the second",
                    "children": [
                        {
                            "label": "2.1 lalala",
                            "text": "this is 2.1"
                        },
                        {
                            "label": "2.2 hahaha",
                            "text": "this is 2.2",
                            "children": [
                                {
                                    "label": "2.2.1 xixixi",
                                    "text": "this is 2.2.1"
                                }
                            ]
                        }
                    ]
                }
            ]
        }
        "message": "fetch detail success",
        "status": 0                   
    }
    
### Create entry

url: http://localhost:8080/create

method: post

body:

    {
        "keyword": "New entry title"
    }
    
response:

    {
        "extraData": {
            "title": "New entry title",
            "summary": "",
            "content": []
        }
        "status": 0,
        "message": "Successfully created"
    }

### Edit entry

url: http://localhost:8080/edit

method: post

body:

    {
        "title": "Edit entry title",
        "summary": "Summary",
        "content": [
            {
                "label": "first",
                "text": "text"
            },
            {
                "label"： "second",
                "text": "text",
                "children": [
                    {
                        "label": "2.1",
                        "text": "text 2.1"
                    },
                    {
                        "label": "2.2",
                        "text": "text2.2",
                        "children": [
                            {
                                "label": "2.2.1",
                                "text": "text2.2.1"
                            }
                        ]
                    }
                ]
            }
        ]
    }
    
response:

    {
        "status": 0,
        "message": "Successfully edited"
    }

## Test and Run

If you want to run this app successfully, please check following points previously.

1. You must check ./src/main/java/com.prinpedia.backend/resources/
application.properties. As you can see in this file, we use 3 different DBs in this
app: MySQL, MongoDB and ElasticSearch. Please configure the urls of these DBs
to ensure successful connection. Besides, please ensure there is a schema named
"prinpedia" in your MySQL and a database named "prinpedia" in your MongoDB.
(If you don't have them, please create them first or do some configuration job in
"application.properties")
Note: You **DO NOT** need to create tables or documents manually, since they will be
created automatically.

2. You'd better insert a few entries into databases before you run this app.
The example entry data file ./data.txt is in this folder, which is very small(only 
contains about 10 entries). To insert these entries, you should navigate to
./src/text/java/com.prinpedia.backend/dataProcess/initEntryData.java first.
You must configure the file path of ./data in the class. Then run this class
(by clicking the button on the left of the classname or press Ctrl+Shift+F10).
This action will insert data into MongoDB. Second, you should navigate to
./src/text/java/com.prinpedia.backend/dataProcess/InitElasticEntry.java.
Run this class, and all the entries in MongoDB will be inserted into Elasticsearch.

3. After above configuration, you should navigate to
./src/text/java/com.prinpedia.backend/BackendApplicationTests.java and
run this class. This action will perform all the tests automatically.
If all tests are passed, congratulations! If not, please contact me.

4. After you pass all tests, you can simply run this app and test APIs.

## Authentication and authorization

This app use SpringSecurity to implement authentication and authorization.

Some resources(or websites) are not accessible if the user don't own specific 
authorities. If you send a request to such resources without authentication, you
will get the following response.

    {
        "message": "No authentication"
        "status": -1
    }

For such resources, you can access them only when you have logged in before.
If you log out by sending a request to "/logout",
you are not able to access them until you log in again.

## How to store entries

Entries' details are stored in mongoDB.

If you want to transfer entries' data from files to mongoDB, you can call the method
in class "src/test/java/com.prinpedia.backend/dataProcess/initEntryData.java".
Please make sure the data in files is formatted before performing insertion.You should
configure the path of your datasource file on your own.

Here is the data structure in mongoDB's "entry" collection.

    {
        "title": "This is the title of the entry",
        "summary": "This is summary of the entry",
        "content": [
            {
                "label": "1 first"
            },
            {
                "label"： “2 second",
                "children": [
                    {
                        "label": "2.1 lalala"
                    },
                    {
                        "label": "2.2 hahaha",
                        "children": [
                            {
                                "label": "2.2.1 xixixi"
                            }
                        ]
                    }
                ]
            }
        ]
        "sectionList": [
            {
                "sectionTitle": "first",
                "sectionText": "this is first..."
            },
            {
                "sectionTitle": "second",
                "sectionText": "this is second..."
            },
            {
                "sectionTitle": "lalala",
                "sectionText": "this is 2.1"
            },
            {
                "sectionTitle": "hahaha",
                "sectionText": "this is 2.2"
            }
            {
                "sectionTitle": "xixixi",
                "sectionText": "this is 2.2.1"
            }
        ]
    }

Note: "content" attribute stores an array of documents, which may recursively own 
an array
of documents("children"). In fact, you can view it as a tree structure. "sectionList"
attribute is an array of documents, which are just simple documents with two
attributes.

## Elasticsearch

Now, we use Elasticsearch to store entries' titles and summaries.

Elasticsearch is a kind of very powerful distributed and RESTful-style engine,
which could support advanced searching.

You can go to the following website to learn more about Elasticsearch.

https://www.elastic.co/cn/elasticsearch/

https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html

