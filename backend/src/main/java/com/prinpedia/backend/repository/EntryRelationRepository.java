package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface EntryRelationRepository extends Neo4jRepository<EntryRelation, Long> {
    @Query("match (parent: Entry), (child: Entry) where parent.index = " +
            "$parentIndex and child.index = $childIndex create " +
            "r = (parent)-[er: EntryRelation]->(child) return r ")
    void createEntryRelation(@Param("parentIndex") int parentIndex,
                             @Param("childIndex") int childIndex);
}
