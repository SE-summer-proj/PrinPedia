package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.EntryRelationDao;
import com.prinpedia.backend.entity.EntryNode;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class EntryServiceImplMockTest {
    @Mock
    private EntryRelationDao entryRelationDao;

    @InjectMocks
    private EntryServiceImpl entryService = new EntryServiceImpl();

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