package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.Entry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Disabled
@SpringBootTest
public class InitEntrySummary {
    @Autowired
    private EntryDao entryDao;

    @Test
    public void entrySummary() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\selected_pages_summary.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = tmp.split("\\t");
            Optional<Entry> entryOptional = entryDao.findByTitle(strings[1]);
            if(entryOptional.isEmpty()) continue;
            Entry entry = entryOptional.get();
            if(strings.length > 2) {
                entry.setSummary(strings[2]);
                entryDao.update(entry);
            }
        }
    }
}
