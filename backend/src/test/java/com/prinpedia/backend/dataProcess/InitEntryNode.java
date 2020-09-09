package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.repository.EntryNodeRepository;
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
public class InitEntryNode {
    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @Test
    public void entryNode() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\Page_001_summary.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        int count = 0;
        List<EntryNode> entryNodeList = new ArrayList<>();
        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = tmp.split("\\t");
            EntryNode entryNode = new EntryNode();
            if(Integer.parseInt(strings[0]) <= 33772) continue;
            entryNode.setIndex(Integer.parseInt(strings[0]));
            if(strings.length > 1)
                entryNode.setTitle(strings[1]);
            entryNodeList.add(entryNode);
            count++;
            if(count >= 7000) break;
        }
        System.out.println("Construction finished");
        entryNodeRepository.saveAll(entryNodeList);
        System.out.println("Insertion finished");
    }
}
