package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.entity.EntryRelation;
import com.prinpedia.backend.repository.EntryNodeRepository;
import com.prinpedia.backend.repository.EntryRelationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Disabled
@ActiveProfiles(profiles = {"prod"})
@SpringBootTest
public class InitEntryRelation {
    @Autowired
    private EntryRelationRepository entryRelationRepository;

    @Autowired
    private EntryNodeRepository entryNodeRepository;

    private Logger logger = LoggerFactory.getLogger(InitEntryRelation.class);

    @Test
    public void entryRelation() throws IOException {
        entryRelationRepository.deleteAll();
        logger.info("Delete all relations finished");
        String path = "G:\\webWorkspace\\prinpedia\\test_inlink_final.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        int oldCount = 0;
        int count = 0;
        List<EntryRelation> entryRelationList = new ArrayList<>();
        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = (tmp.split("\\s+"));
            int parent = Integer.parseInt(strings[0]);
            int child = Integer.parseInt(strings[1]);
            long weight = Long.parseLong(strings[2]);
            if(parent > 55158 || child > 55158) {
                continue;
            }
            else if(oldCount < 0) {
                oldCount++;
                continue;
            }
            EntryNode start = entryNodeRepository.findByIndex(parent);
            EntryNode end = entryNodeRepository.findByIndex(child);
            if(start == null || end == null) continue;
            EntryRelation entryRelation = new EntryRelation();
            entryRelation.setStart(start);
            entryRelation.setEnd(end);
            entryRelation.setWeight(weight);
            entryRelationList.add(entryRelation);
            count++;
            if(count >= 5000) break;
        }
        logger.info("Construction finished, count: " + count);
        entryRelationRepository.saveAll(entryRelationList);
        logger.info("Insertion finished, count: " + count);
    }
}
