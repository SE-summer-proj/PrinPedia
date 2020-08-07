package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.EntryStatics;
import com.prinpedia.backend.entity.SearchStatics;
import com.prinpedia.backend.entity.UserStatics;
import com.prinpedia.backend.repository.EntryStaticsRepository;
import com.prinpedia.backend.repository.SearchStaticsRepository;
import com.prinpedia.backend.repository.UserStaticsRepository;
import com.prinpedia.backend.service.StaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StaticServiceImpl implements StaticService {
    @Autowired
    private EntryStaticsRepository entryStaticsRepository;

    @Autowired
    private SearchStaticsRepository searchStaticsRepository;

    @Autowired
    private UserStaticsRepository userStaticsRepository;

    @Override
    public void entryRecord(String title) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EntryStatics entryStatics =
                entryStaticsRepository
                        .findByTitleAndDate(title, date);
        if(entryStatics == null) {
            entryStatics = new EntryStatics();
            entryStatics.setDate(date);
            entryStatics.setCount((long) 1);
            entryStatics.setTitle(title);
        }
        else {
            entryStatics.setCount(entryStatics.getCount() + 1);
        }
        entryStaticsRepository.save(entryStatics);
    }

    @Override
    public void searchRecord() {
        Date date = new Date();
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SearchStatics searchStatics = searchStaticsRepository.findByDate(date);
        if(searchStatics == null) {
            searchStatics = new SearchStatics();
            searchStatics.setDate(date);
            searchStatics.setCount(1);
        }
        else {
            searchStatics.setCount(searchStatics.getCount() + 1);
        }
        searchStaticsRepository.save(searchStatics);
    }

    @Override
    public void userRecord() {
        Date date = new Date();
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserStatics userStatics = userStaticsRepository.findByDate(date);
        if(userStatics == null) {
            userStatics = new UserStatics();
            userStatics.setDate(date);
            userStatics.setCount(1);
        }
        else {
            userStatics.setCount(userStatics.getCount() + 1);
        }
        userStaticsRepository.save(userStatics);
    }

    @Override
    public List<EntryStatics> getEntryStaticsByTitle(Date start,
                                                     Date end, String title) {
        return entryStaticsRepository.
                findAllByDateBetweenAndTitle(start, end, title);
    }

    @Override
    public List<EntryStatics> getEntryStatics(Date start, Date end) {
        return entryStaticsRepository.findAllByDateBetween(start, end);
    }

    @Override
    public List<SearchStatics> getSearchStatics(Date start, Date end) {
        return searchStaticsRepository.findAllByDateBetween(start, end);
    }

    @Override
    public List<UserStatics> getUserStatics(Date start, Date end) {
        return userStaticsRepository.findAllByDateBetween(start, end);
    }
}
