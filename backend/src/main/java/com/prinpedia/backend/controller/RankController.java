package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rank", produces = "text/plain;charset=UTF-8")
public class RankController {

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String getRank() {
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("word", "上海交通大学");
        jsonObject.put("type", "教育");
        jsonObject.put("change", 5);
        jsonArray.add(jsonObject);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("word", "迟先生");
        jsonObject1.put("type", "卖弱");
        jsonObject1.put("change", -1);
        jsonArray.add(jsonObject1);
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "success");
        response.put("extraData", jsonArray);
        return response.toJSONString();
    }
}
