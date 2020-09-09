package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.entity.EntryNode;
import com.prinpedia.backend.repository.ElasticEntryRepository;
import com.prinpedia.backend.repository.EntryNodeRepository;
import com.prinpedia.backend.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EntryDaoImpl implements EntryDao {
    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private ElasticEntryRepository elasticEntryRepository;

    @Autowired
    private EntryNodeRepository entryNodeRepository;

    @Override
    public Optional<Entry> findByTitle(String title) {
        return entryRepository.findByTitle(title);
    }

    @Override
    public Boolean create(Entry entry) {
        if(entry == null) return false;
        if(entry.getTitle() == null) return false;
        if(entry.getIndex() == null) {
            int maxIndex;
            Entry maxIndexEntry = entryRepository.findTopByOrderByIndexDesc();
            if(maxIndexEntry == null) maxIndex = 0;
            else maxIndex = entryRepository.findTopByOrderByIndexDesc().getIndex();
            entry.setIndex(maxIndex + 1);
        }
        entry.setLocked(false);
        entryRepository.save(entry);

        EntryNode entryNode = new EntryNode();
        entryNode.setTitle(entry.getTitle());
        entryNode.setIndex(entry.getIndex());
        entryNodeRepository.save(entryNode);

        ElasticEntry elasticEntry = new ElasticEntry();
        elasticEntry.setEntryTitle(entry.getTitle());
        elasticEntry.setEntrySummary(entry.getSummary());
        elasticEntryRepository.save(elasticEntry);
        return true;
    }

    @Override
    public Boolean update(Entry entry) {
        if(entry.getIndex() == null) {
            int maxIndex;
            Entry maxIndexEntry = entryRepository.findTopByOrderByIndexDesc();
            if(maxIndexEntry == null) maxIndex = 0;
            else maxIndex = entryRepository.findTopByOrderByIndexDesc().getIndex();
            entry.setIndex(maxIndex + 1);
        }
        entryRepository.save(entry);

        ElasticEntry elasticEntry =
                elasticEntryRepository.findByEntryTitle(entry.getTitle());

        if(elasticEntry == null) {
            ElasticEntry newElasticEntry = new ElasticEntry();
            newElasticEntry.setEntryTitle(entry.getTitle());
            newElasticEntry.setEntrySummary(entry.getSummary());
            elasticEntryRepository.save(newElasticEntry);
        }
        else {
            elasticEntry.setEntrySummary(entry.getSummary());
            elasticEntryRepository.save(elasticEntry);
        }

        return true;
    }

}
