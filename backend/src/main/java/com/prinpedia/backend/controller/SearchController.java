package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
import com.prinpedia.backend.service.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/search", produces = "text/plain;charset=UTF-8")
public class SearchController {
    @Autowired
    EntryService entryService;

    @Autowired
    StaticService staticService;

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String search(@RequestParam(value = "keyword") String keyword) {
        System.out.println("Start " + new Date());
        staticService.searchRecord();
        String title = entryService.searchTitle(keyword);
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        if(title != null) return null;
        System.out.println("Match finished " + new Date());
        List<ElasticEntry> suggestionList = entryService.searchTitleAndSummary(keyword);
        System.out.println("Search finished " + new Date());
        if(suggestionList != null) {
            for(ElasticEntry elasticEntry: suggestionList) {
                JSONObject suggestion = new JSONObject();
                suggestion.put("title", elasticEntry.getEntryTitle());
                suggestion.put("summary",
                        elasticEntry.getEntrySummary());
                jsonArray.add(suggestion);
            }
            response.put("status", 1);
            response.put("message", "no exactly matched entry, find suggestions");
            response.put("extraData", jsonArray);
        }
        else {
            response.put("status", -1);
            response.put("message", "cannot find matched entry");
        }
        System.out.println("Finished " + new Date());
        return response.toJSONString();
    }
}
