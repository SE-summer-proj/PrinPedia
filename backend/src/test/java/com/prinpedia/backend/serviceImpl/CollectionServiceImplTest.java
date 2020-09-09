package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.Collection;
import com.prinpedia.backend.repository.CollectionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class CollectionServiceImplTest {
    @Autowired
    private CollectionServiceImpl collectionService;

    @Autowired
    private CollectionRepository collectionRepository;

    @BeforeEach
    void setUp() {
        Collection collection =
                collectionRepository
                        .findByUsernameAndTitle("test", "test");
        if(collection != null) {
            collectionRepository.deleteById(collection.getCollectionId());
        }
    }

    @AfterEach
    void tearDown() {
        Collection collection =
                collectionRepository
                        .findByUsernameAndTitle("test", "test");
        if(collection != null) {
            collectionRepository.deleteById(collection.getCollectionId());
        }
    }

    @Test
    @Transactional
    @Order(0)
    @DisplayName("Collection basic")
    public void addAndRemove() {
        Boolean result = collectionService
                .isInCollection("test", "test");
        assertFalse(result, "Test data already exists");

        result = collectionService.addToCollection("test", "test");
        assertTrue(result, "Add to collection failed");

        result = collectionService.isInCollection("test", "test");
        assertTrue(result, "Cannot find added collection");

        result = collectionService.addToCollection("test", "test");
        assertFalse(result, "Add duplicate collection");

        result = collectionService
                .removeFromCollection("test", "test");
        assertTrue(result, "Remove collection failed");

        result = collectionService
                .removeFromCollection("test", "test");
        assertFalse(result, "Remove duplicate collection");
    }

    @Test
    @Transactional
    @Order(1)
    @DisplayName("Get a user's all collections")
    public void getAllCollectionByUser() {
        collectionService.addToCollection("test", "test");
        List<Collection> collections =
                collectionService.getAllCollectionByUser("test");
        assertTrue(collections.size() > 0,
                "Can't get all collections by username");
    }
}