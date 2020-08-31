package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.ElasticEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class EntryServiceImplTest {

    @Autowired
    private EntryServiceImpl entryService;

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
            List<ElasticEntry> result =
                    entryService.searchTitleAndSummary(keyword, 0);
        }
    }
}