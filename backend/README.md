# This is the backend app of this project

The app is created using springboot.

Run "BackendApplication" to run this backend.
It runs on localhost:8080 by default.

**If you want to run this app on your local machine, please read 
"Test and Run" in this file first.**

## API

Now API document will be published through Postman. Only some special cases which
have not been covered in Postman document yet would be stated here.

Postman document URL: https://documenter.getpostman.com/view/11974581/T1DiHg68

The above API document was updated on 7.23. If you have any questions, please contact
SHR for more details.

## Test and Run

If you want to run this app successfully, please check following points previously.

1. You must check ./src/main/java/com.prinpedia.backend/resources/
application.properties. As you can see in this file, we use 4 different DBs in this
app: MySQL, MongoDB, ElasticSearch and Neo4j. Please configure the urls of these DBs
to ensure successful connection. Besides, please ensure there is a schema named
"prinpedia" in your MySQL and a database named "prinpedia" in your MongoDB.
(If you don't have them, please create them first or do some configuration job in
"application.properties")
Note: You **DO NOT** need to create tables or documents manually, since they will be
created automatically.

2. You should configure HanLP next. Please check 
./src/main/java/com.prinpedia.backend/resources/hanlp.properties.
You only need to configure the "root" property in this file.
Please download the HanLP dictionary and change the root path to the path of the
downloaded dictionary.
You can download HanLP dictionary at
http://nlp.hankcs.com/download.php?file=data

3. You'd better insert a few entries into databases before you run this app.
The example entry data file ./entries.txt is in this folder, which is very small(only 
contains about 70 entries). To insert these entries, you should navigate to
./src/text/java/com.prinpedia.backend/dataProcess/InitEntryWikiText.java first.
You must configure the file path of ./entries in the class. Then run this class
(by clicking the button on the left of the classname or press Ctrl+Shift+F10).
This action will insert data into MongoDB, Elasticsearch and Neo4j simultaneously. 
Second, you should navigate to
./src/text/java/com.prinpedia.backend/dataProcess/InitEntryRelation.java.
Also, you must configure the file path of ./relations, which contains some
relations between entries. Then run this class, and all the relations will be inserted
into Neo4j. Please don't run other classes in package "dataProcess" if you don't
know what you are doing exactly.

4. **Please skip this step now.**
After above configurations, you should navigate to
./src/text/java/com.prinpedia.backend/BackendApplicationTests.java and
run this class. This action will perform all the tests automatically.
If all tests are passed, congratulations! If not, please contact me.

5. After you pass all tests, you can simply run this app and test APIs.

## Authentication and authorization

This app use SpringSecurity to implement authentication and authorization.

Some resources(or websites) are not accessible if the user don't own 
authentication. If you send a request to such resources without authentication,
you will get the following response.

    {
        "message": "No authentication"
        "status": -1
    }

For such resources, you can access them only when you have logged in before.
If you log out by sending a request to "/logout",
you are not able to access them until you log in again.

Some resources(or websites) are not accessible if the user don't own specific
authorities. If you send a request to such resources without those authorities,
you will get the following response.

    {
        "timestamp": "2020-07-23T02:53:33.567+00:00",
        "status": 403,
        "error": "Forbidden",
        "message": "",
        "path": "/recommend"
    }

## How to store entries

Entries' details are stored in mongoDB.

If you want to transfer entries' data from files to mongoDB.
Please make sure the data in files is formatted before performing insertion.
You should configure the path of your datasource file on your own.

Here is the data structure in mongoDB's "entry" collection.

    {
        "title": "This is the title of the entry",
        "wikiText": "Wiki markup language"
    }

Besides, Elasticsearch and Neo4j are used to store some other information about
entries.

Entries' titles and summaries are stored in Elasticsearch so that they can be
searched faster.Here is the data structure in Elasticsearch's "entry" index. 
(You can view "index"as "collection" in MongoDB)

    {
        "entryTitle": "This is the title of the entry",
        "entrySummary": "This is the summary of the entry"
    }
    
Relationships between entries are stored in Neo4j. Here is the data structure
in Neo4j.

    EntryNode:
    {
        "index": "This is the inner index of the entry",
        "title": "This is the title of the entry"
    }
    
    EntryRelation:
    {
        "start": EntryNode,
        "end": EntryNode
    }
        
## Elasticsearch

Now, we use Elasticsearch to store entries' titles and summaries.

Elasticsearch is a kind of very powerful distributed and RESTful-style engine,
which could support advanced searching.

You can go to the following website to learn more about Elasticsearch.

https://www.elastic.co/cn/elasticsearch/

https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html

## Neo4j

Now, we use Elasticsearch to store entries' relationships.

Neo4j is a native graph database, built from the ground up to leverage 
not only data but also data relationships. Neo4j connects data as it’s stored.

You can go to the following websites to learn more about Elasticsearch.

https://neo4j.com/

https://neo4j.com/docs/