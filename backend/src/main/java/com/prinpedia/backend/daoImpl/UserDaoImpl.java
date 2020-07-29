package com.prinpedia.backend.daoImpl;

import com.prinpedia.backend.dao.UserDao;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.entity.UserOther;
import com.prinpedia.backend.repository.UserOtherRepository;
import com.prinpedia.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserOtherRepository userOtherRepository;

    @Override
    public User findById(Integer userId) {
        User user = userRepository.getOne(userId);
        Optional<UserOther> userOther =
                userOtherRepository.findByUserId(user.getUserId());
        userOther.ifPresent(other -> user.setAvatarBase64(other.getAvatarBase64()));
        return user;
    }

    @Override
    public void update(User user) {
        User user1 = userRepository.saveAndFlush(user);
        Optional<UserOther> userOther =
                userOtherRepository.findByUserId(user1.getUserId());
        if(userOther.isPresent()) {
            if(user.getAvatarBase64() != null) {
                userOther.get().setAvatarBase64(user.getAvatarBase64());
                userOtherRepository.deleteByUserId(user1.getUserId());
                userOtherRepository.save(userOther.get());
            }
        }
        else {
            if(user.getAvatarBase64() != null) {
                UserOther userOther1 = new UserOther();
                userOther1.setUserId(user1.getUserId());
                userOther1.setAvatarBase64(user.getAvatarBase64());
                userOtherRepository.save(userOther1);
            }
        }
    }

    @Override
    public User findByName(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            Optional<UserOther> userOther =
                    userOtherRepository.findByUserId(user.getUserId());
            userOther.ifPresent(other -> user.setAvatarBase64(other.getAvatarBase64()));
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
