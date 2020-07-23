package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.Entry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
public class InitEntryWikiText {
    @Autowired
    private EntryDao entryDao;

    @Test
    @Disabled
    public void entryWikiText() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\selected_simplified.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = tmp.split("\\t");
            Entry entry = new Entry();
            entry.setIndex(Integer.parseInt(strings[0]));
            entry.setTitle(strings[1]);
            entry.setWikiText(strings[2]);
            entryDao.create(entry);
        }
//        String tmp;
//        while(true) {
//            while ((tmp = bufferedReader.readLine()) != null) {
//                if (tmp.equals("$$ Entry $$")) break;
//            }
//            if(tmp == null) break;
//
//            Entry entry = new Entry();
//            tmp = bufferedReader.readLine();
//            entry.setIndex(Integer.parseInt(tmp));
//
//            tmp = bufferedReader.readLine();
//            entry.setTitle(tmp);
//
//            StringBuilder summary = new StringBuilder();
//            while ((tmp = bufferedReader.readLine()) != null) {
//                if (tmp.equals("$$ Summary $$")) break;
//                summary.append(tmp);
//            }
//            entry.setSummary(summary.toString());
//
//            StringBuilder wikiText = new StringBuilder();
//            while ((tmp = bufferedReader.readLine()) != null) {
//                if (tmp.equals("$$ Entry $$")) break;
//                wikiText.append(tmp);
//            }
//            entry.setWikiText(wikiText.toString());
//            entryRepository.save(entry);
//
//            //If you want to insert data into Elasticsearch simultaneously,
//            //use the following method instead of entryRepository.save().
//
//            //entryDao.create(entry);
//        }
    }
}
