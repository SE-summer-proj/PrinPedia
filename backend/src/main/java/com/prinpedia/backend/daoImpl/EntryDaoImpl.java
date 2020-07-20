package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.ElasticEntry;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.ElasticEntryRepository;
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

    @Override
    public Optional<Entry> findByTitle(String title) {
        return entryRepository.findByTitle(title);
    }

    @Override
    public Boolean create(Entry entry) {
        if(entry == null) return false;
        entryRepository.insert(entry);
        ElasticEntry elasticEntry = new ElasticEntry();
        elasticEntry.setEntryTitle(entry.getTitle());
        elasticEntryRepository.save(elasticEntry);
        return true;
    }

    @Override
    public Boolean update(Entry entry) {
        if(entry == null) return false;
        Optional<Entry> optionalEntry =
                entryRepository.findByTitle(entry.getTitle());
        if(optionalEntry.isPresent()) {
            entryRepository.deleteByTitle(entry.getTitle());
        }
        entryRepository.save(entry);

        ElasticEntry elasticEntry =
                elasticEntryRepository.findByEntryTitle(entry.getTitle());
        if(elasticEntry != null) {
            if(elasticEntry.getEntrySummary() == null ||
                    !elasticEntry.getEntrySummary().equals(entry.getSummary())) {
                elasticEntryRepository.deleteByEntryTitle(entry.getTitle());
                ElasticEntry elasticEntry1 = new ElasticEntry();
                elasticEntry1.setEntryTitle(entry.getTitle());
                elasticEntry1.setEntrySummary(entry.getSummary());
                elasticEntryRepository.save(elasticEntry1);
            }
            return true;

        }

        ElasticEntry elasticEntry1 = new ElasticEntry();
        elasticEntry1.setEntryTitle(entry.getTitle());
        elasticEntry1.setEntrySummary(entry.getSummary());
        elasticEntryRepository.save(elasticEntry1);
        return true;
    }

}
