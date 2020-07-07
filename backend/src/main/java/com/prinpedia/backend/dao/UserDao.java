package com.prinpedia.backend.dao;

import com.prinpedia.backend.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserDao {
    User findById(Integer userId);
    void update(User user);
    User findByName(String username);
    List<User> findAllUsers();
}
