package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryDaoImplTest {
    @Autowired
    EntryDaoImpl entryDao;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    ElasticEntryRepository elasticEntryRepository;

    @BeforeEach
    public void setup() {
        Optional<Entry> optionalEntry = entryRepository.findByTitle("test");
        if(optionalEntry.isPresent()) {
            entryRepository.deleteByTitle("test");
            elasticEntryRepository.deleteByEntryTitle("test");
        }
    }

    @AfterEach
    public void after() {
        Optional<Entry> optionalEntry = entryRepository.findByTitle("test");
        if(optionalEntry.isPresent()) {
            entryRepository.deleteByTitle("test");
            elasticEntryRepository.deleteByEntryTitle("test");
        }
    }

    @Test
    public void updateEntry() {
        Entry entry = new Entry();
        entry.setTitle("test");
        Boolean updateResult = entryDao.update(entry);
        assertTrue(updateResult, "Create failure");

        Optional<Entry> entryOptional = entryDao.findByTitle("test");
        assertTrue(entryOptional.isPresent(), "Can't find entry by title");
        assertEquals("test", entryOptional.get().getTitle(),
                "Entry title don't match");

        ElasticEntry elasticEntry =
                elasticEntryRepository.findByEntryTitle("test");
        assertNotNull(elasticEntry, "Can't find entry in elastic");
        assertEquals("test", elasticEntry.getEntryTitle());

        entry.setSummary("summary");
        updateResult = entryDao.update(entry);
        assertTrue(updateResult, "Updated entry failure");

        entryOptional = entryDao.findByTitle("test");
        assertTrue(entryOptional.isPresent(), "Can't find entry by title");
        assertEquals("test", entryOptional.get().getTitle(),
                "Entry title don't match");
        assertEquals("summary", entryOptional.get().getSummary(),
                "Entry summary don't match");

        elasticEntry =
                elasticEntryRepository.findByEntryTitle("test");
        assertNotNull(elasticEntry, "Can't find entry in elastic");
        assertEquals("test", elasticEntry.getEntryTitle());
        assertEquals("summary", elasticEntry.getEntrySummary());

        updateResult = entryDao.update(entry);
        assertTrue(updateResult, "Updated entry failure");

        entryOptional = entryDao.findByTitle("test");
        assertTrue(entryOptional.isPresent(), "Can't find entry by title");
        assertEquals("test", entryOptional.get().getTitle(),
                "Entry title don't match");
        assertEquals("summary", entryOptional.get().getSummary(),
                "Entry summary don't match");

        elasticEntry =
                elasticEntryRepository.findByEntryTitle("test");
        assertNotNull(elasticEntry, "Can't find entry in elastic");
        assertEquals("test", elasticEntry.getEntryTitle());
        assertEquals("summary", elasticEntry.getEntrySummary());
    }

    @Test
    public void createEntry() {
        Entry entry = new Entry();
        entry.setTitle("test");
        Boolean createResult = entryDao.create(entry);
        assertTrue(createResult, "Create entry failure");

        Optional<Entry> entryOptional = entryDao.findByTitle("test");
        assertTrue(entryOptional.isPresent(), "Can't find entry by title");
        assertEquals("test", entryOptional.get().getTitle(),
                "Entry title don't match");

        ElasticEntry elasticEntry =
                elasticEntryRepository.findByEntryTitle("test");
        assertNotNull(elasticEntry, "Can't find entry in elastic");
        assertEquals("test", elasticEntry.getEntryTitle());
    }
}