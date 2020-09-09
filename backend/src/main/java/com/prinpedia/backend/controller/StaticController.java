package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.EntryStatics;
import com.prinpedia.backend.entity.SearchStatics;
import com.prinpedia.backend.entity.UserStatics;
import com.prinpedia.backend.service.StaticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(StaticController.class);

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entry")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEntryStatics(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                  @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        logger.info("Receive GET request on '/statics/entry'");
        logger.debug("GET request on '/statics/entry' with params: " +
                "'start'=" + start.toString() + ", 'end'=" + end.toString());
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
        logger.debug("Response to GET request on '/statics/entry' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/statics/entry' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entryTitle")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEntryStaticsByTitle(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                         @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end,
                                         @RequestParam(value = "title") String title) {
        logger.info("Receive GET request on '/statics/entryTitle'");
        logger.debug("GET request on '/statics/entryTitle' with params: " +
                "'start'=" + start.toString() + ", 'end'=" + end.toString() +
                ", 'title'=" + title);
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
        logger.debug("Response to GET request on '/statics/entryTitle' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/statics/entryTitle' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/search")
    @PreAuthorize("hasRole('ADMIN')")
    public String getSearchStatics(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                   @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        logger.info("Receive GET request on '/statics/search'");
        logger.debug("GET request on '/statics/search' with params: " +
                "'start'=" + start.toString() + ", 'end'=" + end.toString());
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
        logger.debug("Response to GET request on '/statics/search' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/statics/search' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserStatics(@RequestParam(value = "start") @DateTimeFormat(pattern="yyyy-MM-dd") Date start,
                                   @RequestParam(value = "end") @DateTimeFormat(pattern="yyyy-MM-dd") Date end) {
        logger.info("Receive GET request on '/statics/user'");
        logger.debug("GET request on '/statics/user' with params: " +
                "'start'=" + start.toString() + ", 'end'=" + end.toString());
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
        logger.debug("Response to GET request on '/statics/user' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/statics/user' finished");
        return response.toJSONString();
    }
}
