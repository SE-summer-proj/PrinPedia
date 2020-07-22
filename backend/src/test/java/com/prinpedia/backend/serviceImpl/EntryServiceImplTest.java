package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.Content;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.Section;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        String summary = "Edit test summary";
        List<Content> contentList = new ArrayList<>();
        Content content1 = new Content();
        content1.setLabel("content1");
        contentList.add(content1);
        Content content2 = new Content();
        content2.setLabel("content2");
        List<Content> contentList1 = new ArrayList<>();
        Content content21 = new Content();
        content21.setLabel("content21");
        contentList1.add(content21);
        Content content22 = new Content();
        content22.setLabel("content22");
        contentList1.add(content22);
        content2.setChildren(contentList1);
        contentList.add(content2);
        List<Section> sectionList = new ArrayList<>();
        Section section1 = new Section();
        section1.setSectionTitle("content1");
        section1.setSectionText("text1");
        sectionList.add(section1);
        Section section2 = new Section();
        section2.setSectionTitle("content2");
        section2.setSectionText("text2");
        sectionList.add(section2);
        Section section3 = new Section();
        section3.setSectionTitle("content21");
        section3.setSectionText("text21");
        sectionList.add(section3);
        Section section4 = new Section();
        section4.setSectionTitle("content22");
        section4.setSectionText("text22");
        sectionList.add(section4);

        Boolean result = entryService.editEntry(title, summary,
                contentList, sectionList);
        assertTrue(result, "Create new entry failed");

        Entry entry = entryService.findByTitle(title);
        assertNotNull(entry, "Can't find entry");
        assertEquals(title, entry.getTitle(), "Entry title don't match");
        assertEquals(summary, entry.getSummary(),
                "Entry summary don't match");

        summary = "New summary";
        result = entryService.editEntry(title, summary,
                contentList, sectionList);
        assertTrue(result, "Create new entry failed");

        entry = entryService.findByTitle(title);
        assertNotNull(entry, "Can't find entry");
        assertEquals(title, entry.getTitle(), "Entry title don't match");
        assertEquals(summary, entry.getSummary(),
                "Entry summary don't match");

        entryRepository.deleteByTitle(title);
        elasticEntryRepository.deleteByEntryTitle(title);
    }
}