package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.Collection;
import com.prinpedia.backend.repository.CollectionRepository;
import com.prinpedia.backend.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {
    @Autowired
    private CollectionRepository collectionRepository;

    @Override
    public Boolean addToCollection(String username, String title) {
        if(isInCollection(username, title)) return false;
        Collection collection = new Collection();
        collection.setUsername(username);
        collection.setTitle(title);
        collection.setCollectTime(new Date());
        collectionRepository.save(collection);
        return true;
    }

    @Override
    public Boolean isInCollection(String username, String title) {
        return collectionRepository.
                findByUsernameAndTitle(username, title) != null;
    }

    @Override
    public Boolean removeFromCollection(String username, String title) {
        if(!isInCollection(username, title)) return false;
        Collection collection =
                collectionRepository.findByUsernameAndTitle(username, title);
        collectionRepository.deleteById(collection.getCollectionId());
        return true;
    }

    @Override
    public List<Collection> getAllCollectionByUser(String username) {
        return collectionRepository.findAllByUsername(username);
    }
}
