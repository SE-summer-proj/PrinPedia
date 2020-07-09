package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @Autowired
    EntryService entryService;

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String search(@RequestParam(value = "keyword") String keyword) {
        Entry entry =entryService.findByTitle(keyword);
        JSONArray jsonArray = new JSONArray();
        JSONObject response = new JSONObject();
        if(entry != null) {
            jsonArray.add(entry.getTitle());
            response.put("status", 0);
            response.put("message", "success");
        }
        else {
            jsonArray.add("第一条结果");
            jsonArray.add("第二条结果");
            jsonArray.add("The third item");
            response.put("status", -1);
            response.put("message", "cannot find matched entry");
        }
        response.put("extraData", jsonArray);
        return response.toJSONString();
    }
}
