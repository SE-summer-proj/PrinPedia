package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Content;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.Section;
import com.prinpedia.backend.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
public class EntryController {
    @Autowired
    EntryService entryService;

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entry")
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

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/create")
    public String createEntry(@RequestBody JSONObject jsonObject) {
        String title = jsonObject.getString("keyword");
        JSONObject response = new JSONObject();
        if(entryService.createEntry(title)) {
            response.put("status", 0);
            response.put("message", "Successfully created");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("title", title);
            jsonObject1.put("summary", "");
            JSONArray jsonArray = new JSONArray();
            jsonObject1.put("content", jsonArray);
            response.put("extraData", jsonObject1);
        }
        else {
            response.put("status", -1);
            response.put("message", "Create failure");
        }
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/edit")
    public String editEntry(@RequestBody JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        String summary = jsonObject.getString("summary");
        JSONArray content = jsonObject.getJSONArray("content");
        List<Content> contents = new ArrayList<>();
        List<Section> sectionList = new ArrayList<>();
        if(content != null) {
            for (int i = 0; i < content.size(); i++) {
                JSONObject child = content.getJSONObject(i);
                Content childContent = new Content();
                addJsonToContent(child, childContent, sectionList);
                contents.add(childContent);
            }
        }

        entryService.editEntry(title, summary, contents, sectionList);

        JSONObject response = new JSONObject();
        response.put("status", 0);
        response.put("message", "Successfully edited");
        return response.toJSONString();
    }

    private void addJsonToContent(JSONObject jsonObject, Content content,
                                  List<Section> sectionList) {
        String label = jsonObject.getString("label");

        Section section = new Section();
        section.setSectionTitle(label);
        section.setSectionText(jsonObject.getString("text"));
        sectionList.add(section);

        label = "$ " + label;
        content.setLabel(label);

        JSONArray jsonArray = jsonObject.getJSONArray("children");
        if(jsonArray == null || jsonArray.size() <= 0) return;
        List<Content> contents = new ArrayList<>();
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject child = jsonArray.getJSONObject(i);
            Content childContent = new Content();
            addJsonToContent(child, childContent, sectionList);
            contents.add(childContent);
        }
        content.setChildren(contents);
    }
}
