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
    public void createUser(User user) {
        userDao.update(user);
    }

    @Override
    public Boolean validate(String username, String password) {
        User user = userDao.findByName(username);
        if(user.getPassword().equals(password)) return true;
        else return false;
    }

    @Override
    public User findUserByName(String username) {
        return userDao.findByName(username);
    }
}
