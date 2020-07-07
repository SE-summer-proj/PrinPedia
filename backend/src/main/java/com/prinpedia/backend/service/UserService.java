package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.User;

public interface UserService {
    void createUser(User user);
    Boolean validate(String username, String password);
    User findUserByName(String username);
}
