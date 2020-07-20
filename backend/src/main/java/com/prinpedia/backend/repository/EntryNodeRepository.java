package com.prinpedia.backend.repository;

import com.prinpedia.backend.entity.EntryNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface EntryNodeRepository extends Neo4jRepository<EntryNode, Long> {
}
