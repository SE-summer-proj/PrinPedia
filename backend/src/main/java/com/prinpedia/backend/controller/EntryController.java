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
            for(Content content : contentList) {
                addContentToJson(jsonArray, content);
            }
            jsonObject.put("content", jsonArray);
            JSONArray jsonArray1 = new JSONArray();
            List<Section> sectionList = entry.getSectionList();
            for(Section section : sectionList) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("sectionTitle", section.getSectionTitle());
                jsonObject1.put("sectionText", section.getSectionText());
                jsonArray1.add(jsonObject1);
            }
            jsonObject.put("text", jsonArray1);
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

    private void addContentToJson(JSONArray jsonArray, Content content) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("label", content.getLabel());
        if(content.getChildren() != null && content.getChildren().size() > 0) {
            JSONArray jsonArray1 = new JSONArray();
            for(Content child : content.getChildren()) {
                addContentToJson(jsonArray1, child);
            }
            jsonObject.put("children", jsonArray1);
        }
        jsonArray.add(jsonObject);
    }
}
