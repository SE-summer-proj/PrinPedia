package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.repository.EntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EntryDaoImpl implements EntryDao {
    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Optional<Entry> findById(Integer entryId) {
        return entryRepository.findById(entryId);
    }

    @Override
    public Optional<Entry> findByTitle(String title) {
        return entryRepository.findByTitle(title);
    }
}
