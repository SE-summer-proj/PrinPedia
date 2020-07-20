package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.repository.EntryNodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class InitEntryNode {
    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @Test
    public void entryNode() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\entryIndexTest.txt";

        File file = new File(path);
        String tmp;
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while((tmp = bufferedReader.readLine()) != null) {
            String []strings = (tmp.split("\\s+"));
            int index = Integer.parseInt(strings[0]);
            String title = strings[1];

            EntryNode entryNode = new EntryNode();
            entryNode.setIndex(index);
            entryNode.setTitle(title);
            entryNodeRepository.save(entryNode);
        }
    }
}
