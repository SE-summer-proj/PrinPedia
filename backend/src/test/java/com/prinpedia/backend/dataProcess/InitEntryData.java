package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.Content;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.Section;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class InitEntryData {
    @Autowired
    private EntryRepository entryRepository;

    @Test
    public void InsertThroughFile() {
        //change "path" to your file path
        String path = "G:\\webWorkspace\\prinpedia\\data0.txt";

        File file = new File(path);
        String tmp;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while(true) {
                List<Content> contents= new ArrayList<>();
                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Entry $$")) break;
                }
                if(tmp == null) break;

                Entry entry = new Entry();
                tmp = bufferedReader.readLine();
                entry.setTitle(tmp);

                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Content $$")) break;
                }

                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Content $$")) break;
                    int split = tmp.indexOf(' ');
                    String pre = tmp.substring(0, split);
                    char[] chars = pre.toCharArray();
                    Content curContent = null;
                    List<Content> curContents = contents;

                    for (int i = 0; i < chars.length; i++) {
                        char ch = chars[i];
                        if (ch == '.') {
                            i++;
                            ch = chars[i];
                        }
                        int index = 0;
                        while (ch >= '0' && ch <= '9') {
                            index = index * 10 + (ch - '0');
                            i++;
                            if (i >= chars.length) break;
                            ch = chars[i];
                        }
                        if (curContents.size() < index) {
                            Content content1 = new Content();
                            content1.setChildren(new ArrayList<>());
                            curContents.add(content1);
                        }
                        curContent = curContents.get(index - 1);
                        curContents = curContent.getChildren();
                    }

                    if (curContent != null) {
                        curContent.setLabel(tmp);
                    }
                }
                entry.setContent(contents);

                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Summary $$")) break;
                }
                StringBuilder summary = new StringBuilder();
                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Summary $$")) break;
                    summary.append(tmp).append('\n');
                }
                entry.setSummary(summary.toString());

                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Text $$")) break;
                }
                List<Section> sections = new ArrayList<>();
                while (true) {
                    while ((tmp = bufferedReader.readLine()) != null) {
                        if (tmp.equals("$$ Text $$")) break;
                        if (tmp.equals("$$ Section $$")) break;
                    }
                    if (tmp != null && tmp.equals("$$ Text $$")) break;
                    Section section = new Section();
                    tmp = bufferedReader.readLine();
                    section.setSectionTitle(tmp);
                    StringBuilder sectionText = new StringBuilder();
                    while ((tmp = bufferedReader.readLine()) != null) {
                        if (tmp.equals("$$ Section $$")) break;
                        sectionText.append(tmp);
                    }
                    section.setSectionText(sectionText.toString());
                    sections.add(section);
                }
                entry.setSectionList(sections);

                while ((tmp = bufferedReader.readLine()) != null) {
                    if (tmp.equals("$$ Entry $$")) break;
                }
                entryRepository.save(entry);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
