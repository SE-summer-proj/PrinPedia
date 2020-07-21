package com.prinpedia.backend.dao;

import com.prinpedia.backend.entity.EntryNode;

import java.util.List;

public interface EntryRelationDao {
    EntryNode findByIndex(int index);
    Boolean createNode(int index, String title);
    Boolean createRelation(int parentIndex, int childIndex);
    List<EntryNode> findParents(String title);
    List<EntryNode> findChildren(String title);
}
