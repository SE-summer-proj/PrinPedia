package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hankcs.hanlp.HanLP;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    EntryService entryService;

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String search(@RequestParam(value = "keyword") String keyword) {
        String title =entryService.searchTitle(keyword);
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        if(title != null) {
            jsonArray.add(title);
            response.put("status", 0);
            response.put("message", "success");
            response.put("extraData", jsonArray);
            return response.toJSONString();
        }
        List<String> stringList = entryService.searchTitleAndSummary(keyword);
        if(stringList != null) {
            jsonArray.addAll(stringList);
            response.put("status", 1);
            response.put("message", "no exactly matched entry, find suggestions");
            response.put("extraData", jsonArray);
        }
        else {
            response.put("status", -1);
            response.put("message", "cannot find matched entry");
        }
        return response.toJSONString();

    }
}
