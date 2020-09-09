package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.Collection;

import java.util.List;

public interface CollectionService {
    Boolean addToCollection(String username, String title);
    Boolean isInCollection(String username, String title);
    Boolean removeFromCollection(String username, String title);
    List<Collection> getAllCollectionByUser(String username);
}
