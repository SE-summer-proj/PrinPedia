package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.EntryInfo;
import com.prinpedia.backend.entity.Tag;
import com.prinpedia.backend.repository.EntryInfoRepository;
import com.prinpedia.backend.repository.TagRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class TagServiceImplTest {
    @Autowired
    private TagServiceImpl tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntryInfoRepository entryInfoRepository;

    @BeforeEach
    void setUp() {
        entryInfoRepository.deleteByTitle("Test entry");
        tagRepository.deleteByTagName("Test tag");
        tagRepository.deleteByTagName("Another test tag");
    }

    @AfterEach
    void tearDown() {
        entryInfoRepository.deleteByTitle("Test entry");
        tagRepository.deleteByTagName("Test tag");
        tagRepository.deleteByTagName("Another test tag");
    }

    @Test
    @Transactional
    public void tagBasic() {
        Boolean result = tagService.createTag("Test tag");
        assertTrue(result, "Cannot create new tag");

        List<String> tagList = new ArrayList<>();
        tagList.add("Test tag");
        tagList.add("Another test tag");
        tagService.editEntryTag("Test entry", tagList);

        List<EntryInfo> entryInfoList =
                tagService.findEntryByTag("Another test tag");

        List<Tag> tagList1 = tagService.findTagByEntry("Test entry");
        assertNotNull(tagList1, "Cannot find tag by entry");
        assertEquals(2, tagList1.size(),
                "Entry has wrong tag number");

        tagList1 = tagService.findAllTags();
        assertTrue(tagList1.size() >= 2,
                "Cannot find enough tags");
    }
}