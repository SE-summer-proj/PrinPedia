package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryServiceTest {
    @Autowired
    private EntryService entryService;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @DisplayName("Find an entry by title")
    @Test
    public void findByTitle() {
        String title = "Science";

        Entry entry = entryService.findByTitle(title);
        assertNotNull(entry, "Cannot find entry by title");
        assertEquals(title, entry.getTitle(), "Titles don't match");

        title = "123";

        entry = entryService.findByTitle(title);
        assertNull(entry, "Find an entry which shouldn't exist");
    }

    @DisplayName("Search title")
    @Test
    public void searchTitle() {
        String keyword = "science";

        String result = entryService.searchTitle(keyword);
        assertNotNull(result, "Cannot find entry by title");
        System.out.println("Search with keyword(" + keyword + "): " + result);

        result = entryService.searchTitle("hahaha");
        assertNull(result, "Find an entry which shouldn't exist");
    }

    @DisplayName("Suggestion search")
    @Test
    public void  searchTitleAndSummary() {
        String []strings = {
                "greek",
                "technique",
                "mission"
        };

        for(String keyword : strings) {
            List<String> result = entryService.searchTitleAndSummary(keyword);
            System.out.println("Search with keyword (" + keyword + "): " + result);
        }
    }

    @DisplayName("Create new entry")
    @Test
    @Transactional
    @Rollback
    public void createEntry() {
        Boolean result = entryService.createEntry("Science");
        assertFalse(result, "Created duplicate entry");

        result = entryService.createEntry("Created Entry");
        assertTrue(result, "Creating entry failed");

        Entry entry = entryService.findByTitle("Created Entry");
        assertNotNull(entry, "Can't find created entry");
        assertEquals("Created Entry", entry.getTitle(),
                "Title don't match");

        String search = entryService.searchTitle("Created Entry");
        assertNotNull(search, "Can't search created entry");
        assertEquals("Created Entry", search,
                "Searching result don't match");

        entryRepository.deleteByTitle("Created Entry");
        elasticEntryRepository.deleteByEntryTitle("Created Entry");
    }
}