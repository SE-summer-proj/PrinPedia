package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.EntryEditRequest;
import com.prinpedia.backend.service.EntryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/entry/edit", produces = "text/plain;charset=UTF-8")
public class EditEntryController {
    @Autowired
    EntryService entryService;

    private Logger logger = LoggerFactory.getLogger(EditEntryController.class);

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/request")
    @PreAuthorize("hasRole('USER')")
    public String editEntryRequest(@RequestBody JSONObject jsonObject,
                                   Principal principal) {
        logger.info("Receive POST request on '/entry/edit/request'");
        logger.debug("POST request on '/entry/edit/request' with request body: " +
                jsonObject.toJSONString());
        String username = principal.getName();
        String title = jsonObject.getString("title");
        String wikiText = jsonObject.getString("wikiText");
        logger.debug("POST request on '/entry/edit/request' with params: " +
                "'username'=" + username + ", 'title'=" + title +
                "'wikiText'=" + wikiText);
        JSONObject response = new JSONObject();
        if(title != null && wikiText != null && username != null) {
            Entry entry = entryService.findByTitle(title);
            if(entry == null || (entry.getLocked() != null && entry.getLocked())) {
                response.put("status", -1);
                response.put("message", "不能编辑此词条");
            }
            else {
                entryService.editEntryRequest(title, wikiText, username);
                response.put("status", 0);
                response.put("message", "提交编辑申请成功");
            }
        }
        else {
            response.put("status", -1);
            response.put("message", "编辑失败");
        }
        logger.debug("Response to POST request on '/entry/edit/request' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/entry/edit/request' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/userLog")
    @PreAuthorize("principal.username.equals(#username) && hasRole('ADMIN')")
    public String getEditLog(@RequestParam("username") String username) {
        logger.info("Receive GET request on '/entry/edit/userLog'");
        logger.debug("GET request on '/entry/edit/userLog' with params: " +
                "'username'=" + username);
        List<EntryEditRequest> entryEditRequestList =
                entryService.getEditLogByUser(username);
        JSONObject response = new JSONObject();
        JSONArray extraData = new JSONArray();
        for(EntryEditRequest request : entryEditRequestList) {
            JSONObject item = new JSONObject();
            item.put("id", request.getId().toString());
            item.put("date", request.getDate());
            item.put("status", request.getStatus());
            item.put("title", request.getTitle());
            extraData.add(item);
        }
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        logger.debug("Response to GET request on '/entry/edit/userLog' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/entry/edit/userLog' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/detail")
    public String getEditLogDetail(@RequestParam("id") ObjectId id) {
        logger.info("Receive GET request on '/entry/edit/detail'");
        logger.debug("GET request on '/entry/edit/detail' with params: " +
                "'id'=" + id.toString());
        EntryEditRequest entryEditRequest = entryService.getEditLogById(id);
        JSONObject response = new JSONObject();
        if(entryEditRequest != null) {
            JSONObject extraData = new JSONObject();
            extraData.put("title", entryEditRequest.getTitle());
            extraData.put("wikiText", entryEditRequest.getWikiText());
            extraData.put("date", entryEditRequest.getDate());
            extraData.put("status", entryEditRequest.getStatus());
            extraData.put("id", entryEditRequest.getId().toString());
            response.put("status", 0);
            response.put("message", "Success");
            response.put("extraData", extraData);
        }
        else {
            response.put("status", -1);
            response.put("message", "不能找到编辑记录");
        }
        logger.debug("Response to GET request on '/entry/edit/detail' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/entry/edit/detail' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/adminLog")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditLogAdmin(@RequestParam("examined") Boolean examined) {
        logger.info("Receive GET request on '/entry/edit/adminLog'");
        logger.debug("GET request on '/entry/edit/adminLog' with params: " +
                "'examined'=" + examined.toString());
        List<EntryEditRequest> entryEditRequestList =
                entryService.getEditLogAdmin(examined);
        JSONObject response = new JSONObject();
        JSONArray extraData = new JSONArray();
        for(EntryEditRequest request : entryEditRequestList) {
            JSONObject item = new JSONObject();
            item.put("id", request.getId().toString());
            item.put("date", request.getDate());
            item.put("status", request.getStatus());
            item.put("title", request.getTitle());
            extraData.add(item);
        }
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        logger.debug("Response to GET request on '/entry/edit/adminLog' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/entry/edit/adminLog' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/examine")
    @PreAuthorize("hasRole('ADMIN')")
    public String examineEditLog(@RequestBody JSONObject jsonObject) {
        logger.info("Receive POST request on '/entry/edit/examine'");
        logger.debug("POST request on '/entry/edit/examine' with request body: " +
                jsonObject.toJSONString());
        Boolean passed = jsonObject.getBoolean("passed");
        String idString = jsonObject.getString("id");
        logger.debug("POST request on '/entry/edit/examine' with params: " +
                "'id'=" + idString + ", 'passed'=" + passed.toString());
        ObjectId id = new ObjectId(idString);
        JSONObject response = new JSONObject();
        if(entryService.examineEditLog(id, passed)) {
            response.put("status", 0);
            response.put("message", "成功");
        }
        else {
            response.put("status", -1);
            response.put("message", "出现了一点儿问题");
        }
        logger.debug("Response to POST request on '/entry/edit/examine' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/entry/edit/examine' finished");
        return response.toJSONString();
    }
}
