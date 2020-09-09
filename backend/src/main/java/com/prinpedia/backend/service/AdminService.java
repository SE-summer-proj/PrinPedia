package com.prinpedia.backend.service;

import com.prinpedia.backend.entity.User;

import java.util.List;

public interface AdminService {
    List<User> findAllUsers();
    Boolean grantAdmin(String username);
    Boolean changeUserAbility(String username, Boolean enabled);
    Boolean createSuperUser(String username, String password);
}
