package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.EntryEditRequest;
import com.prinpedia.backend.service.EntryService;
import org.bson.types.ObjectId;
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

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/request")
    @PreAuthorize("hasRole('USER')")
    public String editEntryRequest(@RequestBody JSONObject jsonObject,
                                   Principal principal) {
        String username = principal.getName();
        String title = jsonObject.getString("title");
        String wikiText = jsonObject.getString("wikiText");
        JSONObject response = new JSONObject();
        if(title != null && wikiText != null && username != null) {
            entryService.editEntryRequest(title, wikiText, username);
            response.put("status", 0);
            response.put("message", "Successfully edited");
        }
        else {
            response.put("status", -1);
            response.put("message", "Edition failure");
        }
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/userLog")
    @PreAuthorize("principal.username.equals(#username) || hasRole('ADMIN')")
    public String getEditLog(@RequestParam("username") String username) {
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
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/detail")
    public String getEditLogDetail(@RequestParam("id") ObjectId id) {
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
            response.put("message", "Can't find edit log");
        }
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/adminLog")
    @PreAuthorize("hasRole('ADMIN')")
    public String getEditLogAdmin(@RequestParam("examined") Boolean examined) {
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
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/examine")
    @PreAuthorize("hasRole('ADMIN')")
    public String examineEditLog(@RequestBody JSONObject jsonObject) {
        Boolean passed = jsonObject.getBoolean("passed");
        String idString = jsonObject.getString("id");
        ObjectId id = new ObjectId(idString);
        JSONObject response = new JSONObject();
        if(entryService.examineEditLog(id, passed)) {
            response.put("status", 0);
            response.put("message", "Success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Failure");
        }
        return response.toJSONString();
    }
}
