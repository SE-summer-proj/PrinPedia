package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.EntryStatics;
import com.prinpedia.backend.entity.SearchStatics;
import com.prinpedia.backend.entity.UserStatics;
import com.prinpedia.backend.service.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/statics", produces = "text/plain;charset=UTF-8")
public class StaticController {
    @Autowired
    private StaticService staticService;

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entry")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEntryStatics(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                  @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        List<EntryStatics> entryStaticsList =
                staticService.getEntryStatics(start, end);
        JSONArray extraData = new JSONArray();
        for(EntryStatics entryStatics : entryStaticsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", entryStatics.getTitle());
            jsonObject.put("count", entryStatics.getCount());
            extraData.add(jsonObject);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entryTitle")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEntryStaticsByTitle(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                         @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end,
                                         @RequestParam(value = "title") String title) {
        List<EntryStatics> entryStaticsList =
                staticService.getEntryStaticsByTitle(start, end, title);
        JSONArray extraData = new JSONArray();
        for(EntryStatics entryStatics : entryStaticsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", entryStatics.getTitle());
            jsonObject.put("date", entryStatics.getDate());
            jsonObject.put("count", entryStatics.getCount());
            extraData.add(jsonObject);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/search")
    @PreAuthorize("hasRole('ADMIN')")
    public String getSearchStatics(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                   @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        List<SearchStatics> searchStaticsList =
                staticService.getSearchStatics(start, end);
        JSONArray extraData = new JSONArray();
        for(SearchStatics searchStatics : searchStaticsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", searchStatics.getDate());
            jsonObject.put("count", searchStatics.getCount());
            extraData.add(jsonObject);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserStatics(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                   @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        List<UserStatics> userStaticsList =
                staticService.getUserStatics(start, end);
        JSONArray extraData = new JSONArray();
        for(UserStatics userStatics : userStaticsList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", userStatics.getDate());
            jsonObject.put("count", userStatics.getCount());
            extraData.add(jsonObject);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }
}
