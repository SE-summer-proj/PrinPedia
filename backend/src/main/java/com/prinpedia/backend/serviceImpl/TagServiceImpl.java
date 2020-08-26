package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.EntryInfo;
import com.prinpedia.backend.entity.Tag;
import com.prinpedia.backend.repository.EntryInfoRepository;
import com.prinpedia.backend.repository.TagRepository;
import com.prinpedia.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntryInfoRepository entryInfoRepository;

    @Override
    public List<Tag> findTagByEntry(String title) {
        EntryInfo entryInfo = entryInfoRepository.findByTitle(title);
        if(entryInfo == null) return null;
        return entryInfo.getTagList();
    }

    @Override
    public List<EntryInfo> findEntryByTag(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName);
        if(tag == null) return null;
        return tag.getEntryInfoList();
    }

    @Override
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    @Override
    public void editEntryTag(String title, List<String> tagList) {
        EntryInfo entryInfo = entryInfoRepository.findByTitle(title);
        if(entryInfo == null) {
            entryInfo = new EntryInfo();
            entryInfo.setTitle(title);
            entryInfo.setEntryInfoId(-1);
        }
        List<Tag> tagList1 = new ArrayList<>();
        for(String tagName : tagList) {
            Tag tag = tagRepository.findByTagName(tagName);
            if(tag == null) {
                tag = new Tag();
                tag.setTagName(tagName);
            }
            tagList1.add(tag);
        }
        entryInfo.setTagList(tagList1);
        entryInfoRepository.save(entryInfo);
    }

    @Override
    public Boolean createTag(String tagName) {
        Tag tag = tagRepository.findByTagName(tagName);
        if(tag != null) return false;
        tag = new Tag();
        tag.setTagName(tagName);
        tagRepository.save(tag);
        return true;
    }
}
