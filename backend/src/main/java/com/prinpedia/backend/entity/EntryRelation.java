package com.prinpedia.backend.entity;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "EntryRelation")
public class EntryRelation {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private EntryNode start;

    @EndNode
    private EntryNode end;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public EntryNode getStart() {
        return start;
    }
    public void setStart(EntryNode start) {
        this.start = start;
    }

    public EntryNode getEnd() {
        return end;
    }
    public void setEnd(EntryNode end) {
        this.end = end;
    }
}
