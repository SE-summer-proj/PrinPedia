package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Content;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.Section;
import com.prinpedia.backend.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/entry")
public class EntryController {
    @Autowired
    EntryService entryService;

    @CrossOrigin
    @ResponseBody
    @GetMapping
    public String getEntryDetail(@RequestParam(value = "entryName") String title) {
        Entry entry = entryService.findByTitle(title);
        JSONObject response = new JSONObject();
        if(entry != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", entry.getTitle());
            jsonObject.put("summary", entry.getSummary());
            JSONArray jsonArray = new JSONArray();
            List<Content> contentList = entry.getContent();
            List<Section> sectionList = entry.getSectionList();
            for(Content content : contentList) {
                addContentToJson(jsonArray, content, sectionList);
            }
            jsonObject.put("content", jsonArray);
            response.put("status", 0);
            response.put("message", "fetch detail success");
            response.put("extraData", jsonObject);
        }
        else {
            response.put("status", -1);
            response.put("message", "no matched entry");
        }
        return response.toJSONString();
    }

    private void addContentToJson(JSONArray jsonArray,
                                  Content content, List<Section> sectionList) {
        JSONObject jsonObject = new JSONObject();
        String label = content.getLabel();
        int split = label.indexOf(' ');
        label = label.substring(split + 1);
        jsonObject.put("label", label);
        for(Section section : sectionList) {
            if(section.getSectionTitle().equals(label)) {
                jsonObject.put("text", section.getSectionText());
                break;
            }
        }
        if(content.getChildren() != null && content.getChildren().size() > 0) {
            JSONArray jsonArray1 = new JSONArray();
            for(Content child : content.getChildren()) {
                addContentToJson(jsonArray1, child, sectionList);
            }
            jsonObject.put("children", jsonArray1);
        }
        jsonArray.add(jsonObject);
    }
}