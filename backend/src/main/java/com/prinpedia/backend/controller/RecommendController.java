package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/recommend", produces = "text/plain;charset=UTF-8")
public class RecommendController {
    @CrossOrigin
    @ResponseBody
    @GetMapping
    @PreAuthorize("principal.username.equals(#username)")
    public String getRecommend(@RequestParam(value = "username") String username) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("数学");
        jsonArray.add("河北省");
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "success");
        response.put("extraData", jsonArray);
        return response.toJSONString();
    }
}
