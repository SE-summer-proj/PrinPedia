package com.prinpedia.backend.serviceImpl;

import com.prinpedia.backend.dao.RoleDao;
import com.prinpedia.backend.dao.UserDao;
import com.prinpedia.backend.entity.Role;
import com.prinpedia.backend.entity.User;
import com.prinpedia.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<User> findAllUsers() {
        return userDao.findAllUsers();
    }

    @Override
    public Boolean grantAdmin(String username) {
        User user = userDao.findByName(username);
        if(user == null) return false;
        List<Role> roleList = user.getRoleList();
        for(Role role : roleList) {
            if(role.getRoleName().equals("ROLE_ADMIN")) return true;
        }
        Role admin = roleDao.findByRoleName("ROLE_ADMIN");
        if(admin == null) { admin = new Role(); admin.setRoleName("ROLE_ADMIN"); }
        roleList.add(admin);
        user.setRoleList(roleList);
        userDao.update(user);
        return true;
    }

    @Override
    public Boolean changeUserAbility(String username, Boolean enabled) {
        User user = userDao.findByName(username);
        if(user == null) return false;
        List<Role> roleList = user.getRoleList();
        for(Role role : roleList) {
            if(role.getRoleName().equals("ROLE_ADMIN")) return false;
        }
        if(enabled == null) return false;
        user.setEnabled(enabled);
        userDao.update(user);
        return true;
    }

    @Override
    public Boolean createSuperUser(String username, String password) {
        if(userDao.findByName(username) != null) return false;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);
        Role role = roleDao.findByRoleName("ROLE_USER");
        if(role == null) { role = new Role(); role.setRoleName("ROLE_USER"); }
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);
        role = roleDao.findByRoleName("ROLE_ADMIN");
        if(role == null) { role = new Role(); role.setRoleName("ROLE_ADMIN"); }
        roleList.add(role);
        role = roleDao.findByRoleName("ROLE_SUPER");
        if(role == null) { role = new Role(); role.setRoleName("ROLE_SUPER"); }
        roleList.add(role);
        user.setRoleList(roleList);
        user.setUserId(-1);
        userDao.update(user);
        return true;
    }
}
