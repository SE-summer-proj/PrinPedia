# 苏浩然的工作日志

此文件为苏浩然的工作记录

## 7.7

使用springboot搭建了基本的后端，并且实现了user表的OR映射以及基本的crud操作

具体工作详见backend-basic分支

## 7.8

进一步完善了对user的操作，并实现了与前端通信的接口

具体工作详见backend-basic分支

## 7.9

根据前端的要求对接口进行了改进

实现将爬取的词条数据存储到数据库中并进行简单的搜索

具体工作详见backend-basic分支

## 7.10

学习了Elasticsearch相关知识

进一步改进了搜索算法，将词条的词条名和摘要存储在了Elasticsearch中，以便搜索

具体工作详见backend-basic分支

## 7.13

学习了JUnit等单元测试工具，在后端中加入了与用户相关操作的单元测试

学习了一些NLP API，包括百度NLP以及HanLP，由于百度NLP需要通过发送网络请求来得到分析结果，而HanLP
则可以在本地进行分析，故初步尝试了HanLP

将HanLP相关依赖引入项目，并对此API进行了简单的测试

具体工作详见backend-basic分支

## 7.14

完善了对后端Controller层以及Service层的单元测试

将HanLP集成到search方法中，进行分词，改善了搜索

学习了Spring Security的基本配置方案，基本掌握了在前后端分离框架下的认证授权的方法（目前尚未集成到后端）

具体工作详见backend-basic分支

## 7.15

在后端实现了比较完整的认证授权， 对数据库中的密码进行了加密处理

更新了单元测试，并对单元测试做了suite，实现自动化测试

具体工作详见backend-basic分支
