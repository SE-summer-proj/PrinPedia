package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/recommend")
public class RecommendController {
    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String getRecommend(@RequestParam(value = "userId") Integer userID) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("哈哈哈");
        jsonArray.add("嘻嘻嘻");
        return jsonArray.toJSONString();
    }
}
