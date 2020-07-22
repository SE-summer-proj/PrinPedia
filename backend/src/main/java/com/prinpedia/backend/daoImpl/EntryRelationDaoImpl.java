package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.EntryRelationDao;
import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.repository.EntryNodeRepository;
import com.prinpedia.backend.repository.EntryRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EntryRelationDaoImpl implements EntryRelationDao {
    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @Autowired
    private EntryRelationRepository entryRelationRepository;

    @Override
    public EntryNode findByIndex(int index) {
        return entryNodeRepository.findByIndex(index);
    }

    @Override
    public Boolean createNode(int index, String title) {
        EntryNode entryNode = new EntryNode();
        entryNode.setIndex(index);
        entryNode.setTitle(title);
        entryNodeRepository.save(entryNode);
        return entryNodeRepository.findByIndex(index) != null;
    }

    @Override
    public Boolean createRelation(int parentIndex, int childIndex) {
        entryRelationRepository.createEntryRelation(parentIndex, childIndex);
        return true;
    }

    @Override
    public List<EntryNode> findParents(String title) {
        return entryRelationRepository.findParents(title);
    }

    @Override
    public List<EntryNode> findChildren(String title) {
        return entryRelationRepository.findChildren(title);
    }
}
