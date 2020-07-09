package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.EntryDao;
import com.prinpedia.backend.entity.Entry;
import com.prinpedia.backend.service.EntryService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService {
    @Autowired
    private EntryDao entryDao;

    @Override
    public Entry findByTitle(String title) {
        Optional<Entry> entryOptional = entryDao.findByTitle(title);
        return entryOptional.orElse(null);
    }
}
