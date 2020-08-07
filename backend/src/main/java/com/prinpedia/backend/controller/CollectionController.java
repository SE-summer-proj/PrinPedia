package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Collection;
import com.prinpedia.backend.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/collection", produces = "text/plain;charset=UTF-8")
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @CrossOrigin
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public String addToCollection(@RequestBody JSONObject jsonObject,
                                  Principal principal) {
        String username = principal.getName();
        String title = jsonObject.getString("title");
        JSONObject response = new JSONObject();
        if(collectionService.addToCollection(username, title)) {
            response.put("status", 0);
            response.put("message", "Add success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Something wrong happened");
        }
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/remove")
    @PreAuthorize("hasRole('USER')")
    public String removeFromCollection(@RequestBody JSONObject jsonObject,
                                       Principal principal) {
        String username = principal.getName();
        String title = jsonObject.getString("title");
        JSONObject response = new JSONObject();
        if(collectionService.removeFromCollection(username, title)) {
            response.put("status", 0);
            response.put("message", "Remove success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Something wrong happened");
        }
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/query")
    @PreAuthorize("hasRole('USER') || principal.username.equals(#username)")
    public String isInCollection(@RequestParam("username") String username,
                                 @RequestParam("title") String title) {
        JSONObject response = new JSONObject();
        Boolean result = collectionService.isInCollection(username, title);
        response.put("status", 0);
        response.put("message", "Query success");
        response.put("extraData", result);
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') || principal.username.equals(#username)")
    public String getAllByUser(@RequestParam("username") String username) {
        List<Collection> collectionList =
                collectionService.getAllCollectionByUser(username);
        JSONArray extraData = new JSONArray();
        for(Collection collection : collectionList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", collection.getTitle());
            jsonObject.put("addTime", collection.getCollectTime());
            extraData.add(jsonObject);
        }
        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Success");
        response.put("extraData", extraData);
        return response.toJSONString();
    }
}
