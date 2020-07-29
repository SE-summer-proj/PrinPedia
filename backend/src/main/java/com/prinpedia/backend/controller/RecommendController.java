package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/recommend")
public class RecommendController {
    @CrossOrigin
    @ResponseBody
    @GetMapping
    @PreAuthorize("principal.username.equals(#username)")
    public String getRecommend(@RequestParam(value = "username") String username) {
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
