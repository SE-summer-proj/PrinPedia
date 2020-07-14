package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.Entry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryServiceTest {
    @Autowired
    private EntryService entryService;

    @DisplayName("Find an entry by title")
    @Test
    public void findByTitle() {
        String title = "Science";

        Entry entry = entryService.findByTitle(title);
        assertNotNull(entry, "Cannot find entry by title");
        assertEquals(title, entry.getTitle(), "Titles don't match");

        title = "abc";

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
    public void searchTitleAndSummary() {
        String []strings = {
                "greek",
                "shit",
                "hahaha"
        };

        for(String keyword : strings) {
            List<String> result = entryService.searchTitleAndSummary(keyword);
            System.out.println("Search with keyword (" + keyword + "): " + result);
        }

    }
}