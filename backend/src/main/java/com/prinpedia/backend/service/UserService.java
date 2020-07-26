package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.User;

import java.util.List;

public interface UserService {
    Boolean createUser(User user);
    Boolean register(String username, String password, String email);
    Boolean validate(String username, String password);
    User findUserByName(String username);
    List<User> findAllUsers();
    Boolean editUserDetail(User user);
    Boolean grantAdmin(String username);
    Boolean disableUser(String username);
}
