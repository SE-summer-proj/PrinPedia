package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Disabled
@ActiveProfiles(profiles = {"prod"})
@SpringBootTest
public class InitElasticEntry {
    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @Test
    public void indexElasticEntry() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\Page_001_summary.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        int count = 0;
        int oldCount = 0;
        List<ElasticEntry> elasticEntryList = new ArrayList<>();
        while((tmp = bufferedReader.readLine()) != null) {
            if(oldCount < 14000) {
                oldCount++;
                continue;
            }
            String []strings = tmp.split("\\t");
            ElasticEntry elasticEntry = new ElasticEntry();
            if(strings.length > 1)
                elasticEntry.setEntryTitle(strings[1]);
            if(strings.length > 2)
                elasticEntry.setEntrySummary(strings[2]);
            elasticEntryList.add(elasticEntry);
            count++;
            if(count >= 3000) break;
        }
        System.out.println("Construction finished");
        elasticEntryRepository.saveAll(elasticEntryList);
        System.out.println("Insertion finished");
    }
}
