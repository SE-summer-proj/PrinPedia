package com.prinpedia.backend.dataProcess;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Ignore
@SpringBootTest
public class WikiMarkupProcess {
    @Autowired
    EntryRepository entryRepository;

    @Ignore
    @Test
    public void process() {
        Optional<Entry> entry = entryRepository.findByTitle("数学");
        String test = "";
        if(entry.isPresent()) {
            test = entry.get().getWikiText();
        }

        List<String> h1 = new ArrayList<>();
        String regx = "=*==(.+?)===*";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()) {
            h1.add(matcher.group(1));
        }

        List<String> h2 = new ArrayList<>();
        regx = "=*===(.+?)====*";
        pattern = Pattern.compile(regx);
        matcher = pattern.matcher(test);
        while (matcher.find()) {
            h2.add(matcher.group(1));
        }

        List<String> h3 = new ArrayList<>();
        regx = "=*====(.+?)=====*";
        pattern = Pattern.compile(regx);
        matcher = pattern.matcher(test);
        while (matcher.find()) {
            h3.add(matcher.group(1));
        }

        List<List<String>> list = new ArrayList<>();
        list.add(h1);list.add(h2);list.add(h3);

        JSONArray result = new JSONArray();
        int index = 0;
        while(index < h1.size()) {
            JSONObject jsonObject = new JSONObject();
            index = recursion(jsonObject, index, 1, list);
            result.add(jsonObject);
        }

        System.out.println(result.toJSONString());
    }


    private int recursion(JSONObject result, int index, int level,
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
            result.put("title", cur);
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
                result.put("children", children);
                return index;
            }
            JSONObject jsonObject = new JSONObject();
            index = recursion(jsonObject, index, level + 1, list);
            children.add(jsonObject);
        }
        result.put("children", children);
        return index;
    }

    @Test
    public void match() {
        Optional<Entry> entry = entryRepository.findByTitle("数学");
        String test = "";
        if(entry.isPresent()) {
            test = entry.get().getWikiText();
        }
        String regx = "=*==(.+?)===*";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }

        regx = "=*===(.+?)====*";
        pattern = Pattern.compile(regx);
        matcher = pattern.matcher(test);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }

        regx = "=*====(.+?)=====*";
        pattern = Pattern.compile(regx);
        matcher = pattern.matcher(test);
        while (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
