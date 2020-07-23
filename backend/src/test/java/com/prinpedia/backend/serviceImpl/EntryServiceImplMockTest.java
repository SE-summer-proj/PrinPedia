package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.dao.EntryRelationDao;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.EntryNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class EntryServiceImplMockTest {
    @Mock
    private EntryRelationDao entryRelationDao;

    @Mock
    private EntryDao entryDao;

    @InjectMocks
    private EntryServiceImpl entryService = new EntryServiceImpl();

    @DisplayName("Find an entry by title")
    @Test
    public void findByTitle() {
        Entry entry = new Entry();
        entry.setTitle("Test");
        Optional<Entry> optionalEntry = Optional.of(entry);
        Mockito.when(entryDao.findByTitle("Test")).thenReturn(optionalEntry);
        Optional<Entry> optionalEntry1 = Optional.empty();
        Mockito.when(entryDao.findByTitle("123")).thenReturn(optionalEntry1);

        String title = "Test";

        Entry result = entryService.findByTitle(title);
        assertNotNull(result, "Cannot find entry by title");
        assertEquals(title, result.getTitle(), "Titles don't match");

        title = "123";

        result = entryService.findByTitle(title);
        assertNull(result, "Find an entry which shouldn't exist");
    }

    @Test
    public void findParentsAndChildren() {
        EntryNode node1 = new EntryNode();
        node1.setIndex(1);
        node1.setTitle("first");
        EntryNode node2 = new EntryNode();
        node2.setIndex(2);
        node2.setTitle("second");

        List<EntryNode> entryNodeList = new ArrayList<>();
        entryNodeList.add(node1);
        entryNodeList.add(node2);

        Mockito.when(entryRelationDao.findParents(Mockito.anyString()))
                .thenReturn(entryNodeList);
        Mockito.when(entryRelationDao.findChildren(Mockito.anyString()))
                .thenReturn(entryNodeList);

        List<String> result = entryService.findParents("title");
        assertNotNull(result, "Cannot get result");
        assertEquals(2, result.size(), "Result size don't match");
        assertTrue(result.contains("first"),
                "Cannot find first parent entry title");
        assertTrue(result.contains("second"),
                "Cannot find second parent entry title");

        result = entryService.findChildren("title");
        assertNotNull(result, "Cannot get result");
        assertEquals(2, result.size(), "Result size don't match");
        assertTrue(result.contains("first"),
                "Cannot find first child entry title");
        assertTrue(result.contains("second"),
                "Cannot find second child entry title");
    }
}