package com.prinpedia.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
import com.prinpedia.backend.service.StaticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping(produces = "text/plain;charset=UTF-8")
public class EntryController {
    @Autowired
    private EntryService entryService;

    @Autowired
    private StaticService staticService;

    private Logger logger = LoggerFactory.getLogger(EntryController.class);

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entry")
    public String getEntryDetail(@RequestParam(value = "entryName") String title) {
        logger.info("Receive GET request on '/entry'");
        logger.debug("GET request on '/entry' with params: " +
                "'entryName'=" + title);
        Entry entry = entryService.findByTitle(title);
        JSONObject response = new JSONObject();
        if(entry != null) {
            staticService.entryRecord(title);
            JSONObject extraData = new JSONObject();
            extraData.put("title", entry.getTitle());
            String wikiText = entry.getWikiText();
            JSONArray content = parseWikiMarkupIntoContent(wikiText);
            String modifiedWiki = wikiText.replace("\\n", "\n");
            modifiedWiki = modifiedWiki.replace("\\'", "\'");
            extraData.put("content", content);
            extraData.put("wikiText", modifiedWiki);
            if(entry.getLocked() != null) {
                extraData.put("locked", entry.getLocked());
            }
            response.put("status", 0);
            response.put("message", "fetch detail success");
            response.put("extraData", extraData);
        }
        else {
            response.put("status", -1);
            response.put("message", "no matched entry");
        }
        logger.debug("Response to GET request on '/entry' is: " +
                response.toJSONString());
        logger.info("Response to GET request on '/entry' finished");
        return response.toJSONString();
    }

    private JSONArray parseWikiMarkupIntoContent(String markup) {
        logger.info("Start parsing wiki markup");
        if(markup == null) return null;
        List<String> h1 = new ArrayList<>();
        String regx = "=*==(.+?)===*";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(markup);
        while (matcher.find()) {
            h1.add(matcher.group(1));
        }

        List<String> h2 = new ArrayList<>();
        regx = "=*===(.+?)====*";
        pattern = Pattern.compile(regx);
        matcher = pattern.matcher(markup);
        while (matcher.find()) {
            h2.add(matcher.group(1));
        }

        List<String> h3 = new ArrayList<>();
        regx = "=*====(.+?)=====*";
        pattern = Pattern.compile(regx);
        matcher = pattern.matcher(markup);
        while (matcher.find()) {
            h3.add(matcher.group(1));
        }

        List<List<String>> list = new ArrayList<>();
        list.add(h1);list.add(h2);list.add(h3);

        logger.debug("Parsing wiki markup result: " + list.toString());
        logger.info("Parsing wiki markup finished");
        JSONArray result = new JSONArray();
        int index = 0;
        while(index < h1.size()) {
            JSONObject jsonObject = new JSONObject();
            index = assembleContent(jsonObject, index, 1, list);
            result.add(jsonObject);
        }

        return result;
    }

    private int assembleContent(JSONObject result, int index, int level,
                                List<List<String>> list) {
        List<String> h1 = list.get(0);
        String cur = h1.get(index);
        int newLevel;
        for(newLevel = 1; newLevel < list.size(); newLevel++) {
            List<String> stringList = list.get(newLevel);
            if(!stringList.contains(cur)) break;
        }
        if(newLevel < level) return index;
        if(newLevel == level) {
            result.put("label", cur);
        }

        index++;

        JSONArray children = new JSONArray();
        while(index < h1.size()) {
            cur = h1.get(index);
            for(newLevel = 1; newLevel < list.size(); newLevel++) {
                List<String> stringList = list.get(newLevel);
                if(!stringList.contains(cur)) break;
            }
            if(newLevel <= level) {
                if(children.size() > 0) {
                    result.put("children", children);
                }
                return index;
            }
            JSONObject jsonObject = new JSONObject();
            index = assembleContent(jsonObject, index, level + 1, list);
            children.add(jsonObject);
        }

        if(children.size() > 0) { result.put("children", children); }
        return index;
    }
    /*
    //depreciated method
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/entryOld")
    public String getEntryDetailOld(@RequestParam(value = "entryName") String title) {
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
    */
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/create")
    public String createEntry(@RequestBody JSONObject jsonObject) {
        logger.info("Receive POST request on '/create'");
        logger.debug("POST request on '/create' with request body: " +
                jsonObject.toJSONString());
        String title = jsonObject.getString("keyword");
        logger.debug("POST request on '/create' with params: " +
                "'keyword'=" + title);
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
        logger.debug("Response to POST request on '/create' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/create' finished");
        return response.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/edit")
    public String editEntry(@RequestBody JSONObject jsonObject) {
        logger.warn("'/edit' API is depreciated, this API will be removed in " +
                "later version");
        JSONObject response = new JSONObject();
        String title = jsonObject.getString("title");
        String wikiText = jsonObject.getString("wikiText");
        if(wikiText != null) {
            entryService.editEntry(title, wikiText);
            response.put("status", 0);
            response.put("message", "Successfully edited");
        }
        else {
            response.put("status", -1);
            response.put("message", "Edition failure");
        }

        return response.toJSONString();
    }
    /*
    //depreciated method
    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/editOld")
    public String editEntryOld(@RequestBody JSONObject jsonObject) {
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
     */

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/relation")
    public String getRelationByTitle(@RequestParam("title") String title) {
        logger.info("Receive GET request on '/relation'");
        logger.debug("GET request on '/relation' with params: " +
                "'title'=" + title);

        List<String> parents = entryService.findParents(title);
        List<String> children = entryService.findChildren(title);

        JSONObject jsonObject = new JSONObject();

        JSONArray jsonParent = new JSONArray();
        jsonParent.addAll(parents);
        jsonObject.put("parents", jsonParent);

        JSONArray jsonChild = new JSONArray();
        jsonChild.addAll(children);
        jsonObject.put("children", jsonChild);

        JSONArray jsonCurrent = new JSONArray();
        jsonCurrent.add(title);
        jsonObject.put("current", jsonCurrent);

        logger.debug("Response to GET request on '/relation' is: " +
                jsonObject.toJSONString());
        logger.info("Response to GET request on '/relation' finished");
        return jsonObject.toJSONString();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/lock")
    @PreAuthorize("hasRole('ADMIN')")
    public String lockEntry(@RequestBody JSONObject request) {
        logger.info("Receive POST request on '/lock'");
        logger.debug("POST request on '/lock' with request body: " +
                request.toJSONString());
        String title = request.getString("title");
        Boolean lock = request.getBoolean("lock");
        logger.debug("POST request on '/entry/edit/request' with params: " +
                "'title'=" + title + ", 'lock'=" + lock.toString());
        JSONObject response = new JSONObject();
        if(entryService.lockEntry(title, lock)) {
            response.put("status", 0);
            response.put("message", "Success");
        }
        else {
            response.put("status", -1);
            response.put("message", "Something wrong happened");
        }
        logger.debug("Response to POST request on '/lock' is: " +
                response.toJSONString());
        logger.info("Response to POST request on '/lock' finished");
        return response.toJSONString();
    }
}
