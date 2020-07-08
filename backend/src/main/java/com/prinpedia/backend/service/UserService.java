package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.User;

public interface UserService {
    void createUser(User user);
    void register(String username, String password, String email);
    Boolean validate(String username, String password);
    User findUserByName(String username);
}
