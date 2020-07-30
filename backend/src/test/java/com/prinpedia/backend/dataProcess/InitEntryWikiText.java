package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryNodeRepository;
import com.prinpedia.backend.repository.EntryRepository;
import com.prinpedia.backend.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;

@ActiveProfiles(profiles = {"prod"})
@SpringBootTest
public class InitEntryWikiText {
    @Autowired
    private EntryDao entryDao;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @Test
    @Disabled
    public void entryWikiText() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\Page_001_simplified.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = tmp.split("\\t");
            if(Integer.parseInt(strings[0]) <= 5565) continue;
            Entry entry = new Entry();
            entry.setIndex(Integer.parseInt(strings[0]));
            if(strings.length > 2)
                entry.setTitle(strings[2]);
            if(strings.length > 3)
                entry.setWikiText(strings[3]);
            entryDao.create(entry);
        }
    }
}
