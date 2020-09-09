package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.EntryStatics;
import com.prinpedia.backend.entity.SearchStatics;
import com.prinpedia.backend.entity.UserStatics;
import com.prinpedia.backend.repository.EntryStaticsRepository;
import com.prinpedia.backend.repository.SearchStaticsRepository;
import com.prinpedia.backend.repository.UserStaticsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class StaticServiceImplTest {
    @Autowired
    private StaticServiceImpl staticService;

    @Autowired
    private EntryStaticsRepository entryStaticsRepository;

    @Autowired
    private SearchStaticsRepository searchStaticsRepository;

    @Autowired
    private UserStaticsRepository userStaticsRepository;

    @Test
    @Transactional
    public void EntryStatics() {
        Date date = new Date();
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        entryStaticsRepository.deleteByTitle("Test entry");

        staticService.entryRecord("Test entry");
        List<EntryStatics> entryStatics =
                staticService.getEntryStaticsByTitle(date, date, "Test entry");
        assertTrue(entryStatics.size() > 0, "Cannot get statics");
        EntryStatics entryStatics1 = entryStatics.get(0);
        Long count = entryStatics1.getCount();
        staticService.entryRecord("Test entry");
        entryStatics =
                staticService.getEntryStaticsByTitle(date, date, "Test entry");
        assertTrue(entryStatics.size() > 0, "Cannot get statics");
        entryStatics1 = entryStatics.get(0);
        assertEquals(entryStatics1.getCount(), count + 1,
                "Count number didn't increase");

        entryStatics =
                staticService.getEntryStatics(date, date);
        assertTrue(entryStatics.size() > 0, "Cannot get statics");

        entryStaticsRepository.deleteByTitle("Test entry");
    }

    @Test
    @Transactional
    public void searchStatics() {
        Date date = new Date();
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        searchStaticsRepository.deleteByDate(date);

        staticService.searchRecord();
        List<SearchStatics> searchStatics =
                staticService.getSearchStatics(date, date);
        assertTrue(searchStatics.size() > 0, "Cannot get statics");
        SearchStatics searchStatics1 = searchStatics.get(0);
        long count = searchStatics1.getCount();

        staticService.searchRecord();
        searchStatics = staticService.getSearchStatics(date, date);
        assertTrue(searchStatics.size() > 0, "Cannot get statics");
        searchStatics1 = searchStatics.get(0);
        assertTrue(searchStatics1.getCount() > count,
                "Count number didn't increase");

        searchStaticsRepository.deleteByDate(date);
    }

    @Test
    @Transactional
    public void userStatics() {
        Date date = new Date();
        try {
            SimpleDateFormat sdf =
                    new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(new Date());
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userStaticsRepository.deleteByDate(date);

        staticService.userRecord();
        List<UserStatics> userStatics =
                staticService.getUserStatics(date, date);
        assertTrue(userStatics.size() > 0, "Cannot get statics");
        UserStatics userStatics1 = userStatics.get(0);
        long count = userStatics1.getCount();

        staticService.userRecord();
        userStatics = staticService.getUserStatics(date, date);
        assertTrue(userStatics.size() > 0, "Cannot get statics");
        userStatics1 = userStatics.get(0);
        assertTrue(userStatics1.getCount() > count,
                "Count number didn't increase");

        userStaticsRepository.deleteByDate(date);
    }
}