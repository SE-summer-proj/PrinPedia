package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.entity.EntryRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntryRelationRepository extends Neo4jRepository<EntryRelation, Long> {
    @Query("match (parent: Entry), (child: Entry) where parent.index = " +
            "$parentIndex and child.index = $childIndex create " +
            "r = (parent)-[er: EntryRelation]->(child) return r ")
    void createEntryRelation(@Param("parentIndex") int parentIndex,
                             @Param("childIndex") int childIndex);

    @Query("match (parent: Entry), (child: Entry) where parent.index = " +
            "$parentIndex and child.index = $childIndex create " +
            "r = (parent)-[er: EntryRelation {weight: $weight}]->(child) return r ")
    void createEntryRelationWithWeight(@Param("parentIndex") int parentIndex,
                                       @Param("childIndex") int childIndex,
                                       @Param("weight") Long weight);

    @Query("match (parent: Entry)-[r: EntryRelation]-> (current: Entry) " +
            "where current.title = $currentTitle " +
            "return parent")
    List<EntryNode> findParents(@Param("currentTitle") String currentTitle);

    @Query("match (current: Entry)-[r: EntryRelation]-> (child: Entry) " +
            "where current.title = $currentTitle " +
            "return child")
    List<EntryNode> findChildren(@Param("currentTitle") String currentTitle);

    @Query("match (parent: Entry)-[r]->(child: Entry) " +
            "where parent.index = $parentIndex and child.index = $childIndex " +
            "delete r")
    void deleteRelations(@Param("parentIndex") int parentIndex,
                         @Param("childIndex") int childIndex);
}
