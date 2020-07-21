package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.EntryRelationDao;
import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.repository.EntryNodeRepository;
import com.prinpedia.backend.repository.EntryRelationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntryRelationDaoImplTest {
    @Autowired
    private EntryRelationDao entryRelationDao;

    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @Autowired
    private EntryRelationRepository entryRelationRepository;

    @Test
    @Transactional
    public void createNode() {
        int index = 123456;
        String title = "hahaha";
        Boolean createResult = entryRelationDao.createNode(index, title);
        assertTrue(createResult, "Create failed");

        EntryNode entryNode = entryRelationDao.findByIndex(index);
        assertNotNull(entryNode, "Can't find created entryNode");
        assertEquals(title, entryNode.getTitle(), "Title don't match");

        entryNodeRepository.deleteByIndex(index);
    }

    @Test
    @Transactional
    public void relationTest() {
        int index1 = 1000;
        String title1 = "title1";

        int index2 = 2000;
        String title2 = "title2";

        int index3 = 3000;
        String title3 = "title3";

        entryRelationDao.createNode(index1, title1);
        entryRelationDao.createNode(index2, title2);
        entryRelationDao.createNode(index3, title3);

        Boolean result = entryRelationDao.createRelation(index1, index3);
        assertTrue(result, "Create 1st relation failed");

        result = entryRelationDao.createRelation(index2, index3);
        assertTrue(result, "Create 2nd relation failed");

        List<EntryNode> entryNodeList = entryRelationDao.findParents(title3);
        assertEquals(2, entryNodeList.size(),
                "Parents size don't match");

        result = entryRelationDao.createRelation(index1, index2);
        assertTrue(result, "Create 3rd relation failed");

        entryNodeList = entryRelationDao.findChildren(title1);
        assertEquals(2, entryNodeList.size(),
                "Parents size don't match");

        entryRelationRepository.deleteRelations(index1, index3);
        entryRelationRepository.deleteRelations(index2, index3);
        entryRelationRepository.deleteRelations(index1, index2);

        entryNodeRepository.deleteByIndex(index1);
        entryNodeRepository.deleteByIndex(index2);
        entryNodeRepository.deleteByIndex(index3);
    }
}