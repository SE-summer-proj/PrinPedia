package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.Entry;

import java.util.Optional;

public interface EntryService {
    Entry findByTitle(String title);
}
