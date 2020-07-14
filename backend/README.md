# This is the backend app of this project

The app is created using springboot.

Run "BackendApplication" to run this backend.
It runs on localhost:8080 by default.

## Example

### User registration

url: http://localhost:8080/user/register

method: post

body:

    {
        "username": "user0",  
        "password": "password",
        "email": "123@123.com"
    }


response: nothing

### User login

url: http://localhost:8080/user/login

method: post

body:

    {
        "username": "user0",
        "password": "password"
    }

response:

    {
        "extraData": {
            "userType": 1,
            "username": "user0"
        },
        "message": "Login succeed",
        "status": 0
    }

Note: if login fail, "userType" is 0; "userType" will be 1 if this is a normal user

If login fails:

response: 

    {
        "extraData": {
            "userType": 0,
            "username": "123"
        },
        "message": "Wrong username or password",
        "status": -1
    }

### rank

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

### recommend

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

### search

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

### fetch entry details

url: http://localhost:8080/entry?entryName=?

method: get

Note: response may be very long, following is just an example.
Note!: catalog

response:

    {
        "extraData": {
            "title": "This is the title of the entry",
            "summary": "This is summary of the entry",
            "content": [
                {
                    "label": "first",
                    "text": "this is the first",
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

