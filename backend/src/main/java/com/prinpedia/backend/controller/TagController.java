package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.EntryInfo;
import com.prinpedia.backend.entity.Tag;
import com.prinpedia.backend.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/tag", produces = "text/plain;charset=UTF-8")
public class TagController {
    @Autowired
    private TagService tagService;

    private Logger logger = LoggerFactory.getLogger(TagController.class);

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/title")
    public String getTagByTitle(@RequestParam(value = "title")String title) {
        logger.info("Receive GET request on '/tag/title'");
        logger.debug("GET request on '/tag/title' with params: " +
                "'title'=" + title);
        List<Tag> tagList = tagService.findTagByEntry(title);
        JSONObject response = new JSONObject();
        JSONArray tagArray = new JSONArray();
        if(tagList == null) tagList = new ArrayList<>();
        for(Tag tag : tagList) {
            tagArray.add(tag.getTagName());
        }
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", tagArray);
        logger.debug("Response to GET request on '/tag/title' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/tag/title' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/tagName")
    public String getEntryByTag(@RequestParam(value = "tagName")String tagName) {
        logger.info("Receive GET request on '/tag/tagName'");
        logger.debug("GET request on '/tag/tagName' with params: " +
                "'tagName'=" + tagName);
        List<EntryInfo> entryInfoList = tagService.findEntryByTag(tagName);
        JSONObject response = new JSONObject();
        JSONArray entryArray = new JSONArray();
        if(entryInfoList == null) entryInfoList = new ArrayList<>();
        for(EntryInfo entryInfo : entryInfoList) { entryArray.add(entryInfo.getTitle()); }
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", entryArray);
        logger.debug("Response to GET request on '/tag/tagName' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/tag/tagName' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/allTag")
    public String getAllTags() {
        logger.info("Receive GET request on '/tag/allTag'");
        logger.debug("GET request on '/tag/allTag' with no param");
        List<Tag> tagList = tagService.findAllTags();
        JSONObject response = new JSONObject();
        JSONArray tagArray = new JSONArray();
        for(Tag tag : tagList) {
            tagArray.add(tag.getTagName());
        }
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", tagArray);
        logger.debug("Response to GET request on '/tag/allTag' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/tag/allTag' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/edit")
    public String editEntryTag(@RequestBody JSONObject request) {
        logger.info("Receive POST request on '/tag/edit'");
        logger.debug("POST request on '/tag/edit' with request body: " +
                request.toJSONString());
        String title = request.getString("title");
        JSONArray tags = request.getJSONArray("tagList");
        logger.debug("POST request on '/tag/edit' with params: " +
                "'title'=" + title + ", 'tagList'=" + tags.toString());
        List<String> tagList = tags.toJavaList(String.class);
        tagService.editEntryTag(title, tagList);
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        logger.debug("Response to POST request on '/tag/edit' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/tag/edit' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/create")
    public String createTag(@RequestBody JSONObject request) {
        logger.info("Receive POST request on '/tag/create'");
        logger.debug("POST request on '/tag/create' with request body: " +
                request.toJSONString());
        String tagName = request.getString("tagName");
        logger.debug("POST request on '/tag/create' with params: " +
                "'tagName'=" + tagName);
        JSONObject response = new JSONObject();
        if(tagService.createTag(tagName)) {
            response.put("status", 0);
            response.put("message", "创建标签成功");
        }
        else {
            response.put("status", -1);
            response.put("message", "标签已存在");
        }
        logger.debug("Response to POST request on '/tag/create' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/tag/create' finished");
        return response.toJSONString();
    }
}
