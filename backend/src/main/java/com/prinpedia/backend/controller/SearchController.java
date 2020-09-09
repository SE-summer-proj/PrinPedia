package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
import com.prinpedia.backend.service.StaticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/search", produces = "text/plain;charset=UTF-8")
public class SearchController {
    @Autowired
    private EntryService entryService;

    @Autowired
    private StaticService staticService;

    private Logger logger = LoggerFactory.getLogger(SearchController.class);

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String search(@RequestParam(value = "keyword") String keyword,
                         @RequestParam(value = "page") @Nullable Integer page) {
        logger.info("Receive GET request on '/search'");
        logger.debug("GET request on '/search' with params: " +
                "'keyword'=" + keyword + ", 'page'=" + page);
        staticService.searchRecord();
        //String title = entryService.searchTitle(keyword);
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
//        if(title != null) {
//            response.put("status", 0);
//            response.put("message", "Find an exactly matched entry");
//            response.put("extraData", title);
        //}

        //else {
        if(page == null) page = 0;
        List<ElasticEntry> suggestionList =
                entryService.searchTitleAndSummary(keyword, page);
        if (suggestionList != null) {
            for (ElasticEntry elasticEntry : suggestionList) {
                JSONObject suggestion = new JSONObject();
                suggestion.put("title", elasticEntry.getEntryTitle());
                suggestion.put("summary",
                        elasticEntry.getEntrySummary());
                jsonArray.add(suggestion);
            }
            response.put("status", 1);
            response.put("message", "no exactly matched entry, find suggestions");
            response.put("extraData", jsonArray);
        } else {
            response.put("status", -1);
            response.put("message", "cannot find matched entry");
        }
        //}
        logger.debug("Response to GET request on '/search' is: "
                + response.toJSONString());
        logger.info("Response to GET request on '/search' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/advanced")
    public String advancedSearch(@RequestBody JSONObject request) {
        logger.info("Receive POST request on '/search/advanced'");
        logger.debug("POST request on '/search/advanced' with request body: " +
                request.toJSONString());
        String must = request.getString("must");
        String should = request.getString("should");
        String mustNot = request.getString("mustNot");
        String mustTotal = request.getString("mustTotal");
        String mustTitle = request.getString("mustTitle");
        Integer page = request.getInteger("page");
        if(page == null) page = 0;
        List<ElasticEntry> suggestionList =
                entryService.advancedSearch(must, should, mustNot, mustTotal,
                        mustTitle, page);
        JSONObject response = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (suggestionList != null) {
            for (ElasticEntry elasticEntry : suggestionList) {
                JSONObject suggestion = new JSONObject();
                suggestion.put("title", elasticEntry.getEntryTitle());
                suggestion.put("summary",
                        elasticEntry.getEntrySummary());
                jsonArray.add(suggestion);
            }
            response.put("status", 0);
            response.put("message", "Advanced search success");
            response.put("extraData", jsonArray);
        } else {
            response.put("status", -1);
            response.put("message", "Cannot find matched entry");
        }

        logger.debug("Response to POST request on '/search/advanced' is: "
                + response.toJSONString());
        logger.info("Response to GET request on '/search/advanced' finished");
        return response.toJSONString();
    }
}
