package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String search(@RequestParam(value = "keyword") String keyword) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("第一条结果");
        jsonArray.add("第二条结果");
        jsonArray.add("The third item");
        return jsonArray.toJSONString();
    }
}
