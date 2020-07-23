package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryServiceImplTest {

    @Autowired
    private EntryServiceImpl entryService;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @DisplayName("Search title")
    @Test
    public void searchTitle() {
        String keyword = "science";
        String result = entryService.searchTitle(keyword);
        System.out.println("Search with keyword(" + keyword + "): " + result);

        keyword = "hahaha";
        result = entryService.searchTitle(keyword);
        System.out.println("Search with keyword(" + keyword + "): " + result);
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
    public void createEntry() {
        Boolean result = entryService.createEntry("Created Entry");
        assertTrue(result, "Create entry failed");

        result = entryService.createEntry("Created Entry");
        assertFalse(result, "Created duplicate entry");

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

    @DisplayName("Edit entry")
    @Test
    @Transactional
    public void editEntry() {
        String title = "Edit test";
        String wikiText = "wikiText";

        Boolean result = entryService.editEntry(title, wikiText);
        assertTrue(result, "Create new entry failure");

        Entry entry = entryService.findByTitle(title);
        assertNotNull(entry, "Can't find entry");
        assertEquals(title, entry.getTitle(), "Entry title don't match");
        assertEquals(wikiText, entry.getWikiText(),
                "Entry wiki text don't match");

        wikiText = "New wikiText";
        result = entryService.editEntry(title, wikiText);
        assertTrue(result, "Edit entry failure");

        entry = entryService.findByTitle(title);
        assertNotNull(entry, "Can't find entry");
        assertEquals(title, entry.getTitle(), "Entry title don't match");
        assertEquals(wikiText, entry.getWikiText(),
                "Entry wiki text don't match");

        entryRepository.deleteByTitle(title);
        elasticEntryRepository.deleteByEntryTitle(title);
    }
}