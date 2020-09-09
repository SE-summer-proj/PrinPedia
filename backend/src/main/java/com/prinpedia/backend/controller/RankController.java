package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.EntryStatics;
import com.prinpedia.backend.repository.EntryRepository;
import com.prinpedia.backend.repository.EntryStaticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rank", produces = "text/plain;charset=UTF-8")
public class RankController {
    @Autowired
    EntryStaticsRepository entryStaticsRepository;

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String getRank() {
        List<EntryStatics> entryStaticsList =
                entryStaticsRepository.findTop4ByOrderByCountDesc();
        JSONArray jsonArray = new JSONArray();
        for(EntryStatics entryStatics: entryStaticsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("word", entryStatics.getTitle());
            jsonArray.add(jsonObject);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "success");
        response.put("extraData", jsonArray);
        return response.toJSONString();
    }
}
