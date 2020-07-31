package com.prinpedia.backend.dataProcess;

import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Disabled
@SpringBootTest
public class InitElasticEntry {
    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @Autowired
    private EntryRepository entryRepository;

    @Test
    public void indexElasticEntry() {
        elasticEntryRepository.deleteAll();
        List<Entry> entryList = entryRepository.findAll();
        for(Entry entry : entryList) {
            ElasticEntry elasticEntry = new ElasticEntry();
            elasticEntry.setEntryTitle(entry.getTitle());
            elasticEntry.setEntrySummary(entry.getSummary());
            elasticEntryRepository.save(elasticEntry);
        }
    }
}
