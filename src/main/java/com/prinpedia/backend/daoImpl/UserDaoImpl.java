package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.UserDao;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Integer userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
