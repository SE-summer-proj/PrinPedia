package com.prinpedia.backend;

import com.prinpedia.backend.dao.TagDao;
import com.prinpedia.backend.dao.UserDao;
import com.prinpedia.backend.entity.Tag;
import com.prinpedia.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    private TagDao tagDao;

    @Test
    void contextLoads() {
        Tag tag = new Tag();
        tag.setKeyword("key");
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        idList.add(3);
        tag.setS1contentId(idList);
        tagDao.update(tag);
    }

}
