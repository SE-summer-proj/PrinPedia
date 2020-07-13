package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.UserDao;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public Boolean createUser(User user) {
        if(user == null) return false;
        if(user.getUsername() == null || user.getPassword() == null) {
            return false;
        }
        userDao.update(user);
        return true;
    }

    @Override
    public Boolean register(String username, String password, String email) {
        if(userDao.findByName(username) != null) return false;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setAuthority(1);
        userDao.update(user);
        return true;
    }

    @Override
    public Boolean validate(String username, String password) {
        User user = userDao.findByName(username);
        if(user == null) return false;
        return user.getPassword().equals(password);
    }

    @Override
    public User findUserByName(String username) {
        return userDao.findByName(username);
    }
}
