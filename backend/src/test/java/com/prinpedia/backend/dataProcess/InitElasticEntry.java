package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Disabled
@ActiveProfiles(profiles = {"prod"})
@SpringBootTest
public class InitElasticEntry {
    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    private Logger logger = LoggerFactory.getLogger(InitElasticEntry.class);

    @Test
    public void indexElasticEntry() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\page_summary\\Page_008_summary.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        logger.info("Opening file succeeded, starting construction");
        String tmp;
        int count = 0;
        int oldCount = 0;
        List<ElasticEntry> elasticEntryList = new ArrayList<>();
        while((tmp = bufferedReader.readLine()) != null) {
            if(oldCount < 0) {
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
            if(count >= 2000) {
                logger.info("Construction finished, count: " + count);
                elasticEntryRepository.saveAll(elasticEntryList);
                logger.info("Insertion finished, count: " + count);
                count = 0;
                elasticEntryList.clear();
            }
        }
        if(elasticEntryList.size() == 0) return;
        logger.info("Construction finished, count: " + count);
        elasticEntryRepository.saveAll(elasticEntryList);
        logger.info("Insertion finished, count: " + count);
    }
}
