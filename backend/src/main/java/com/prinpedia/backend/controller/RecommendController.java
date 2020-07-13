package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "success");
        response.put("extraData", jsonArray);
        return response.toJSONString();
    }
}
