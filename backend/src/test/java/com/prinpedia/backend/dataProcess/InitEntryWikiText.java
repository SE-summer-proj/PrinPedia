package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@SpringBootTest
@Ignore
public class InitEntryWikiText {
    @Autowired
    private EntryRepository entryRepository;

    @Test
    public void entryWikiText() throws IOException {
        String path = "G:\\webWorkspace\\prinpedia\\entryTextWiki.txt";

        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        while(true) {
            while ((tmp = bufferedReader.readLine()) != null) {
                if (tmp.equals("$$ Entry $$")) break;
            }
            if(tmp == null) break;

            Entry entry = new Entry();
            tmp = bufferedReader.readLine();
            entry.setTitle(tmp);

            StringBuilder stringBuilder = new StringBuilder();
            while ((tmp = bufferedReader.readLine()) != null) {
                if (tmp.equals("$$ Entry $$")) break;
                stringBuilder.append(tmp);
            }
            entry.setWikiText(stringBuilder.toString());
            entryRepository.save(entry);
        }
    }
}
