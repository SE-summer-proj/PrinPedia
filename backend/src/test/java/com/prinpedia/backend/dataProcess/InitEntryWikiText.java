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

@ActiveProfiles(profiles = {"test"})
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
        entryRepository.deleteAll();
        elasticEntryRepository.deleteAll();
        entryNodeRepository.deleteAll();

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
    }
}
