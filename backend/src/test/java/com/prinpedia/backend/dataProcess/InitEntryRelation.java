package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.repository.EntryRelationRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Disabled
@ActiveProfiles(profiles = {"prod"})
@SpringBootTest
public class InitEntryRelation {
    @Autowired
    private EntryRelationRepository entryRelationRepository;

    @Test
    public void entryRelation() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\page_inlinks.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        int oldCount = 0;
        int count = 0;
        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = (tmp.split("\\s+"));
            int parent = Integer.parseInt(strings[0]);
            int child = Integer.parseInt(strings[1]);
            if(parent > 55158 || child > 55158) {
                continue;
            }
            else if(oldCount < 0) {
                oldCount++;
                continue;
            }
            entryRelationRepository.createEntryRelation(parent, child);
            count++;
            if(count >= 10000) break;
        }
        System.out.println(count);
    }
}
