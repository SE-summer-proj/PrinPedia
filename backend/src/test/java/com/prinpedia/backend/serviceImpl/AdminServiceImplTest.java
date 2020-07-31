package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.entity.Role;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach()
    void setUp() {
        User test = userRepository.findByUsername("test");
        if(test != null) {
            userRepository.deleteById(test.getUserId());
        }
    }

    @AfterEach
    void after() {
        User test = userRepository.findByUsername("test");
        if(test != null) {
            userRepository.deleteById(test.getUserId());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void findAllUsers() {
        String username = "test";
        String password = "test";
        String email = "123@123.com";

        userService.register(username, password, email);
        List<User> userList = adminService.findAllUsers();
        assertTrue(userList.size() > 0, "Can't find any users");
    }

    @Test
    @Order(0)
    @Transactional
    @Rollback
    public void grantAdmin() {
        String username = "test";
        String password = "test";
        String email = "123@123.com";

        userService.register(username, password, email);

        Boolean result = adminService.grantAdmin(username);
        assertTrue(result, "Grant admin failure");

        User user = userService.findUserByName(username);
        boolean flag = false;
        List<Role> roleList = user.getRoleList();
        for(Role role : roleList) {
            System.out.println(role.getRoleName());
            if(role.getRoleName().equals("ROLE_ADMIN")) {
                flag = true;
                break;
            }
        }
        assertTrue(flag, "Cannot find \"ROLE_ADMIN\" in roleList");

        result = adminService.grantAdmin(username);
        assertTrue(result, "Grant admin failure when already have");

        result = adminService.grantAdmin("wrong");
        assertFalse(result,
                "Granting admin to a non-existing user don't fail");
    }

    @Test
    @Order(1)
    @Transactional
    @Rollback
    void changeUserAbility() {
        String username = "test";
        String password = "test";
        String email = "123@123.com";

        userService.register(username, password, email);

        Boolean result = adminService.changeUserAbility(username, false);
        assertTrue(result, "Disable user failure");

        User user = userService.findUserByName(username);
        assertFalse(user.getEnabled(),
                "User enabled property don't match");

        adminService.grantAdmin(username);
        result = adminService.changeUserAbility(username, false);
        assertFalse(result, "Change an admin's ability");

        result = adminService.changeUserAbility("wrong", false);
        assertFalse(result, "Change a non-existing user's ability");
    }

}